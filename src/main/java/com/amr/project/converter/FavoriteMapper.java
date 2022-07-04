package com.amr.project.converter;

import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.FavoriteService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class FavoriteMapper {

    @Autowired
    FavoriteService favoriteService;
    @Autowired
    ShopConverter shopConverter;
    @Autowired
    ItemMapper itemMapper;


    @Mappings({
            @Mapping(target = "shops", expression = "java(getShopsByFavorite(favoriteDto))"),
            @Mapping(target = "items", expression = "java(getItemsByFavorite(favoriteDto))"),
            @Mapping(target = "user", expression = "java(getUserByFavorite(favoriteDto))")
    })
    protected abstract Favorite toModel (FavoriteDto favoriteDto);

    @Mappings({
            @Mapping(target = "items", expression = "java(getItemsDtoByFavorite(favorite))"),
            @Mapping(target = "shops", expression = "java(getShopsDtoByFavorite(favorite))"),
            @Mapping(target = "userId", expression = "java(getUserIdByFavorite(favorite))")
    })
    protected abstract FavoriteDto toDto (Favorite favorite);

    protected List<ItemDto> getItemsDtoByFavorite (Favorite favorite) {
        if (favorite.getItems() != null) {
            return favorite.getItems().stream().map(itemMapper::toDto).collect(Collectors.toList());
        }
        return null;
    }

    protected List<ShopDto> getShopsDtoByFavorite (Favorite favorite) {
        if (favorite.getShops() != null) {
            return favorite.getShops().stream().map(shopConverter::entityToDto).collect(Collectors.toList());
        }
        return null;
    }

    protected Long getUserIdByFavorite (Favorite favorite) {
        if (favorite.getUser() != null) {
            return favorite.getUser().getId();
        }
        return null;
    }

    protected List<Item> getItemsByFavorite (FavoriteDto favoriteDto) {
        if (favoriteDto.getItems() != null) {
            return favoriteDto.getItems().stream().map(itemMapper::toEntity).collect(Collectors.toList());
        }
        return null;
    }

    protected List<Shop> getShopsByFavorite (FavoriteDto favoriteDto) {
        if (favoriteDto.getShops() != null) {
            return favoriteDto.getShops().stream().map(shopConverter::dtoToEntity).collect(Collectors.toList());
        }
        return null;
    }

    protected User getUserByFavorite (FavoriteDto favoriteDto) {
        if (favoriteDto.getId() != null) {
            return favoriteService.findById(favoriteDto.getId()).getUser();
        }
        return null;

    }
}
