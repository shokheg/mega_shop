package com.amr.project.service.impl;

import com.amr.project.converter.CategoryConverter;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopConverter;
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
    private final ItemMapper itemMapper;
    private final ShopConverter shopConverter;

    private final CategoryConverter categoryConverter;
    @Autowired
    public MainPageServiceImpl(ItemService itemService, ShopService shopService, CategoryService categoryService, ItemMapper itemMapper, ShopConverter shopConverter, CategoryConverter categoryConverter) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.categoryService = categoryService;
        this.itemMapper = itemMapper;
        this.shopConverter = shopConverter;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public MainPageDto getMainPageDto() {
        return MainPageDto.builder()
                .categoryDto(categoryConverter.toDtoList(categoryService.findAll()))
                .shopDtoList(shopConverter.entityToDto(shopService.findPopularShop()))
                .itemDtoList(itemMapper.toItemsDTO(itemService.findPopularItems()))
                .username(null)
                .build();
    }
}
