package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemConverter;
import com.amr.project.converter.ShopConverter;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@Api(tags = "Контроллер поиска")
public class SearchRestController {

    private ShopService shopService;
    private ItemService itemService;
    private ShopConverter shopConverter;
    private ItemConverter itemConverter;

    @Autowired
    public SearchRestController(ShopService shopService, ItemService itemService, ShopConverter shopConverter, ItemConverter itemConverter) {
        this.shopService = shopService;
        this.itemService = itemService;
        this.shopConverter = shopConverter;
        this.itemConverter = itemConverter;
    }

    @GetMapping
    @ApiOperation(value = "Получить магазины, где в названии есть query")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    public ResponseEntity<List<ShopDto>> searchShop(@RequestParam String query) {
        List<Shop> shops = shopService.findShopsBySearchRequest(query);
        return new ResponseEntity<>(shopConverter.entityToDto(shops), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Получить товары, где в названии есть query")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    public ResponseEntity<List<ItemDto>> searchItem(@RequestParam String query) {
        List<Item> items = itemService.findItemsBySearchRequest(query);
        return new ResponseEntity<>(itemConverter.entityToDto(items), HttpStatus.OK);
    }
}
