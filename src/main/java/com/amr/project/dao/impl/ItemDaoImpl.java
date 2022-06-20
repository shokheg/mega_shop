package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<Item, Long> implements ItemDao {

    @Override
    public List<Item> findItemsBySearchRequest(String query) {
        return em.createQuery("select i from Item i where i.name like :query and i.isModerateAccept = true and i.isPretendedToBeDeleted = false", Item.class)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }
}
