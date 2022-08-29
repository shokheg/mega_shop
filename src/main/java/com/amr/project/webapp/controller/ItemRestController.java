package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidItemException;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/items")
@Api(tags = "Контроллер работы с товарами")
public class ItemRestController {

    public final ItemService itemServiceImpl;
    public final ItemMapper itemMapper;

    public ItemRestController(ItemService itemServiceImpl, ItemMapper itemMapper) {
        this.itemServiceImpl = itemServiceImpl;
        this.itemMapper = itemMapper;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить товар по ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Товар не найден.")
            }
    )
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long id) {
        if (!itemServiceImpl.existsById(id)) {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(204, date, "Товар не найден."
                    , "Товара с ID = " + id + " не существует в базе данных."));}
        Item item = itemServiceImpl.findById(id);
        ItemDto itemDto = itemMapper.toDto(item);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }


    @ApiOperation(value = "Добавить новый товар (без ID)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Создан"),
                    @ApiResponse(code = 400, message = "Товар уже создан.")
            }
    )
    @PostMapping
    public ResponseEntity<ItemDto> addNewItem(@RequestBody ItemDto itemDto) {
        System.out.println(itemDto);
        if (itemDto.getId() != null) {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(400, date, "Такой товар уже создан."
                    , "Товар с ID = " + itemDto.getId() + " уже существует в базе данных."));
        }
        itemServiceImpl.persist(itemMapper.toEntity(itemDto));
        return new ResponseEntity<>(itemDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Изменить товар")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "ID товара не может быть пустым.")
            }
    )
    @PutMapping
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto) {
        if (itemDto.getId() == null) {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(400, date
                    , "ID товара не может быть пустым.", "При изменении товара ID = null"));
        }
        itemServiceImpl.update(itemMapper.toEntity(itemDto));
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить товар по ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Товар не найден.")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") Long id) {
        if (!itemServiceImpl.existsById(id)) {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(204, date, "Товар не найден."
                    , "Товара с ID = " + id + " не существует в базе данных."));
        }
        itemServiceImpl.deleteByIdCascadeEnable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Претендент на удаление (ID товара)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Товар не найден.")
            }
    )
    @PatchMapping("/pretendedToBeDeleted/{id}")
    public ResponseEntity<Void> pretendedToBeDeletedItem(@PathVariable("id") Long id) {
        if (!itemServiceImpl.existsById(id)) {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(204, date
                    , "Товар не найден.", "Товара с ID = " + id + " не существует в базе данных."));
        }
        Item item = itemServiceImpl.findById(id);
        item.setPretendedToBeDeleted(true);
        itemServiceImpl.update(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
