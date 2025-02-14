package com.fashion_search.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {

    @Getter
    @Builder
    // 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 응답 DTO
    public static class LowPriceItemsByCategory {
        @Schema(description = "총액")
        private final Integer totalPrice;
        @Schema(description = "카테고리 별 최저가격 브랜드와 상품 가격")
        private final List<CategoryItemInfo> categoryItemInfos;

        public LowPriceItemsByCategory(int totalPrice, List<CategoryItemInfo> categoryItemInfos) {
            this.totalPrice = totalPrice;
            this.categoryItemInfos = categoryItemInfos;
        }

        @Getter
        @Builder
        public static class CategoryItemInfo {
            @Schema(description = "카테고리명")
            private final String categoryName;
            @Schema(description = "브랜드 코드")
            private final String brandCode;
            @Schema(description = "상품 가격")
            private final Integer price;

            public CategoryItemInfo(String categoryName, String brandCode, Integer price) {
                this.categoryName = categoryName;
                this.brandCode = brandCode;
                this.price = price;
            }
        }
    }

    @Getter
    @Builder
    public static class LowPriceItemsByBrand {
        @Schema(description = "총액")
        private final Integer totalPrice;
        @Schema(description = "브랜드 코드")
        private final String brandCode;
        @Schema(description = "카테고리 별 최저가격 브랜드와 상품 가격")
        private final List<LowPriceItemsByBrand.BrandItemInfo> brandItemInfos;

        public LowPriceItemsByBrand(int totalPrice, String brandCode, List<LowPriceItemsByBrand.BrandItemInfo> brandItemInfos) {
            this.totalPrice = totalPrice;
            this.brandCode = brandCode;
            this.brandItemInfos = brandItemInfos;
        }

        @Getter
        @Builder
        public static class BrandItemInfo {
            @Schema(description = "카테고리명")
            private final String categoryName;
            @Schema(description = "상품 가격")
            private final Integer price;

            public BrandItemInfo(String categoryName, Integer price) {
                this.categoryName = categoryName;
                this.price = price;
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CategoryHighAndLowItem {
        @Schema(description = "카테고리명")
        private final String categoryName;
        @Schema(description = "최저가격 브랜드와 상품 가격")
        private final CategoryHighAndLowItem.CategoryBrandItemInfo lowPriceItem;
        @Schema(description = "최고가격 브랜드와 상품 가격")
        private final CategoryHighAndLowItem.CategoryBrandItemInfo highPriceItem;

        @Getter
        @Builder
        @AllArgsConstructor
        public static class CategoryBrandItemInfo {
            @Schema(description = "브랜드 코드")
            private final String brandCode;
            @Schema(description = "상품 가격")
            private final Integer price;
        }
    }
}
