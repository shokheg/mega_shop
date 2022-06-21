package com.amr.project.converter;

import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReviewService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
    @Autowired
    ReviewService reviewService;

    @Mappings({
            @Mapping(source = "review.item.id", target = "itemId"),
            @Mapping(source = "review.user.id", target = "userId"),
            @Mapping(source = "review.user.username", target = "userName"),
            @Mapping(source = "review.shop.id", target = "shopId")
    })
    public abstract ReviewDto toDto(Review review);

    @Mappings({
            @Mapping(target = "user", expression = "java(getUser(reviewDto))"),
            @Mapping(target = "shop", expression = "java(getShop(reviewDto))"),
            @Mapping(target = "item", expression = "java(getItem(reviewDto))"),
            @Mapping(target = "review.isModerated", expression = "java(getIsModerated(reviewDto))"),
            @Mapping(target = "review.isModerateAccept", expression = "java(getIsModerateAccept(reviewDto))"),
            @Mapping(target = "moderatedRejectReason", expression = "java(getModeratedRejectReason(reviewDto))")
    })
    public abstract Review toEntity(ReviewDto reviewDto);

        protected User getUser(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).getUser();
            }
            return null;
        }

        protected Shop getShop(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).getShop();
            }
            return null;
        }

        protected Item getItem(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).getItem();
            }
            return null;
        }

        protected boolean getIsModerated(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).isModerated();
            }
            return false;
        }
        protected boolean getIsModerateAccept(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).isModerateAccept();
            }
            return false;
        }

        protected String getModeratedRejectReason(ReviewDto reviewDto) {
            if (reviewDto.getId() != null) {
                return reviewService.findById(reviewDto.getId()).getModeratedRejectReason();
            }
            return null;
        }

    public abstract List<ReviewDto> toReviewDtos(List<Review> reviews);
    public abstract List<Review> toReviews(List<ReviewDto> reviewDtos);


}

