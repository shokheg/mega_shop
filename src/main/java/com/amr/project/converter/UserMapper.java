package com.amr.project.converter;

import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import com.amr.project.model.enums.Roles;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected AddressMapper addressMapper;
    @Autowired
    protected UserService userService;
    @Autowired
    protected ImageMapper imageMapper;
    @Autowired
    protected ReviewMapper reviewMapper;
    @Autowired
    protected FeedbackMapper feedbackMapper;
    @Autowired
    protected FavoriteMapper favoriteMapper;
    @Autowired
    protected DiscountMapper discountMapper;

    @Mappings({
            @Mapping(target = "activate", expression = "java(getActivate(userDto))"),
            @Mapping(target = "activationCode", expression = "java(getActivationCode(userDto))"),
            @Mapping(target = "using2FA", expression = "java(getIsUsing2FA(userDto))"),
            @Mapping(target = "identification", expression = "java(getIsIdentification(userDto))"),
            @Mapping(target = "role", expression = "java(getRoles(userDto))"),
            @Mapping(target = "personalData", expression = "java(getPersonalData(userDto))"),
            @Mapping(target = "favorite", expression = "java(getFavorite(userDto))"),
            @Mapping(target = "address", expression = "java(getAddress(userDto))"),
            @Mapping(target = "images", expression = "java(getImages(userDto))"),
            @Mapping(target = "coupons", expression = "java(getCoupons(userDto))"),
            @Mapping(target = "carts", expression = "java(getCartItems(userDto))"),
            @Mapping(target = "orders", expression = "java(getOrders(userDto))"),
            @Mapping(target = "reviews", expression = "java(getReviews(userDto))"),
            @Mapping(target = "shops", expression = "java(getShops(userDto))"),
            @Mapping(target = "discounts", expression = "java(getDiscounts(userDto))"),
            @Mapping(target = "messages", expression = "java(getMessages(userDto))"),
            @Mapping(target = "chats", expression = "java(getChats(userDto))"),
            @Mapping(target = "feedbacks", expression = "java(getFeedbacks(userDto))"),
            @Mapping(target = "items", expression = "java(getItems(userDto))")

    })
    public abstract User toModel(UserDto userDto);

    @Mappings({
            @Mapping (target = "addresses", expression = "java(getAddressesDto(user))"),
            @Mapping (target = "image", expression = "java(getImagesDto(user))"),
            @Mapping (target = "couponIds", expression = "java(getCouponIds(user))"),
            @Mapping (target = "orderIds", expression = "java(getOrderIds(user))"),
            @Mapping (target = "reviews", expression = "java(getReviewsDto(user))"),
            @Mapping (target = "feedbacks", expression = "java(getFeedbacksDto(user))"),
            @Mapping (target = "shopIds", expression = "java(getShopsIds(user))"),
            @Mapping (target = "favorite", expression = "java(getFavoriteDto(user))"),
            @Mapping (target = "discounts", expression = "java(getDiscountsDto(user))"),
            @Mapping (target = "chatIds", expression = "java(getChatsId(user))"),
    })
    public abstract UserDto toDto(User user);

    //methods for toDto
    protected List<AddressDto> getAddressesDto(User user) {
        if (user.getAddress() != null) {
            return List.of(addressMapper.toDto(user.getAddress()));
        }
        return null;
    }

    protected ImageDto getImagesDto(User user) {
        if (user.getImages() != null) {
            return imageMapper.toImageDtos(user.getImages()).stream().filter(ImageDto::getIsMain).findFirst().orElse(null);
        }
        return null;
    }

    protected List<Long> getCouponIds(User user) {
        if (user.getCoupons() != null) {
            return user.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList());
        }
        return null;
    }

    protected List<Long> getOrderIds(User user) {
        if (user.getOrders() != null) {
            return user.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        }
        return null;
    }

    protected List<ReviewDto> getReviewsDto(User user) {
        if (user.getReviews() != null) {
            return user.getReviews().stream().map(reviewMapper::toDto).collect(Collectors.toList());
        }
        return null;
    }

    protected List<FeedbackDto> getFeedbacksDto(User user) {
        if (user.getFeedbacks() != null) {
            return user.getFeedbacks().stream().map(feedbackMapper::toDto).collect(Collectors.toList());
        }
        return null;
    }

    protected List<Long> getShopsIds(User user) {
        if (user.getShops() != null) {
            return user.getShops().stream().map(Shop::getId).collect(Collectors.toList());
        }
        return null;
    }

    protected FavoriteDto getFavoriteDto(User user) {
        if (user.getFavorite() != null) {
            return favoriteMapper.toDto(user.getFavorite());
        }
        return null;
    }

    protected List<DiscountDto> getDiscountsDto(User user) {
        if (user.getDiscounts() != null) {
            return user.getDiscounts().stream().map(discountMapper::toDto).collect(Collectors.toList());
        }
        return null;
    }

    protected Set<Long> getChatsId(User user) {
        if (user.getChats() != null) {
            return user.getChats().stream().map(Chat::getId).collect(Collectors.toSet());
        }
        return null;
    }

    //methods for toModel
    protected Boolean getActivate(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).isActivate();
        }
        return null;
    }

    protected String getActivationCode(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getActivationCode();
        }
        return null;
    }

    protected Boolean getIsUsing2FA(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).isUsing2FA();
        }
        return null;
    }

    protected Boolean getIsIdentification(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).isIdentification();
        }
        return null;
    }

    protected Roles getRoles(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getRole();
        }
        return null;
    }

    protected PersonalData getPersonalData(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getPersonalData();
        }
        return null;
    }

    protected Favorite getFavorite(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getFavorite();
        }
        return null;
    }

    protected Address getAddress(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getAddress();
        }
        return null;
    }

    protected List<Image> getImages(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getImages();
        }
        return null;
    }

    protected List<Coupon> getCoupons(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getCoupons();
        }
        return null;
    }

    protected List<CartItem> getCartItems(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getCarts();
        }
        return null;
    }

    protected List<Order> getOrders(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getOrders();
        }
        return null;
    }

    protected List<Review> getReviews(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getReviews();
        }
        return null;
    }

    protected Set<Shop> getShops(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getShops();
        }
        return null;
    }

    protected Set<Discount> getDiscounts(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getDiscounts();
        }
        return null;
    }

    protected Set<Message> getMessages(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getMessages();
        }
        return null;
    }

    protected Set<Chat> getChats(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getChats();
        }
        return null;
    }

    protected Set<Feedback> getFeedbacks(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getFeedbacks();
        }
        return null;
    }

    protected List<Item> getItems(UserDto userDto) {
        if (userService.existsById(userDto.getId())) {
            return userService.findById(userDto.getId()).getItems();
        }
        return null;
    }
















}
