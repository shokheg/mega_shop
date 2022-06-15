package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityConverter {

    CityDto entityToDto(City city);
}
