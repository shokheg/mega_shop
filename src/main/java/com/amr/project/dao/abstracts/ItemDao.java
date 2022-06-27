package com.amr.project.dao.abstracts;

import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long>{
    List<Item> findPopularItems();

    List<Item> findItemsBySearchRequest(String query);

    PaginationDto findAllItems(int page, int size, int offset);
}
