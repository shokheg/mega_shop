package com.amr.project.converter;

import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class MessageConverter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public abstract MessageDto entityToDto(Message message);

    @Mappings({
            @Mapping(target = "sender", expression = "java(getUser(messageDto.getFromUserId()))"),
            @Mapping(target = "recipient", expression = "java(getUser(messageDto.getToUserId()))"),
            @Mapping(target = "date", expression = "java(getDate(messageDto.getCreationTime()))")
    })
    public abstract Message dtoToEntity(MessageDto messageDto);

    protected User getUser(Long id) {
        return userService.findById(id);
    }

    protected Date getDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
