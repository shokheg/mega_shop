package com.amr.project.webapp.controller;

import com.amr.project.converter.CartItemConverter;
import com.amr.project.converter.ItemMapper;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidItemException;
import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.CartService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final CartItemConverter cartItemConverter;


    @Autowired
    public CartRestController(CartService cartService, UserService userService, ItemMapper itemMapper, ItemService itemService, CartItemConverter cartItemConverter) {
        this.cartService = cartService;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.itemService = itemService;
        this.cartItemConverter = cartItemConverter;
    }

    @GetMapping("")
    //todo переделать на Principal после подключения секьюрити UserDetails,
    public ResponseEntity<List<ItemDto>> getUserCart(Principal principal) {
//        String name = principal.getName();
//        User user = userService.findByUserName(name);
//        cartService.findCartByUserID(user.getId);
        // Получаем список всех cart items у которых заданный юзер.
        List<ItemDto> itemDtoList;
        if ((cartService.findCartItemsByUserID(1L)) != null) {
            itemDtoList = cartService
                    .findCartItemsByUserID(1L)
                    .stream()
                    .map(cartItem -> {
                                Item item = itemService.findById(cartItem.getItemInCart().getId());
                                item.setCount(cartItem.getQuantity());
                                return item;
                            } //todo сделать чтобы плюсовалось количество выводимых одинаковых сущностей
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

    @DeleteMapping("")   //todo переделать на принципала
    //@Valid  необходимо обработать исключение валидатора
    public ResponseEntity<List<ItemDto>> removeItemsFromCart(
            @RequestBody List<ItemDto> itemsToDelete
            //, Principal principal
    ) {
//        String name = principal.getName();
//        User user = userService.findByUserName(name);
//        cartService.findCartByUserID(user.getId);
        //todo добавить исключения если невалидный входной ДТО и если пустой то БЭД реквест
        List<CartItem> cartItemsList;
        if (cartService.findCartItemsByUserID(1L) != null) {
            for (ItemDto itemToDelete : itemsToDelete) {
                cartService.deleteByIdCascadeIgnore(itemToDelete.getId());
            }
        } else {
            Date date = new Date();
            throw new InvalidItemException(new ErrorMessage(400, date, "Что-то пошло не так."
                    , "Возможно товары уже удалены из корзины"));
        }
        return new ResponseEntity<>(itemsToDelete, HttpStatus.OK);
    }
/*
Нужна валидация входящих дто. Сейчас при отсутствии ID выкидывает 500 ответ, при отсутствии discount выбрасывает 400.
Позволяет добавлять товар с количеством "0" и без price...разобраться
*/

    @PostMapping("")
    public ResponseEntity<List<CartItemDto>> addItemsToCart(@RequestBody List<ItemDto> itemDtoList
                                                            //, Principal principal
    ) {
//        String name = principal.getName();
//        User user = userService.findByUserName(name);
//        cartService.findCartByUserID(user.getId);
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem cartItem;
        for (ItemDto itemDto : itemDtoList) {
            cartItem = CartItem.builder()
                    .user(userService.findById(1L))
                    .itemInCart(itemService.findById(itemDto.getId()))
                    .quantity(itemDto.getCount())
                    .build();
            cartService.persist(cartItem);
            cartItemList.add(cartItem);
        }
        return new ResponseEntity<>(cartItemConverter.toDto(cartItemList), HttpStatus.CREATED);


    }
}
