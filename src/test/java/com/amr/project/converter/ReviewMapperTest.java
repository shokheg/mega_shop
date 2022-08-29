package com.amr.project.converter;


import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewMapperTest {

    @Autowired
    ReviewMapper reviewMapper;

    @Mock
    User user;

    @Mock
    Shop shop;

    @Mock()
    Item item;

    @Test
    public void entityToDtoTest() {
        Review review = Review.builder()
                .id(1L)
                .dignity("Some dignity")
                .flaw("Some flaw")
                .text("Some text")
                .rating(15)
                .isModerated(true)
                .isModerateAccept(true)
                .moderatedRejectReason("Some reason")
                .user(user)
                .shop(shop)
                .item(item)
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(1L)
                .dignity("Some dignity")
                .flaw("Some flaw")
                .text("Some text")
                .rating(15)
                .itemId(item.getId())
                .userId(user.getId())
                .userName(user.getUsername())
                .shopId(shop.getId())
                .build();

        assertEquals(reviewMapper.toDto(review),reviewDto);
    }
}
