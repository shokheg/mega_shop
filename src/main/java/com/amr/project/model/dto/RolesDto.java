package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//TODO Удалить!
public enum RolesDto {
    USER,
    MODERATOR,
    ADMIN,
    ANONYMOUS

}
