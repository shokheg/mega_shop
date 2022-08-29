package com.amr.project.service.impl;

import com.amr.project.model.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemServiceImplTest {

    @Autowired
    ItemServiceImpl itemService;

    @Test
    public void getItemByIdTest() {
        Item item = Item.builder()
                .name("TV")
                .build();
        itemService.persist(item);
        Item testItem = itemService.findById(11L);
        assertThat(item.getName()).isEqualTo(testItem.getName());
    }

    @Test
    public void updateShopByIdTest() {
        Item item = itemService.findById(1L);
        String itemDescription = item.getDescription();

        item.setDescription("Some description");
        itemService.update(item);
        Item testItem = itemService.findById(1L);
        String testItemDescription = testItem.getDescription();

        assertThat(itemDescription).isNotEqualTo(testItemDescription);
    }

    @Test
    public void deleteItemTest() {
        Item item = Item.builder()
                .name("TV")
                .basePrice(BigDecimal.valueOf(100))
                .price(BigDecimal.valueOf(200))
                .count(400)
                .rating(1.1)
                .build();
        itemService.persist(item);
        Item testItem = itemService.findById(11L);
        itemService.delete(itemService.findById(11L));
        Item testItemAfterDelete = itemService.findById(11L);
        assertThat(testItem).isNotEqualTo(testItemAfterDelete);
    }


}