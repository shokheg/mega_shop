//package com.amr.project.converter;
//
//import com.amr.project.model.dto.ImageDto;
//import com.amr.project.model.dto.ItemDto;
//import com.amr.project.model.dto.ReviewDto;
//import com.amr.project.model.entity.*;
//import com.amr.project.model.entity.report.SalesHistory;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class ItemConverterTest {
//
//    @Autowired
//    private ItemMapper itemMapper;
//
//    @Test
//    public void toDto() {
//        Item item = createItem();
//        ItemDto itemDto = itemMapper.toDto(item);
//        assertEquals(itemDto.getId(), 5L);
//        assertEquals(itemDto.getName(),"Some thing");
//        assertEquals(itemDto.getBasePrice(), new BigDecimal(50));
//        assertEquals(itemDto.getPrice(), new BigDecimal(100));
//        assertEquals(itemDto.getCount(), 1);
//        assertEquals(itemDto.getRating(), 4.9);
//    }
//
//    @Test
//    public void toModel() {
//        ItemDto itemDto = createItemDto();
//        Item item = itemMapper.toEntity(itemDto);
//        assertEquals(item.getId(), 5L);
//        assertEquals(item.getName(),"Some thing");
//        assertEquals(item.getBasePrice(), new BigDecimal(50));
//        assertEquals(item.getPrice(), new BigDecimal(100));
//        assertEquals(item.getCount(), 1);
//        assertEquals(item.getRating(), 4.9);
//    }
//
//    public Item createItem() {
//        return Item.builder()
//                .id(5L)
//                .name("Some thing")
//                .basePrice(new BigDecimal(50))
//                .price(new BigDecimal(100))
//                .count(1)
//                .rating(4.9)
//                .description("")
//                .discount(10)
//                .user(new User())
//                .category(new Category())
//                .cartItem(new CartItem())
//                .images(List.of(createImage()))
//                .reviews(List.of(createReview()))
//                .favorites(List.of(new Favorite()))
//                .orders(List.of(new Order()))
//                .shop(new Shop())
//                .history(List.of(new SalesHistory()))
//                .isModerated(true)
//                .isModerateAccept(true)
//                .moderatedRejectReason("")
//                .isPretendedToBeDeleted(true)
//                .build();
//    }
//
//    public ItemDto createItemDto() {
//
//        return ItemDto.builder()
//                .id(5L)
//                .name("Some thing")
//                .basePrice(new BigDecimal(50))
//                .price(new BigDecimal(100))
//                .count(1)
//                .rating(4.9)
//                .images(List.of(createImageDto()))
//                .reviews(List.of(createReviewDto()))
//                .build();
//    }
//
//    public Image createImage() {
//        return Image.builder()
//                .id(3L)
//                .build();
//    }
//
//    public ImageDto createImageDto() {
//        return ImageDto.builder()
//                .id(3L)
//                .build();
//    }
//
//    public Review createReview() {
//        return Review.builder()
//                .id(5L)
//                .build();
//    }
//
//    public ReviewDto createReviewDto() {
//        return ReviewDto.builder()
//                .id(5L)
//                .build();
//    }
//
//
//}
