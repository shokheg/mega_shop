package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopServiceImpl extends ReadWriteServiceImpl<Shop, Long> implements ShopService {

    private ShopDao shopDao;

    @Autowired
    public void setShopDao(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    public ShopServiceImpl(ReadWriteDao<Shop, Long> dao) {
        super(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> findAllShopForUser() {
        return shopDao.findAllShopForUser();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> findShopsBySearchRequest(String query) {
        return shopDao.findShopsBySearchRequest(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> findPopularShop() {
        return shopDao.findPopularShop();
    }

    @Override
    public PaginationDto findAllShop(int page, int size, int offset) {
        return shopDao.findAllShop(page, size, offset);
    }
}
