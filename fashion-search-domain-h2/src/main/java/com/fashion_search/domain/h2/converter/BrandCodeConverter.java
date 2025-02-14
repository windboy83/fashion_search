package com.fashion_search.domain.h2.converter;

import com.fashion_search.domain.h2.enums.BrandCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class BrandCodeConverter implements AttributeConverter<BrandCode, String> {

    @Override
    public String convertToDatabaseColumn(BrandCode brandCode) {
        return brandCode.getCode();
    }

    @Override
    public BrandCode convertToEntityAttribute(String code) {
        if (isNull(code)) {
            return null;
        }
        return BrandCode.findByCode(code);
    }
}
