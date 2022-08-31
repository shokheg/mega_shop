package com.amr.project.converter;

import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shokhalevich
 */
@Component
@Mapper(componentModel = "spring")
public abstract class CartItemConverter {

    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;

    @Mappings({
            @Mapping(target = "userId", expression = "java(cartItem.getUser().getId())"),
            @Mapping(target = "itemInCart", expression = "java(cartItem.getItemInCart().getId())")
    })
    public abstract CartItemDto toDto(CartItem cartItem);
    @Mappings({
            @Mapping(target = "user", expression = "java(userService.findById(cartItemDto.getUserId()))"),
            @Mapping(target = "itemInCart", expression = "java(itemService.findById(cartItemDto.getItemInCart()))")
    })
    public abstract CartItem toEntity(CartItemDto cartItemDto);

    @Mappings({
            @Mapping(target = "userId", expression = "java(cartItem.getUser().getId())"),
            @Mapping(target = "itemInCart", expression = "java(cartItem.getItemInCart().getId())")
    })
    public abstract List<CartItemDto> toDto(List<CartItem> cartItemList);
    @Mappings({
            @Mapping(target = "user", expression = "java(userService.findById(cartItemDto.getUserId()))"),
            @Mapping(target = "itemInCart", expression = "java(itemService.findById(cartItemDto.getItemInCart()))")
    })
    public abstract List<CartItem> toEntity(List<CartItemDto> cartItemDtoList);


}
