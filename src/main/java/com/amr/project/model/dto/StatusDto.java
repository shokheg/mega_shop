package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//TODO удалить!!!
public enum StatusDto {
    START,
    COMPLETE,
    WAITING,
    PAID,
    SENT,
    DELIVERED
}
