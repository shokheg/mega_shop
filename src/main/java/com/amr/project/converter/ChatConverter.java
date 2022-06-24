package com.amr.project.converter;

import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ChatConverter {

    @Mappings({
            @Mapping(target = "membersId", expression = "java(getMembersIds(chat))")
    })
    ChatDto entityToDto(Chat chat);

    default Set<Long> getMembersIds(Chat chat) {
        Set<Long> membersIds = new HashSet<>();
        membersIds.add(chat.getSender().getId());
        membersIds.add(chat.getRecipient().getId());
        return membersIds;
    }
}
