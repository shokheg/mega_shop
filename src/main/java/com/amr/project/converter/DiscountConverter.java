package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountConverter {

    Discount dtoToEntity(DiscountDto discountDto);

    DiscountDto entityToDto(Discount discount);

    List<Discount> dtoToEntity(List<DiscountDto> discountDtoList);

    List<DiscountDto> entityToDto(List<Discount> discountList);
}
