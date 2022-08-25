package com.amr.project.webapp.controller;


import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopConverter;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderator")
@Api(tags = "Moderator Controller")
public class ModeratorRestController {

    private final ShopService shopService;

    private final ShopConverter shopConverter;

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;


    public ModeratorRestController(ShopService shopService, ShopConverter shopConverter, ItemService itemService, ItemMapper itemMapper, ReviewService reviewService, ReviewMapper reviewMapper) {
        this.shopService = shopService;
        this.shopConverter = shopConverter;
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }


    @GetMapping("/shops")
    @ApiOperation("Поиск непроверенных магазинов модератором")
    public ResponseEntity<List<ShopDto>> allShopsNotModerated() {
        List<Shop> shopList = shopService.findShopsNotModerated();
        List<ShopDto> shopDtoList = shopConverter.entityToDto(shopList);
        return new ResponseEntity<>(shopDtoList, HttpStatus.OK);
    }

    @GetMapping("/items")
    @ApiOperation("Поиск непроверенных предметов модератором")
    public ResponseEntity<List<ItemDto>> allItemsNotModerated() {
        List<Item> itemList = itemService.findItemsNotModerated();
        List<ItemDto> itemDtoList = itemMapper.toItemsDTO(itemList);
        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }

    @GetMapping("/reviews")
    @ApiOperation("Поиск непроверенных отзывов")
    public ResponseEntity<List<ReviewDto>> allReviewsNotModerated() {
        List<Review> reviewsList = reviewService.findReviewsNotModerated();
        List<ReviewDto> reviewDtos = reviewMapper.toReviewDtos(reviewsList);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @PutMapping("/shops/accepted/{id}")
    @ApiOperation("Подтверждение магазина")
    public ResponseEntity<ShopDto> acceptModerShop(@PathVariable Long id) {
        if (shopService.existsById(id)) {
            Shop shop = shopService.findById(id);
            shop.setModerateAccept(true);
            shop.setModerated(true);
            shop.setPretendedToBeDeleted(false);
            shopService.update(shop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/shops/decline/{id}")
    @ApiOperation("Отклонённый магазин")
    public ResponseEntity<ShopDto> declineModerShop(@PathVariable Long id, @RequestParam String rejectReason) {
        if (shopService.existsById(id)) {
            Shop shop = shopService.findById(id);
            shop.setModerateAccept(false);
            shop.setModerated(true);
            shop.setPretendedToBeDeleted(true);
            shop.setModeratedRejectReason(rejectReason);
            shopService.update(shop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/items/accepted/{id}")
    @ApiOperation("Принятый товар")
    public ResponseEntity<ItemDto> acceptModerItem(@PathVariable Long id) {
        if (itemService.existsById(id)) {
            Item item = itemService.findById(id);
            item.setModerated(true);
            item.setModerateAccept(true);
            item.setPretendedToBeDeleted(false);
            itemService.update(item);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/items/decline/{id}")
    @ApiOperation("Отклонённый товар")
    public ResponseEntity<ItemDto> declineModerItem(@PathVariable Long id, @RequestParam String rejectReason) {
        if (itemService.existsById(id)) {
            Item item = itemService.findById(id);
            item.setModerateAccept(false);
            item.setModerated(true);
            item.setPretendedToBeDeleted(true);
            item.setModeratedRejectReason(rejectReason);
            itemService.update(item);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping("/reviews/accepted/{id}")
    @ApiOperation("Подтверждение отзыва")
    public ResponseEntity<ReviewDto> acceptModerReview(@PathVariable Long id) {
        if (reviewService.existsById(id)) {
            Review review = reviewService.findById(id);
            review.setModerated(true);
            review.setModerateAccept(true);
            reviewService.update(review);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/reviews/decline/{id}")
    @ApiOperation("Отклонённый отзыв")
    public ResponseEntity<ReviewDto> declineModerReview(@PathVariable Long id, @RequestParam String rejectReason) {
        if (reviewService.existsById(id)) {
            Review review = reviewService.findById(id);
            review.setModerated(true);
            review.setModerateAccept(false);
            review.setModeratedRejectReason(rejectReason);
            reviewService.update(review);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
