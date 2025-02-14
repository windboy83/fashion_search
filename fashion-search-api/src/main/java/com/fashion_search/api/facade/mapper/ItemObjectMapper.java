package com.fashion_search.api.facade.mapper;

import com.fashion_search.api.controller.dto.ItemRequest;
import com.fashion_search.api.controller.dto.ItemResponse;
import com.fashion_search.domain.h2.dto.command.ItemCommand;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemObjectMapper {

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "categoryItemInfos", source = "itemInfoList", qualifiedByName = "toCategoryItemInfo")
    ItemResponse.LowPriceItemsByCategory toLowPriceItemsByCategory(int totalPrice, List<ItemInfo> itemInfoList);

    @Mapping(target = "categoryName", source = "categoryType.categoryName")
    @Mapping(target = "brandCode", source = "brandCode")
    @Mapping(target = "price", source = "price")
    @Named("toCategoryItemInfo")
    ItemResponse.LowPriceItemsByCategory.CategoryItemInfo toCategoryItemInfo(ItemInfo itemInfo);

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "brandCode", source = "brandCode")
    @Mapping(target = "brandItemInfos", source = "itemInfoList", qualifiedByName = "toBrandItemInfo")
    ItemResponse.LowPriceItemsByBrand toLowPriceItemsByBrand(int totalPrice, String brandCode, List<ItemInfo> itemInfoList);

    @Mapping(target = "categoryName", source = "categoryType.categoryName")
    @Mapping(target = "price", source = "price")
    @Named("toBrandItemInfo")
    ItemResponse.LowPriceItemsByBrand.BrandItemInfo toBrandItemInfo(ItemInfo itemInfo);

    @Mapping(target = "brandCode", source = "brandCode")
    @Mapping(target = "price", source = "price")
    @Named("toCategoryBrandItemInfo")
    ItemResponse.CategoryHighAndLowItem.CategoryBrandItemInfo toCategoryBrandItemInfo(ItemInfo itemInfo);

    ItemCommand.RegisterItem toRegisterItem(ItemRequest.RegisterItem request);

    ItemCommand.ModifyItem toModifyItem(ItemRequest.ModifyItem request);
}
