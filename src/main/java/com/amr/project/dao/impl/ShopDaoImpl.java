package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopDaoImpl extends ReadWriteDaoImpl<Shop, Long> implements ShopDao {

    @Override
    public List<Shop> findAllShopForUser() {
        return em.createQuery("select s from Shop s where s.isPretendedToBeDeleted = false and s.isModerateAccept = true", Shop.class).getResultList();
    }
}
