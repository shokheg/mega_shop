package com.amr.project.converter;

import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.entity.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryConverter {
        CategoryDto toDto(Category Category);

        Category toModel(CategoryDto categoryDto);

        List<CategoryDto> toDtoList(List<Category> categoryList);

        List<Category> toModelList(List<CategoryDto> categoryDtoList);
}
