package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidItemException;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.CartService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;
    private final UserService userService;
    public final ItemMapper itemMapper;
    private final ItemService itemService;

    @Autowired
    public CartRestController(CartService cartService, UserService userService, ItemMapper itemMapper, ItemService itemService) {
        this.cartService = cartService;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.itemService = itemService;
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
                    .map(cartItem ->
                            itemService.findById(cartItem.getItemInCart().getId()) //todo пробросить количество
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


    @PostMapping()
    public ResponseEntity<List<ItemDto>> addItemsToCart(@RequestBody List<ItemDto> itemDtoList
                                                        //, Principal principal
    ) {
//        String name = principal.getName();
//        User user = userService.findByUserName(name);
//        cartService.findCartByUserID(user.getId);
        for (ItemDto itemDto : itemDtoList) {
            cartService.persist(
                    CartItem.builder()
                            .user(userService.findById(1L))
                            .itemInCart(itemService.findById(itemDto.getId()))
                            .quantity(itemDto.getCount())
                            .build());
        }
        return new ResponseEntity<>(itemDtoList, HttpStatus.CREATED);


    }
}
