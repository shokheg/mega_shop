package com.amr.project.service.abstracts;

import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemService extends ReadWriteService<Item, Long>{

    List<Item> findPopularItems();

    List<Item> findItemsBySearchRequest(String query);

    PaginationDto findAllItems(int page, int size, int offset);
}
