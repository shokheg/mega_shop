package com.amr.project.model.dto;

import com.amr.project.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private Gender gender;
    private LocalDate birthday;
    private String secret;
    private List<AddressDto> addresses;
    private ImageDto image;
    private List<Long> couponIds;
    private List<Long> orderIds;
    private List<ReviewDto> reviews;
    private List<FeedbackDto> feedbacks;
    private List<Long> shopIds;
    private FavoriteDto favorite;
    private List<DiscountDto> discounts;
    private Set<Long> chatIds;
}
