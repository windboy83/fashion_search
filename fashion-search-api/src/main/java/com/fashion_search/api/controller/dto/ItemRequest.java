package com.fashion_search.api.controller.dto;

import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ItemRequest {

    @Getter
    @Setter
    @Builder
    public static class RegisterItem {
        @Schema(description = "브랜드명", example = "A")
        @NotNull
        private BrandCode brandCode;
        @Schema(description = "카테고리 타입", example = "TOP")
        @NotNull
        private CategoryType categoryType;
        @Schema(description = "가격", example = "10000", minimum = "0")
        @NotNull
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;
    }

    @Getter
    @Setter
    @Builder
    public static class ModifyItem {
        @Schema(description = "상품 번호", example = "1")
        @NotNull
        private Long seqNo;
        @Schema(description = "브랜드명", example = "A")
        @NotNull
        private BrandCode brandCode;
        @Schema(description = "카테고리 타입", example = "TOP")
        @NotNull
        private CategoryType categoryType;
        @Schema(description = "가격", example = "10000")
        @NotNull
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;
    }
}
