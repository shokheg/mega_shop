package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.dto.FeedbackDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Feedback;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.DiscountService;
import com.amr.project.service.abstracts.FeedbackService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DiscountMapper {

    @Autowired
    DiscountService discountService;

    @Mappings({
            @Mapping(target = "shop", expression = "java(getShopByDiscount(discountDto))")
    })
    public abstract Discount toModel (DiscountDto discountDto);

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "shopId", expression = "java(getShopId(discount))")
    })
    public abstract DiscountDto toDto (Discount discount);

    protected Long getShopId (Discount discount) {
        if (discount.getShop() != null) {
            return discount.getShop().getId();
        }
        return null;
    }

    protected Shop getShopByDiscount (DiscountDto discountDto) {
        if (discountDto.getId() != null) {
            return discountService.findById(discountDto.getId()).getShop();
        }
        return null;
    }
}

