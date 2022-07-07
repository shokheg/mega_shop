package com.amr.project.controller;


import com.amr.project.service.abstracts.ItemService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@TestPropertySource("/application.properties")

public class ItemRestControllerTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStart() throws Exception {
        assertThat(mockMvc).isNotNull();
    }
    @Test
    public void getItemByIdTest() throws Exception {
        mockMvc.perform(get("/api/items/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", Matchers.is(itemService.findById(1L).getName())));
    }

    @Test
    public void testItemDeleteShop() throws Exception {
        mockMvc.perform(delete("/api/items/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
