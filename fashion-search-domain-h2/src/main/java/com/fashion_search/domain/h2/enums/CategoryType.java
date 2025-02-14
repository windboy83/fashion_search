package com.fashion_search.domain.h2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    TOP (1, "상의"),
    OUTER (2, "아우터"),
    PANTS (3, "바지"),
    SNEAKERS (4, "스니커즈"),
    BAG (5, "가방"),
    HAT (6, "모자"),
    SOCKS (7, "양말"),
    ACCESSORY (8, "액세서리");

    private final int code;
    private final String categoryName;

    public static CategoryType findByName(String categoryName) {
        for (CategoryType categoryType : values()) {
            if (categoryType.getCategoryName().equals(categoryName)) {
                return categoryType;
            }
        }
        return null;
    }

    // find by code
    public static CategoryType findByCode(int code) {
        for (CategoryType categoryType : values()) {
            if (categoryType.getCode() == code) {
                return categoryType;
            }
        }
        return null;
    }
}
