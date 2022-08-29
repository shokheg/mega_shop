package com.amr.project.controller;

import com.amr.project.converter.ShopConverter;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.InvalidShopException;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.webapp.controller.ShopRestController;
import org.assertj.core.api.Assertions;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ShopRestController.class)
@AutoConfigureMockMvc
public class ShopRestControllerModuleTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShopService shopService;
    @MockBean
    private ShopConverter shopConverter;

    @Test
    public void getShopPositiveTest() throws Exception {
        when(shopService.existsById(1L)).thenReturn(true);
        when(shopService.findById(1L)).thenReturn(getShop0());
        mockMvc.perform(get("/api/shops/{id}","1"))
                .andExpect(status().isOk());
        verify(shopService, times(1)).existsById(1L);
        verify(shopService, times(1)).findById(1L);
    }

    @Test
    public void getShopNegativeTest() throws Exception {
        when(shopService.existsById(1L)).thenReturn(false);
        when(shopService.findById(1L)).thenReturn(getShop0());

        Assertions
                .assertThatThrownBy(
                        () -> mockMvc.perform(MockMvcRequestBuilders.get("/api/shops/{id}","1").contentType(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(InvalidShopException.class).hasMessageContaining("Request processing failed; nested exception is com.amr.project.exception.InvalidShopException");

        verify(shopService, times(1)).existsById(1L);
        verify(shopService, times(0)).findById(1L);
    }

    @Test
    public void getAllShopAdmin() throws Exception {
        when(shopService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/shops/admin"))
                .andExpect(status().isOk());
        verify(shopService, times(1)).findAll();
    }

    @Test
    public void getAllShopUser() throws Exception {
        when(shopService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/shops/user"))
                .andExpect(status().isOk());
        verify(shopService, times(1)).findAllShopForUser();
    }

    @Test
    public void addShopPositiveTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();

        when(shopService.persist(shop)).thenReturn(shop);
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);

        mockMvc.perform(post("/api/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shopDto)))
                .andExpect(status().isCreated());
        verify(shopService, times(1)).persist(shop);
    }

    @Test
    public void addShopNegativeTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();
        shopDto.setId(1L);

        when(shopService.persist(shop)).thenReturn(shop);
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);

        Assertions
                .assertThatThrownBy(
                        () -> mockMvc.perform(post("/api/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shopDto)))).hasCause(new InvalidShopException(new ErrorMessage(204, new Date(), "", "")));
        verify(shopService, times(0)).persist(shop);
    }

    @Test
    public void updateShopPositiveTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();
        shopDto.setId(1L);

        doNothing().when(shopService).update(shop);
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);

        mockMvc.perform(put("/api/shops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shopDto)))
                .andExpect(status().isOk());
        verify(shopService, times(1)).update(shop);
    }

    @Test
    public void updateShopNegativeTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();

        doNothing().when(shopService).update(shop);
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);
        Assertions
                .assertThatThrownBy(
                        () -> mockMvc.perform(put("/api/shops")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(shopDto)))).hasCause(new InvalidShopException(new ErrorMessage(204, new Date(), "", "")));
        verify(shopService, times(0)).update(shop);
    }

    @Test
    public void deleteShopPositiveTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();
        shopDto.setId(1L);

        doNothing().when(shopService).deleteByIdCascadeEnable(shop.getId());
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);
        when(shopService.existsById(shopDto.getId())).thenReturn(true);

        mockMvc.perform(delete("/api/shops/{id}","1"))
                .andExpect(status().isOk());
        verify(shopService, times(1)).existsById(shopDto.getId());
        verify(shopService, times(1)).deleteByIdCascadeEnable(shop.getId());
    }

    @Test
    public void deleteShopNegativeTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();

        doNothing().when(shopService).deleteByIdCascadeEnable(1L);
        when(shopConverter.dtoToEntity(shopDto)).thenReturn(shop);
        when(shopService.existsById(1L)).thenReturn(false);
        Assertions
                .assertThatThrownBy(
                        () -> mockMvc.perform(delete("/api/shops/{id}","1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(shopDto)))).hasCause(new InvalidShopException(new ErrorMessage(204, new Date(), "", "")));
        verify(shopService, times(1)).existsById(1L);
        verify(shopService, times(0)).deleteByIdCascadeEnable(1L);
    }

    @Test
    public void pretendedShopPositiveTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();
        shopDto.setId(1L);

        when(shopService.existsById(1L)).thenReturn(true);
        when(shopService.findById(1L)).thenReturn(shop);

        mockMvc.perform(patch("/api/shops/pretendedToBeDeleted/{id}","1"))
                .andExpect(status().isOk());
        verify(shopService, times(1)).existsById(1L);
        verify(shopService, times(1)).findById(1L);
        assertTrue(shop.isPretendedToBeDeleted());
    }

    @Test
    public void pretendedShopNegativeTest() throws Exception {
        Shop shop = getShop0();
        ShopDto shopDto = getShopDto();
        shopDto.setId(1L);

        when(shopService.existsById(1L)).thenReturn(false);
        when(shopService.findById(1L)).thenReturn(shop);

        Assertions
                .assertThatThrownBy(
                        () -> mockMvc.perform(patch("/api/shops/pretendedToBeDeleted/{id}","1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(shopDto)))).hasCause(new InvalidShopException(new ErrorMessage(204, new Date(), "", "")));
        verify(shopService, times(1)).existsById(1L);
        verify(shopService, times(0)).findById(1L);
        assertFalse(shop.isPretendedToBeDeleted());
    }




    public Shop getShop0() {
        return Shop.builder()
                .id(1L)
                .name("Home money")
                .email("zaim@gmail.com")
                .phone("88005553535")
                .description("Take our money")
                .count(1)
                .rating(4.7)
                .isModerateAccept(true)
                .isPretendedToBeDeleted(false)
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();
    }

    public Shop getShop1() {
        return Shop.builder()
                .id(2L)
                .name("Home money2")
                .email("zaim2@gmail.com")
                .phone("88005555555")
                .description("Take our money2")
                .count(1)
                .rating(4.8)
                .isModerateAccept(true)
                .isPretendedToBeDeleted(false)
                .user(User.builder()
                        .id(2L)
                        .build())
                .build();
    }

    public ShopDto getShopDto() {
        return ShopDto.builder()
                .id(null)
                .name("Home money2")
                .email("zaim2@gmail.com")
                .phone("88005555555")
                .description("Take our money2")
                .rating(4.8)
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
