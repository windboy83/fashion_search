package com.fashion_search.domain.h2.converter;

import com.fashion_search.domain.h2.enums.CategoryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<CategoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoryType categoryType) {
        return categoryType.getCode();
    }

    @Override
    public CategoryType convertToEntityAttribute(Integer integer) {
        if (isNull(integer)) {
            return null;
        }
        return CategoryType.findByCode(integer);
    }
}
