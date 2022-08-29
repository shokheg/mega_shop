package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemMapperTest {


    @Autowired
    ItemMapper itemMapper;

    @Test
    public void entityToDtoTest() {
        Item item = Item.builder()
                .id(1L)
                .name("Some Item")
                .basePrice(BigDecimal.valueOf(1234L))
                .price(BigDecimal.valueOf(432L))
                .count(15)
                .rating(1.1)
                .description("Some Description")
                .images(List.of(Image.builder()
                        .id(1l)
                        .build()))
                .reviews((List.of(Review.builder()
                        .id(1L)
                        .build())))
                .isModerateAccept(true)
                .isPretendedToBeDeleted(false)
                .isModerated(true)
                .moderatedRejectReason("ok")
                .build();

        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Some Item")
                .basePrice(BigDecimal.valueOf(1234L))
                .price(BigDecimal.valueOf(432L))
                .count(15)
                .rating(1.1)
                .description("Some Description")
                .images(List.of(ImageDto.builder()
                        .id(1l)
                        .build()))
                .reviews((List.of(ReviewDto.builder()
                        .id(1L)
                        .build())))
                .build();

        assertEquals(itemMapper.toDto(item),itemDto);
    }
}
