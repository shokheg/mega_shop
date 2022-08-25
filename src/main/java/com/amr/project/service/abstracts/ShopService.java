package com.amr.project.service.abstracts;

import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ShopService extends ReadWriteService<Shop, Long> {

    List<Shop> findAllShopForUser();
    List<Shop>findPopularShop();
    List<Shop> findShopsNotModerated();

    List<Shop> findShopsBySearchRequest(String query);

    PaginationDto findAllShop(int page, int size, int offset);
}
