package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl <Item, Long> implements ItemDao {

    public List<Item> findPopularItems() {
        return em.createQuery("SELECT u FROM Item u WHERE u.isModerateAccept = true AND u.isPretendedToBeDeleted = false ORDER BY u.rating DESC", Item.class).setMaxResults(4).getResultList();
    }
    public List<Item> findItemsBySearchRequest (String query) {
        return em.createQuery("select i from Item i where i.name like :query and i.isModerateAccept = true and i.isPretendedToBeDeleted = false", Item.class)
                .setParameter("query", "%" + query + "%")
                .getResultList();
    }
}
