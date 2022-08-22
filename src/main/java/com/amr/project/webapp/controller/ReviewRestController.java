package com.amr.project.webapp.controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items/{itemId}/reviews")
public class ReviewRestController {
    private ReviewService reviewService;
    private ItemService itemService;
    private ShopService shopService;
    private UserService userService;
    private ReviewMapper reviewMapper;

    @Autowired
    public ReviewRestController(ReviewService reviewService, ItemService itemService,
                                ShopService shopService, UserService userService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.itemService = itemService;
        this.shopService = shopService;
        this.userService = userService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@PathVariable(name = "itemId") long id,
                                          @RequestBody ReviewDto reviewDto) {
        boolean isSaved = false;
        try {
            Item item = itemService.findById(id);
            Shop shop = shopService.findById(reviewDto.getShopId());
            User user = userService.findById(reviewDto.getUserId());

            Review review = reviewMapper.toEntity(reviewDto);
            review.setItem(item);
            review.setUser(user);
            review.setShop(shop);

            System.out.println(review);

            // Вынести отдельный метод в классе Item?
            List<Review> itemReviews = item.getReviews();
            if (itemReviews != null) {
                itemReviews.add(review);
            } else {
                itemReviews = Arrays.asList(review);
            }

            // Вынести отдельный метод в классе Shop?
            List<Review> shopReviews = shop.getReviews();
            if (shopReviews != null) {
                shopReviews.add(review);
            } else {
                shopReviews = Arrays.asList(review);
            }

            // Вынести отдельный метод в классе User?
            List<Review> userReviews = user.getReviews();
            if (userReviews != null) {
                userReviews.add(review);
            } else {
                userReviews = Arrays.asList(review);
            }

            reviewService.persist(review);
            itemService.update(item);
            shopService.update(shop);
            userService.update(user);

            isSaved = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isSaved
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> showItemReviews(@PathVariable(name = "itemId") long id) {
        List<Review> itemReviews = reviewService.findAll();
        List<ReviewDto> resultReviewsList = itemReviews.stream()
                .filter(itemReview -> itemReview.getItem().getId() == id)
                .map(review -> reviewMapper.toDto(review))
                .collect(Collectors.toList());
        System.out.println(resultReviewsList);
        return new ResponseEntity<>(resultReviewsList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto updatedReviewDto) {
        boolean isUpdated = false;
        try {
            Review updateReview = reviewMapper.toEntity(updatedReviewDto);
            updateReview.setModerated(true);
            reviewService.update(updateReview);
            isUpdated = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return isUpdated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable(name = "reviewId") long id) {
        Review review = reviewService.findById(id);
        if (review != null) {
            reviewService.delete(review);
            return new ResponseEntity<>("Review removed", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
