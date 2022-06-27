package com.amr.project.aspect;

import com.amr.project.annotations.Pagination;
import com.amr.project.model.dto.PaginationDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Aspect
@Component
public class PaginationAnnotation {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut(value = "@annotation(com.amr.project.annotations.Pagination)")
    private void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(pagination)")
    public Object pagination(ProceedingJoinPoint point, Pagination pagination) {

        PaginationDto paginationDto = new PaginationDto();

        int limit;

        Class<?> clazz = pagination.entityClass();

        Object[] args = point.getArgs();

        int page = Math.max((int) args[0], 0);
        int size = Math.max((int) args[1], 0);
        int offset = Math.max((int) args[2], 0);

        Long count = entityManager.createQuery("select count (u.id) from " + clazz.getName() + " u", Long.class)
                .getSingleResult();

        int maxPages = (int) Math.ceil(((double) count - (double) offset) / (double) size);

        limit = offset == 0 ? (page) * size : (page * size) + offset;

        List<?> entities = entityManager.createQuery("select u from " + clazz.getName() + " u group by u.id", clazz)
                .setFirstResult(limit)
                .setMaxResults(size)
                .getResultList();

        paginationDto.setContent(entities);
        paginationDto.setCount(count);
        paginationDto.setMaxPages(maxPages);

        return paginationDto;
    }

    @AfterThrowing(value = "pointcut() && @annotation(pagination)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Pagination pagination, Exception ex) {
        System.out.println("Выполнен метод afterThrowing");
        System.out.println("Произошло исключение: " + ex.getMessage());
    }
}
