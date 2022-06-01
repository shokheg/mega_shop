package com.amr.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//TODO УДАЛИТЬ!!!! ДТО для енум не нужно делать, это просто не имеет смысла
public enum GenderDto {
    MALE,
    FEMALE,
    UNKNOWN
}