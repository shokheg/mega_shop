package com.amr.project.service.impl;

import com.amr.project.converter.CategoryConverter;
import com.amr.project.converter.ItemConverter;
import com.amr.project.converter.ShopConverter;
import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.MainPageDto;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.MainPageService;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageServiceImpl implements MainPageService {
    private final ItemService itemService;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final ItemConverter itemConverter;
    private final ShopConverter shopConverter;

    private final CategoryConverter categoryConverter;
    @Autowired
    public MainPageServiceImpl(ItemService itemService, ShopService shopService, CategoryService categoryService, ItemConverter itemConverter, ShopConverter shopConverter, CategoryConverter categoryConverter) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.itemConverter = itemConverter;
        this.shopConverter = shopConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public MainPageDto getMainPageDto() {
        return MainPageDto.builder()
                .categoryDto(categoryConverter.toDtoList(categoryService.findAll()))
                .shopDtoList(shopConverter.entityToDto(shopService.findPopularShop()))
                .itemDtoList(itemConverter.entityToDto(itemService.findPopularItems()))
                .username(null)
                .build();
    }
}
