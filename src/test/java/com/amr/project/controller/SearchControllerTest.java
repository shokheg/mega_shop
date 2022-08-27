//package com.amr.project.controller;
//
//import com.amr.project.model.dto.ItemDto;
//import com.amr.project.model.entity.Item;
//import com.amr.project.model.entity.Shop;
//import com.amr.project.model.entity.User;
//import com.amr.project.service.abstracts.ItemService;
//import com.amr.project.service.abstracts.ShopService;
//import com.amr.project.service.abstracts.UserService;
//import com.amr.project.webapp.controller.SearchRestController;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.JsonContent;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//import springfox.documentation.spring.web.json.Json;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//public class SearchControllerTest {
//
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private SearchRestController searchRestController;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ItemService itemService;
//
//    private User getUser() {
//        return userService.findById(1L);
//    }
//
//    private Shop getShop() {
//        return Shop.builder()
//                .name("Bershka")
//                .email("bershka@gmail.com")
//                .phone("8(988)658-55-99")
//                .description("Bershka видит себя маяком в мире моды, ориентированным на все более требовательную аудиторию, и всего за два года компания объединила под своим брендом сотню магазинов.")
//                .count(1)
//                .rating(4.6)
//                .isModerateAccept(true)
//                .isPretendedToBeDeleted(false)
//                .user(getUser())
//                .build();
//    }
//
//    @Test
//    public void searchShop() throws Exception {
//        assertThat(searchRestController).isNotNull();
//        User user = getUser();
//        shopService.persist(getShop());
//        this.mockMvc.perform(get("http://localhost:8888/api/search/shops?query=sh"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{\"id\":3,\"name\":\"Bershka\",\"description\":\"Bershka видит себя маяком в мире моды, ориентированным на все более требовательную аудиторию, и всего за два года компания объединила под своим брендом сотню магазинов.\",\"email\":\"bershka@gmail.com\",\"phone\":\"8(988)658-55-99\",\"rating\":4.6,\"reviews\":[],\"logo\":null,\"discounts\":[],\"location\":null,\"userId\":1,\"couponIds\":[]}]"));
//    }
//
//    @Test
//    public void searchItem() throws Exception {
//        Item item = Item.builder()
//                .name("Куртка")
//                .count(2)
//                .discount(3)
//                .rating(5)
//                .isModerateAccept(true)
//                .isPretendedToBeDeleted(false)
//                .description("Зимняя куртка")
//                .shop(shopService.findById(2L))
//                .user(getUser())
//                .build();
//        itemService.persist(item);
//        this.mockMvc.perform(get("http://localhost:8888/api/search/items?query=Кур"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{\"id\":11,\"name\":\"Куртка\",\"basePrice\":null,\"price\":null,\"count\":2,\"rating\":5.0,\"description\":\"Зимняя куртка\",\"images\":[],\"reviews\":[]}]"));
//    }
//}
