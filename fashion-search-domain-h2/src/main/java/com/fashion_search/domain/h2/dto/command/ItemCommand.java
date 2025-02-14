package com.fashion_search.domain.h2.dto.command;

import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCommand {

    @Getter
    @Setter
    @Builder
    public static class RegisterItem {
        private BrandCode brandCode;
        private CategoryType categoryType;
        private Integer price;
    }

    @Getter
    @Setter
    @Builder
    public static class ModifyItem {
        private Long seqNo;
        private BrandCode brandCode;
        private CategoryType categoryType;
        private Integer price;
    }
}
