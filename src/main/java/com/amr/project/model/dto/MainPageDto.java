package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MainPageDto {
    private List<CategoryDto> categoryDto;
    private List<ShopDto> shopDtoList;
    private List<ItemDto> itemDtoList;
    private String username;
}
