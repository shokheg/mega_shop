package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl extends ReadWriteServiceImpl<Item, Long> implements ItemService {

    private ItemDao itemDao;

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public ItemServiceImpl(ReadWriteDao<Item, Long> dao) {
        super(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findItemsBySearchRequest(String query) {
        return itemDao.findItemsBySearchRequest(query);
    }

    @Override
    public List<Item> findPopularItems() {
        return itemDao.findPopularItems();
    }

    @Override
    public PaginationDto findAllItems(int page, int size, int offset) {
        return itemDao.findAllItems(page, size, offset);
    }

    @Override
    public List<Item> findItemsNotModerated() {
        return itemDao.findNotModeratedItems();
    }
}
