package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CityConverterTest {

    @Autowired
    private CityConverter cityConverter;

    @Test
    public void entityToDto() {
        City city = City.builder()
                .id(1L)
                .name("Moscow")
                .addresses(List.of(Address.builder()
                        .id(1L)
                        .build()))
                .country(Country.builder()
                        .id(1L)
                        .name("Russia")
                        .build())
                .build();

        CityDto cityDto = CityDto.builder()
                .id(1L)
                .name("Moscow")
                .countryId(1L)
                .countryName("Russia")
                .build();

        assertEquals(cityConverter.entityToDto(city), cityDto);
    }
}
