package com.amr.project.converter;

import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.AddressService;
import com.amr.project.service.abstracts.CityService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    @Autowired
    AddressService addressService;
    @Autowired
    CityService cityService;

    @Mappings({
            @Mapping(target = "city", expression = "java(getCity(addressDto))"),
            @Mapping(target = "users", expression = "java(getUsers(addressDto))"),
            @Mapping(target = "shops", expression = "java(getShops(addressDto))"),
            @Mapping(target = "orders", expression = "java(getOrders(addressDto))")
    })
    public abstract Address toModel(AddressDto addressDto);

    @Mappings({
        @Mapping(target = "cityId", expression = "java(address.getCity().getId())"),
        @Mapping(target = "city", expression = "java(address.getCity().getName())"),
        @Mapping(target = "countryId", expression = "java(address.getCity().getCountry().getId())"),
        @Mapping(target = "country", expression = "java(address.getCity().getCountry().getName())"),
        @Mapping(target = "additionalInfo", ignore = true)
    })
    public abstract AddressDto toDto(Address address);

    protected City getCity(AddressDto addressDto) {
        if (addressDto.getCityId() != null) {
            return cityService.findById(addressDto.getCityId());
        }
        return null;
    }

    protected List<User> getUsers(AddressDto addressDto) {
        if (addressDto.getId()!= null) {
            return addressService.findById(addressDto.getId()).getUsers();
        }
        return new ArrayList<>();
    }

    protected List<Shop> getShops(AddressDto addressDto) {
        if (addressDto.getId()!= null) {
            return addressService.findById(addressDto.getId()).getShops();
        }
        return new ArrayList<>();
    }

    protected List<Order> getOrders(AddressDto addressDto) {
        if (addressDto.getId()!= null) {
            return addressService.findById(addressDto.getId()).getOrders();
        }
        return null;
    }

}
