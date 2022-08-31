package com.amr.project.dao.impl;

import com.amr.project.dao.Util;
import com.amr.project.dao.abstracts.CartDao;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Chat;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CartDaoImpl extends ReadWriteDaoImpl<CartItem, Long> implements CartDao {


    @Override
    public List<CartItem> findCartItemsByUserID(Long id) {
        TypedQuery<CartItem> query = em.createQuery("select c from CartItem c where c.user.id = :id", CartItem.class)
                .setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<CartItem> findCartItemsByItemID(Long id) {
        TypedQuery<CartItem> query = em.createQuery("select c from CartItem c where c.itemInCart.id = :id", CartItem.class)
                .setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void deleteCartItemsByItemID(Long id) {
        em.createQuery("delete from CartItem u where u.itemInCart.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
