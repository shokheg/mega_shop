package com.amr.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    START,
    COMPLETE,
    WAITING,
    PAID,
    SENT,
    DELIVERED
}

