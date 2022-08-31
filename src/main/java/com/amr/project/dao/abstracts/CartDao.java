package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.CartItem;

import java.util.List;

public interface CartDao extends ReadWriteDao<CartItem, Long> {
    List<CartItem> findCartItemsByUserID(Long id);
    List<CartItem> findCartItemsByItemID(Long id);
    void deleteCartItemsByItemID(Long id);

}
