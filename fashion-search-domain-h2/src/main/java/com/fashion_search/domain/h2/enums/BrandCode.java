package com.fashion_search.domain.h2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BrandCode {
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I");

    private final String code;

    public static BrandCode findByCode(String code) {
        for (BrandCode brandCode : values()) {
            if (brandCode.getCode().equals(code)) {
                return brandCode;
            }
        }
        return null;
    }
}
