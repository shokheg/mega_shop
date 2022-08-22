package com.amr.project.dao.impl;

import com.amr.project.annotations.Pagination;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopDaoImpl extends ReadWriteDaoImpl<Shop, Long> implements ShopDao {

    @Override
    public List<Shop> findAllShopForUser() {
        return em.createQuery("select s from Shop s where s.isPretendedToBeDeleted = false and s.isModerateAccept = true", Shop.class).getResultList();
    }

    @Override
    public List<Shop> findShopsBySearchRequest(String query) {
        return em.createQuery("select s from Shop s where s.name like :query and s.isModerateAccept = true and s.isPretendedToBeDeleted = false", Shop.class)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }

    @Override
    public List<Shop> findShopsNotModerated() {
        return em.createQuery("select s from Shop s where s.isModerated = false", Shop.class)
                .getResultList();
    }

    @Override
    public List<Shop> findPopularShop() {
        return em.createQuery("select s from Shop s where s.isPretendedToBeDeleted = false and s.isModerateAccept = true order by s.rating desc", Shop.class).setMaxResults(4).getResultList();
    }

    @Override
    @Pagination(entityClass = Shop.class)
    public PaginationDto findAllShop(int page, int size, int offset) {
        return null;
    }


}
