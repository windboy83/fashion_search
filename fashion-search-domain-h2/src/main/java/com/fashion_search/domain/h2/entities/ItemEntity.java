package com.fashion_search.domain.h2.entities;

import com.fashion_search.domain.h2.converter.BrandCodeConverter;
import com.fashion_search.domain.h2.converter.CategoryTypeConverter;
import com.fashion_search.domain.h2.dto.command.ItemCommand;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class ItemEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seqNo")
    private Long seqNo;

    // 브랜드 코드
    @Column(name = "brandCode", nullable = false, columnDefinition = "char(1)")
    @Convert(converter = BrandCodeConverter.class)
    private BrandCode brandCode;

    // 카테고리 타입
    @Column(name = "categoryType", nullable = false, columnDefinition = "smallint")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType categoryType;

    @Column(name = "price", nullable = false)
    private Integer price;

    public void modifyItem(ItemCommand.ModifyItem modifyItem) {
        this.brandCode = modifyItem.getBrandCode();
        this.categoryType = modifyItem.getCategoryType();
        this.price = modifyItem.getPrice();
    }
}
