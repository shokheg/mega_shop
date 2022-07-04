package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopConverter;
import com.amr.project.model.dto.PaginationDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class MainPageRestControllerTest {

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ShopConverter shopConverter;

    @MockBean
    private ShopService shopService;

    @Autowired
    private MainPageRestController mainPageRestController;

    @Test
    void testPaginationItems() throws Exception {
        when(itemService.findAllItems(anyInt(), anyInt(), anyInt())).thenReturn(new PaginationDto());
        when(itemMapper.toItemsDTO((List<Item>) any())).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pageItem");
        
        MockMvcBuilders.standaloneSetup(mainPageRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"content\":[],\"count\":null,\"maxPages\":0}"));
    }

    @Test
    void testPaginationShops() throws Exception {
        when(shopService.findAllShop(anyInt(), anyInt(), anyInt())).thenReturn(new PaginationDto());
        when(shopConverter.entityToDto((List<Shop>) any())).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pageShop");

        MockMvcBuilders.standaloneSetup(mainPageRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"content\":[],\"count\":null,\"maxPages\":0}"));
    }
}
