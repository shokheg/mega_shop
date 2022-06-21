package com.amr.project.converter;

import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ImageService;

import com.amr.project.service.abstracts.ItemService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    @Autowired
    ImageService imageService;

    public abstract ImageDto toDto(Image image);

    @Mappings({
            @Mapping(target = "shop", expression = "java(getShop(imageDto))")
    })
    public abstract Image toEntity(ImageDto imageDto);

    public abstract List<ImageDto> toImageDtos(List<Image> images);

    public abstract List<Image> toImages(List<ImageDto> imageDtos);

    protected Shop getShop(ImageDto imageDto) {
        if (imageDto.getId() != null) {
            return imageService.findById(imageDto.getId()).getShop();
        }
        return null;
    }
}