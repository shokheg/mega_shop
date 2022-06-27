package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopConverter;
import com.amr.project.model.dto.*;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.MainPageService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/")
@Api(tags = "Контроллер главной страницы")
public class MainPageRestController {

    private final MainPageService mainPageService;
    private final ItemService itemService;
    private final ShopService shopService;
    private final ShopConverter shopConverter;
    public final ItemMapper itemMapper;

    public MainPageRestController(MainPageService mainPageService, ItemService itemService,
                                  ShopService shopService, ShopConverter shopConverter, ItemMapper itemMapper) {
        this.mainPageService = mainPageService;
        this.itemService = itemService;
        this.shopService = shopService;
        this.shopConverter = shopConverter;
        this.itemMapper = itemMapper;
    }

    @ApiOperation(value = "Получить все категории, популярные товары и магазины (4шт)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping
    public ResponseEntity<MainPageDto> findMainPage() {
        MainPageDto mainPageDto = mainPageService.getMainPageDto();
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Пагинация по товарам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/pageItem")
    public ResponseEntity<PaginationDto> paginationItems(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        PaginationDto paginationDto = itemService.findAllItems(page, size, offset);
        List<?> content = paginationDto.getContent();
        List<ItemDto> itemDtos = itemMapper.toItemsDTO((List<Item>) content);
        paginationDto.setContent(itemDtos);
        return ResponseEntity.ok(paginationDto);
    }

    @ApiOperation(value = "Пагинация по магазинам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/pageShop")
    public ResponseEntity<PaginationDto> paginationShops(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "2") int size,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        PaginationDto paginationDto = shopService.findAllShop(page, size, offset);
        List<?> content = paginationDto.getContent();
        List<ShopDto> shopDtos = shopConverter.entityToDto((List<Shop>) content);
        paginationDto.setContent(shopDtos);
        return ResponseEntity.ok(paginationDto);
    }
}
