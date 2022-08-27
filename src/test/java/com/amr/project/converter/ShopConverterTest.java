//package com.amr.project.converter;
//
//import com.amr.project.model.dto.*;
//import com.amr.project.model.entity.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class ShopConverterTest {
//
//    @Autowired
//    private ShopConverter shopConverter;
//
//    @Test
//    public void entityToDtoTest() {
//        Shop shop = Shop.builder()
//                .id(1L)
//                .name("Магазин")
//                .email("shop@gmail.com")
//                .phone("8(915)888-85-96")
//                .description("Магазин продуктов")
//                .count(1)
//                .rating(2.5)
//                .items(List.of(Item.builder()
//                        .id(1L)
//                        .name("Молоко")
//                        .build()))
//                .reviews(List.of(Review.builder()
//                        .id(1L)
//                        .build()))
//                .logo(Image.builder()
//                        .id(1L)
//                        .build())
//                .user(User.builder()
//                        .id(1L)
//                        .build())
//                .cartItem(CartItem.builder()
//                        .id(1L)
//                        .build())
//                .feedbacks(List.of(Feedback.builder()
//                        .id(1L)
//                        .build()))
//                .discounts(List.of(Discount.builder()
//                        .id(1L)
//                        .build()))
//                .favorites(List.of(Favorite.builder()
//                        .id(1L)
//                        .build()))
//                .address(Address.builder()
//                        .id(1L)
//                        .city(City.builder()
//                                .id(1L)
//                                .build())
//                        .build())
//                .coupons(List.of(Coupon.builder()
//                        .id(1L)
//                        .build()))
//                .isModerateAccept(true)
//                .isPretendedToBeDeleted(false)
//                .isModerated(true)
//                .moderatedRejectReason("1")
//                .build();
//
//        ShopDto shopDto = ShopDto.builder()
//                .id(1L)
//                .name("Магазин")
//                .description("Магазин продуктов")
//                .email("shop@gmail.com")
//                .phone("8(915)888-85-96")
//                .rating(2.5)
//                .reviews(List.of(ReviewDto.builder()
//                        .id(1L)
//                        .build()))
//                .logo(ImageDto.builder()
//                        .id(1L)
//                        .build())
//                .discounts(List.of(DiscountDto.builder()
//                        .id(1L)
//                        .build()))
//                .location(CityDto.builder()
//                        .id(1L)
//                        .build())
//                .userId(1L)
//                .couponIds(Arrays.asList(1L))
//                .build();
//
//        assertEquals(shopConverter.entityToDto(shop), shopDto);
//    }
//}
