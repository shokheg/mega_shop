package com.amr.project.controller;


import com.amr.project.converter.ShopConverter;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) //аннотация JUnit
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")

public class ShopRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopConverter shopConverter;

    @Test
    public void getShop() throws Exception {
        Shop shop = createShop();
        shopService.persist(shop);

        ResultActions responce = mockMvc.perform(get("/api/shops/{id}", shop.getId()));

        responce.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Home money"))
                .andExpect(jsonPath("$.email").value("zaim@gmail.com"))
                .andExpect(jsonPath("$.phone").value("88005553535"))
                .andExpect(jsonPath("$.description").value("Take our money"))
                .andExpect(jsonPath("$.rating").value(4.7));

        shopService.delete(shop);
    }

    @Test
    public void pretendentToDeleteShop() throws Exception {
        Shop shop = createShop();
        shopService.persist(shop);

        ResultActions responce = mockMvc.perform(patch("/api/shops/pretendedToBeDeleted/{id}", shop.getId()));

        responce.andDo(print())
                .andExpect(status().isOk());
        assertTrue(shopService.findById(shop.getId()).isPretendedToBeDeleted());
        shopService.deleteByIdCascadeIgnore(shop.getId());
    }

    public Shop createShop() {
        return Shop.builder()
                .name("Home money")
                .email("zaim@gmail.com")
                .phone("88005553535")
                .description("Take our money")
                .rating(4.7)
                .isModerateAccept(true)
                .isPretendedToBeDeleted(false)
                .user(User.builder().id(1L).build())
                .build();
    }

    public ShopDto createShopDto() {
        return ShopDto.builder()
                .name("Home money")
                .email("zaim@gmail.com")
                .phone("88005553535")
                .description("Take our money")
                .rating(4.7)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
