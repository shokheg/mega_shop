package com.amr.project.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Social {
    FACEBOOK("facebook"),
    GOOGLE("google"),
    GITHUB("github"),
    LOCAL("local");

    private String providerType;
}
