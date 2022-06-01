package com.amr.project.model.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Long.class)
public class AddressDto {
    private Long id;
    private String cityIndex;
    private String street;
    private String house;
    private Long cityId;
    private String city;
    private Long countryId;
    private String country;
    //добавить это поле в сущности нужно для указания дополнительной информации по адресу
    private String additionalInfo;
}
