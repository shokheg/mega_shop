package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CityConverter {

    @Mappings({
            @Mapping(target = "id", expression = "java(city.getId())"),
            @Mapping(target = "name", expression = "java(city.getName())"),
            @Mapping(target = "countryName", expression = "java(getCountryName(city))"),
            @Mapping(target = "countryId", expression = "java(getCountryId(city))")
    })
    CityDto entityToDto(City city);

    default String getCountryName(City city) {
        if (city.getCountry() != null) {
            return city.getCountry().getName();
        }
        return null;
    }

    default Long getCountryId(City city) {
        if (city.getCountry() != null) {
            return city.getCountry().getId();
        }
        return null;
    }
}
