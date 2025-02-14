package com.fashion_search.domain.h2.dto.info;

import com.fashion_search.domain.h2.entities.ItemEntity;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {
    @Schema(description = "상품 번호")
    private Long seqNo;
    @Schema(description = "브랜드 코드")
    private BrandCode brandCode;
    @Schema(description = "카테고리 타입")
    private CategoryType categoryType;
    @Schema(description = "가격")
    private Integer price;
    @Schema(description = "생성일")
    private LocalDateTime createdAt;
    @Schema(description = "수정일")
    private LocalDateTime updatedAt;

    public ItemInfo(ItemEntity itemEntity) {
        this.seqNo = itemEntity.getSeqNo();
        this.brandCode = itemEntity.getBrandCode();
        this.categoryType = itemEntity.getCategoryType();
        this.price = itemEntity.getPrice();
        this.createdAt = itemEntity.getCreateAt();
        this.updatedAt = itemEntity.getUpdateAt();
    }
}
