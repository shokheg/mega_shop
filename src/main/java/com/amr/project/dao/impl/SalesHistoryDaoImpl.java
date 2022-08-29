package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.entity.report.SalesHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Repository
public class SalesHistoryDaoImpl extends ReadWriteDaoImpl <SalesHistory, Long> implements SalesHistoryDao {

    @Override
    public List<SalesHistory> findByDates(Calendar fromDate, Calendar toDate, Long shopId){

        Query query = em.createQuery("FROM SalesHistory sh WHERE sh.item.shop.id = :shopId AND "
                + " sh.orderDate < :toDate"
                + " AND sh.orderDate >= :fromDate"
                + " ORDER BY sh.orderDate DESC", SalesHistory.class);

        toDate.add(Calendar.DATE, 1);

        query.setParameter("shopId", shopId);
        query.setParameter("toDate", toDate);
        query.setParameter("fromDate", fromDate);
        return query.getResultList();
    }
}
