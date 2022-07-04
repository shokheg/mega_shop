package com.amr.project.service.impl;

import com.amr.project.model.entity.Shop;
import com.amr.project.service.impl.ShopServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShopServiceImplTest {

    private final ShopServiceImpl shopService;

    @Autowired
    public ShopServiceImplTest(ShopServiceImpl shopService) {
        this.shopService = shopService;
    }

    @Test
    void getShopByIdTest() {
        Shop shop = Shop.builder()
                .name("MvideoTest")
                .email("test@gmail.com")
                .build();

        shopService.persist(shop);

        Shop actualShop = shopService.findById(3L);

        Assertions.assertThat(shop).isEqualTo(actualShop);
    }

    @Test
    @Transactional
    void updateShopByIdTest() {
        Shop testShop = shopService.findById(1L);
        String actualEmail = testShop.getEmail();

        testShop.setEmail("test@gmail.com");
        shopService.update(testShop);
        Shop shop = shopService.findById(1L);
        String testEmail = shop.getEmail();

        Assertions.assertThat(actualEmail).isNotEqualTo(testEmail);
    }

    @Test
    @Transactional
    void deleteShopTest() {
        Shop shopTest = Shop.builder().name("MvideoTest").email("test1@gmail.com").build();
        shopService.persist(shopTest);
        List<Shop> shopList1 = shopService.findAll();

        shopService.delete(shopTest);
        List<Shop> shopList2 = shopService.findAll();

        Assertions.assertThat(shopList1.size()).isNotEqualTo(shopList2.size());
        Assertions.assertThat(shopList2).doesNotContain(shopTest);
    }

}
