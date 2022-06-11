package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopConverter;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidShopException;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@Api(tags = "Контроллер магазина")
public class ShopRestController {

    private final ShopService shopService;
    private final ShopConverter shopConverter;

    @Autowired
    public ShopRestController(ShopService shopService, ShopConverter shopConverter) {
        this.shopService = shopService;
        this.shopConverter = shopConverter;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить магазин по ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Магазин не найден.")
            }
    )
    public ResponseEntity<ShopDto> shopWindow(@PathVariable Long id) {
        if (!shopService.existsById(id)) {
            Date date = new Date();
            throw new InvalidShopException(new ErrorMessage(204, date, "Магазин не найден.", "Магазина с ID = " + id + " не существует в базе данных."));
        }
        Shop shop = shopService.findById(id);
        ShopDto shopDto = shopConverter.entityToDto(shop);
        return new ResponseEntity<>(shopDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить все магазины (Админ)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping("/admin")
    public ResponseEntity<List<ShopDto>> allShopWindowsAdmin() {
        List<Shop> shopList = shopService.findAll();
        List<ShopDto> shopDtoList = shopConverter.entityToDto(shopList);
        return new ResponseEntity<>(shopDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Получить все магазины (Пользователь)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping("/user")
    public ResponseEntity<List<ShopDto>> allShopWindowsUser() {
        List<Shop> shopList = shopService.findAllShopForUser();
        List<ShopDto> shopDtoList = shopConverter.entityToDto(shopList);
        return new ResponseEntity<>(shopDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавить новый магазин (без ID)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Создан"),
                    @ApiResponse(code = 400, message = "Магазин уже создан.")
            }
    )
    @PostMapping
    public ResponseEntity<ShopDto> createShop(@RequestBody ShopDto shopDto) {
        if (shopDto.getId() != null) {
            Date date = new Date();
            throw new InvalidShopException(new ErrorMessage(400, date, "Магазин уже создан.", "Магазин с ID = " + shopDto.getId() + " уже существует в базе данных."));
        }
        Shop shop = shopConverter.dtoToEntity(shopDto);
        shopService.persist(shop);
        return new ResponseEntity<>(shopDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Изменить магазин")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "ID магазина не может быть пустым.")
            }
    )
    @PutMapping
    public ResponseEntity<ShopDto> updateShop(@RequestBody ShopDto shopDto) {
        if (shopDto.getId() == null) {
            Date date = new Date();
            throw new InvalidShopException(new ErrorMessage(400, date, "ID магазина не может быть пустым.", "При изменении магазина ID = null"));
        }
        Shop shop = shopConverter.dtoToEntity(shopDto);
        shopService.update(shop);
        return new ResponseEntity<>(shopDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить магазин по ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Магазин не найден.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        if (!shopService.existsById(id)) {
            Date date = new Date();
            throw new InvalidShopException(new ErrorMessage(204, date, "Магазин не найден.", "Магазина с ID = " + id + " не существует в базе данных."));
        }
        shopService.deleteByIdCascadeEnable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Претендент на удаление (ID магазина)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Магазин не найден.")
            }
    )
    @PatchMapping("/pretendedToBeDeleted/{id}")
    public ResponseEntity<Void> pretendedToBeDeletedShop(@PathVariable Long id) {
        if (!shopService.existsById(id)) {
            Date date = new Date();
            throw new InvalidShopException(new ErrorMessage(204, date, "Магазин не найден.", "Магазина с ID = " + id + " не существует в базе данных."));
        }
        Shop shop = shopService.findById(id);
        shop.setPretendedToBeDeleted(true);
        shopService.update(shop);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
