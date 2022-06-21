package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long>{
    List<Item> findPopularItems();

    List<Item> findItemsBySearchRequest(String query);
}
