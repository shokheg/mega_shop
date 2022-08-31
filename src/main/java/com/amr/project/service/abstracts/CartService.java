package com.amr.project.service.abstracts;

import com.amr.project.model.entity.CartItem;

import java.util.List;

public interface CartService extends ReadWriteService<CartItem, Long>{
    List<CartItem> findCartItemsByUserID(Long id);
    List<CartItem> findCartItemsByItemID(Long id);
    void deleteCartItemsByItemID(Long id);
    int getCartItemQuantityByID(Long id);

}
