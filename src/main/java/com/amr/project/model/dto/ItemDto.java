package com.amr.project.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class ItemDto {
    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private Long id;
    private String name;
    private BigDecimal basePrice;
    private BigDecimal price;
    @Min(value = 0L, message = "The value must be positive")
    private int count;
    private double rating;
    private String description;
    @Min(value = 0L, message = "The value must be positive")
    private String discount;
    private List<ImageDto> images;
    private List<ReviewDto> reviews;
//    private boolean isModerated;
//    private boolean isModerateAccept;
//    private String moderatedRejectReason;
//    private boolean isPretendedToBeDeleted;
}
