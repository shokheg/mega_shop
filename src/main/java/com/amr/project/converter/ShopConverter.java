package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.CouponService;
import com.amr.project.service.abstracts.DiscountService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public abstract class ShopConverter {

    @Autowired
    protected CityConverter cityConverter;
    @Autowired
    protected ShopService shopService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected CouponService couponService;
    @Autowired
    protected DiscountConverter discountConverter;
    @Autowired
    protected DiscountService discountService;

    @Mappings({
            @Mapping(target = "user", expression = "java(userService.findById(shopDto.getUserId()))"),
            @Mapping(target = "items", expression = "java(getItems(shopDto))"),
            @Mapping(target = "cartItem", expression = "java(getCartItem(shopDto))"),
            @Mapping(target = "feedbacks", expression = "java(getFeedbacks(shopDto))"),
            @Mapping(target = "favorites", expression = "java(getFavorites(shopDto))"),
//            @Mapping(target = "moderated", expression = "java(getIsModerated(shopDto))"),
//            @Mapping(target = "moderateAccept", expression = "java(getIsModerateAccept(shopDto))"),
            @Mapping(target = "moderatedRejectReason", expression = "java(getModeratedRejectReason(shopDto))"),
//            @Mapping(target = "pretendedToBeDeleted", expression = "java(getIsPretendedToBeDeleted(shopDto))"),
            @Mapping(target = "coupons", expression = "java(getCoupons(shopDto))"),
            @Mapping(target = "discounts", expression = "java(getDiscounts(shopDto))"),
            @Mapping(target = "reviews", expression = "java(getReviews(shopDto))")
    })
    public abstract Shop dtoToEntity(ShopDto shopDto);

    @Mappings({
            @Mapping(target = "userId", expression = "java(shop.getUser().getId())"),
            @Mapping(target = "location", expression = "java(getCityDto(shop))"),
            @Mapping(target = "couponIds", expression = "java(getCouponIds(shop))"),
            @Mapping(target = "discounts", expression = "java(discountConverter.entityToDto(shop.getDiscounts()))")
    })
    public abstract ShopDto entityToDto(Shop shop);

    public abstract List<Shop> dtoToEntity(List<ShopDto> shopDtoList);

    public abstract List<ShopDto> entityToDto(List<Shop> shopList);

    protected CityDto getCityDto(Shop shop) {
        if (shop.getAddress() != null) {
            if (shop.getAddress().getCity() != null) {
                return cityConverter.entityToDto(shop.getAddress().getCity());
            }
        }
        return null;
    }

    protected List<Item> getItems(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).getItems();
        }
        return null;
    }

    protected CartItem getCartItem(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).getCartItem();
        }
        return null;
    }

    protected List<Feedback> getFeedbacks(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).getFeedbacks();
        }
        return null;
    }

    protected List<Favorite> getFavorites(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).getFavorites();
        }
        return null;
    }

    protected boolean getIsModerated(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).isModerated();
        }
        return false;
    }

    protected boolean getIsModerateAccept(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).isModerateAccept();
        }
        return false;
    }

    protected String getModeratedRejectReason(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).getModeratedRejectReason();
        }
        return null;
    }

    protected boolean getIsPretendedToBeDeleted(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            return shopService.findById(shopDto.getId()).isPretendedToBeDeleted();
        }
        return false;
    }

    protected List<Coupon> getCoupons(ShopDto shopDto) {
        if (shopDto.getCouponIds() != null) {
            return couponService.findCouponsByIds(shopDto.getCouponIds());
        }
        return null;
    }

    protected List<Long> getCouponIds(Shop shop) {
        List<Coupon> coupons = shop.getCoupons();
        if (coupons != null) {
            List<Long> couponIds = new ArrayList<>();
            for (Coupon coupon : coupons) {
                couponIds.add(coupon.getId());
            }
            return couponIds;
        }

        return null;
    }

    protected List<Discount> getDiscounts(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            Shop shop = shopService.findById(shopDto.getId());
            return shop.getDiscounts();
        }
        return null;
    }

    protected List<Review> getReviews(ShopDto shopDto) {
        if (shopDto.getId() != null) {
            Shop shop = shopService.findById(shopDto.getId());
            return shop.getReviews();
        }
        return null;
    }
}
