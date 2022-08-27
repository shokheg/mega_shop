package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.*;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.service.abstracts.ItemService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class, ReviewMapper.class})
public abstract class ItemMapper {
    @Autowired
    ItemService itemService;

    public abstract ItemDto toDto(Item item);

    @Mappings({
            @Mapping(target = "discount", expression = "java(getDiscount(itemDto))"),
            @Mapping(target = "category", expression = "java(getCategory(itemDto))"),
            @Mapping(target = "user", expression = "java(getUser(itemDto))"),
            @Mapping(target = "cartItem", expression = "java(getCartItem(itemDto))"),
            @Mapping(target = "favorites", expression = "java(getFavorites(itemDto))"),
            @Mapping(target = "orders", expression = "java(getOrders(itemDto))"),
            @Mapping(target = "shop", expression = "java(getShop(itemDto))"),
            @Mapping(target = "history", expression = "java(getHistory(itemDto))"),
            @Mapping(target = "item.isModerated", expression = "java(getIsModerated(itemDto))"),
            @Mapping(target = "item.isModerateAccept", expression = "java(getIsModerateAccept(itemDto))"),
            @Mapping(target = "moderatedRejectReason", expression = "java(getModeratedRejectReason(itemDto))"),
            @Mapping(target = "item.isPretendedToBeDeleted", expression = "java(getIsPretendedToBeDeleted(itemDto))"),
            @Mapping(target = "images", expression = "java(getImages(itemDto))"),
            @Mapping(target = "reviews", expression = "java(getReviews(itemDto))")
    })
    public abstract Item toEntity(ItemDto itemDto);

    public abstract List<ItemDto> toItemsDTO(List<Item> items);

    public abstract List<Item> toItems(List<ItemDto> itemsDto);

    protected Integer getDiscount(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getDiscount();
        }
        return 0;
    }

    protected List<Image> getImages(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getImages();
        }
        return null;
    }

    protected List<Review> getReviews(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getReviews();
        }
        return null;
    }

    protected Category getCategory(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getCategory();
        }
        return null;
    }

    protected User getUser(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getUser();
        }
        return null;
    }

    protected List<CartItem> getCartItem(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getCartItem();
        }
        return null;
    }

    protected List<Favorite> getFavorites(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getFavorites();
        }
        return null;
    }

    protected List<Order> getOrders(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getOrders();
        }
        return null;
    }

    protected Shop getShop(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getShop();
        }
        return null;
    }

    protected List<SalesHistory> getHistory(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getHistory();
        }
        return null;
    }

    protected boolean getIsModerated(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).isModerated();
        }
        return false;
    }

    protected boolean getIsModerateAccept(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).isModerateAccept();
        }
        return false;
    }

    protected String getModeratedRejectReason(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).getModeratedRejectReason();
        }
        return null;
    }

    protected boolean getIsPretendedToBeDeleted(ItemDto itemDto) {
        if (itemDto.getId() != null) {
            return itemService.findById(itemDto.getId()).isPretendedToBeDeleted();
        }
        return false;
    }

}
