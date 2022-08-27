package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CartDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl extends ReadWriteServiceImpl<CartItem, Long> implements CartService {

    private CartDao cartDao;

    public CartServiceImpl(ReadWriteDao<CartItem, Long> dao, CartDao cartDao) {
        super(dao);
        this.cartDao = cartDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> findCartItemsByUserID(Long id) {
        return cartDao.findCartItemsByUserID(id);
    }

}
