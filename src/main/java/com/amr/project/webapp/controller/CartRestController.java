package com.amr.project.webapp.controller;

import com.amr.project.converter.CartItemConverter;
import com.amr.project.converter.ItemMapper;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidItemException;
import com.amr.project.exception.OAuth2Exception;
import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.CartService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.service.impl.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Контроллер пользовательской корзины", description = "Контроллер пользовательской корзины. Методы доступны для авторизованного пользователя. " +
        "В случае доступа без авторизации или с неверным JWT токеном возвращает 403 \"Ошибка доступа\". GET метод возвращает полноценные ItemDTO из пользовательской корзины, в т.ч. с изображениями. " +
        "POST метод имеет валидацию входного ItemDTO. Может принимать на вход список товаров для добавления в корзину. В таком случае необходимо чтобы в списке отсутствовали товары с одинаковым Id, иначе товары не будут добавлены." +
        "Обязательным полем являются только ID и discount. Discount должен проходить валидацию на стороне сервера(не реализовано)." +
        "В случае добавления в корзину товаров с одинаковым ID, их количества будут суммированы. DELETE метод удаляет товары из корзины по ID. Поля количества и другие игнорируются. Метод может принимать на вход список товаров." +
        " Если корзина уже пуста, возвращает соответствующий ответ с кодом 400.  ")
public class CartRestController {

    private final CartService cartService;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final CartItemConverter cartItemConverter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    public CartRestController(CartService cartService, UserService userService, ItemMapper itemMapper, ItemService itemService, CartItemConverter cartItemConverter, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.cartService = cartService;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.itemService = itemService;
        this.cartItemConverter = cartItemConverter;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping("")
    @ApiOperation(value = "Получить товары в корзине для текущего авторизованного пользователя")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Корзина пуста."),
                    @ApiResponse(code = 403, message = "Ошибка доступа.")
            }
    )
    public ResponseEntity<List<ItemDto>> getUserCart(Principal principal) {
        if (principal == null) {
            Date date = new Date();
            throw new OAuth2Exception(new ErrorMessage(403, date, "Ошибка доступа"
                    , "Отказано в доступе"));
        }

        String name = principal.getName();
        User user = userService.findUserByUsername(name);

        // Получаем список всех cart items у которых заданный юзер.
        List<ItemDto> itemDtoList;
        if (!cartService.findCartItemsByUserID(user.getId()).isEmpty()) {
            itemDtoList = cartService
                    .findCartItemsByUserID(user.getId())
                    .stream()
                    .map(cartItem -> {
                                Item item = itemService.findById(cartItem.getItemInCart().getId());
                                item.setCount(cartItem.getQuantity());
                                return item;
                            }
                    )
                    .peek(System.out::println)
                    .map(itemMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(400, date, "Что-то пошло не так."
                    , "Не могу найти корзину или она пуста"));
        }
        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @DeleteMapping("")
    @ApiOperation(value = "Удалить товар из корзины по ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Ошибка валидации входных параметров."),
                    @ApiResponse(code = 403, message = "Ошибка доступа.")
            }
    )
    public ResponseEntity<List<ItemDto>> removeItemsFromCart(@Valid @RequestBody List<ItemDto> itemsToDelete, Principal principal) {
        if (principal == null) {
            Date date = new Date();
            throw new OAuth2Exception(new ErrorMessage(403, date, "Ошибка доступа"
                    , "Отказано в доступе"));
        }

        String name = principal.getName();
        User user = userService.findUserByUsername(name);

        if (!cartService.findCartItemsByUserID(user.getId()).isEmpty()) {
            for (ItemDto itemToDelete : itemsToDelete) {
                cartService.deleteCartItemsByItemID(itemToDelete.getId());
            }
        } else {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(400, date, "Что-то пошло не так."
                    , "Возможно товары уже удалены из корзины"));
        }
        return new ResponseEntity<>(itemsToDelete, HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "Добавить товары в корзину по ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Ошибка валидации входных параметров."),
                    @ApiResponse(code = 403, message = "Ошибка доступа.")
            }
    )
    public ResponseEntity<List<CartItemDto>> addItemsToCart(@Valid @RequestBody List<ItemDto> itemDtoList, Principal principal) {
        if (principal == null) {
            Date date = new Date();
            throw new OAuth2Exception(new ErrorMessage(403, date, "Ошибка доступа"
                    , "Отказано в доступе"));
        }

        String name = principal.getName();
        User user = userService.findUserByUsername(name);

        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem;

        for (ItemDto itemDto : itemDtoList) {
            if (cartService.findCartItemsByItemID(itemDto.getId()).isEmpty()) {  //if cart item exist, summing quantity
                cartItem = CartItem.builder()
                        .user(userService.findById(user.getId()))
                        .itemInCart(itemService.findById(itemDto.getId()))
                        .quantity(itemDto.getCount())
                        .build();
            } else {
                int i = cartService.getCartItemQuantityByID(itemDto.getId());
                cartService.deleteCartItemsByItemID(itemDto.getId());
                cartItem = CartItem.builder()
                        .user(userService.findById(user.getId()))
                        .itemInCart(itemService.findById(itemDto.getId()))
                        .quantity(i + itemDto.getCount())
                        .build();

            }
            cartService.persist(cartItem);
            cartItemList.add(cartItem);
        }
        return new ResponseEntity<>(cartItemConverter.toDto(cartItemList), HttpStatus.CREATED);
    }
}
