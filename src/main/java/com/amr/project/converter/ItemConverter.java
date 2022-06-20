package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ItemConverter {

    public abstract ItemDto entityToDto(Item item);

    public abstract List<ItemDto> entityToDto(List<Item> items);
}
