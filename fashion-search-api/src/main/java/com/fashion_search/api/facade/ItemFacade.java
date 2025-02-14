package com.fashion_search.api.facade;

import com.fashion_search.api.common.exception.BusinessException;
import com.fashion_search.api.common.exception.ErrorCode;
import com.fashion_search.api.common.utils.StreamUtils;
import com.fashion_search.api.controller.dto.ItemRequest;
import com.fashion_search.api.controller.dto.ItemResponse;
import com.fashion_search.api.facade.mapper.ItemObjectMapper;
import com.fashion_search.api.service.ItemService;
import com.fashion_search.domain.h2.dto.command.ItemCommand;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import com.fashion_search.domain.h2.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemFacade {
    private final ItemService itemService;
    private final ItemObjectMapper itemObjectMapper;

    public ItemResponse.LowPriceItemsByCategory getLowestPriceItemsByCategory() {
        List<ItemInfo> itemInfoList = itemService.getLowestPriceItemsByCategory();
        if (itemInfoList.isEmpty()) {
            return new ItemResponse.LowPriceItemsByCategory(0, List.of());
        }

        return itemObjectMapper.toLowPriceItemsByCategory(StreamUtils.sum(itemInfoList, ItemInfo::getPrice), itemInfoList);
    }

    public ItemResponse.LowPriceItemsByBrand getLowestPriceItemsByBrands() {
        List<ItemInfo> itemInfoList = itemService.getLowestPriceItemsByBrands();
        if (itemInfoList.isEmpty()) {
            return new ItemResponse.LowPriceItemsByBrand(0, "", List.of());
        }

        return itemObjectMapper.toLowPriceItemsByBrand(StreamUtils.sum(itemInfoList, ItemInfo::getPrice), itemInfoList.getFirst().getBrandCode().getCode(), itemInfoList);
    }

    public ItemResponse.CategoryHighAndLowItem getCategoryHighAndLowItem(String categoryName) {
        // 카테고리명으로 카테고리 타입 조회
        CategoryType categoryType = CategoryType.findByName(categoryName);
        if (categoryType == null) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST_ERROR);
        }

        // 카테고리별 최저가, 최고가 상품 조회
        ItemInfo lowestPriceItem = itemService.getLowestPriceItemBy(categoryType);
        ItemInfo highestPriceItem = itemService.getHighestPriceItemBy(categoryType);

        return ItemResponse.CategoryHighAndLowItem.builder()
                .categoryName(categoryType.getCategoryName())
                .lowPriceItem(itemObjectMapper.toCategoryBrandItemInfo(lowestPriceItem))
                .highPriceItem(itemObjectMapper.toCategoryBrandItemInfo(highestPriceItem))
                .build();
    }

    public ItemInfo registerItem(ItemRequest.RegisterItem request) {
        ItemCommand.RegisterItem registerItem = itemObjectMapper.toRegisterItem(request);
        return itemService.registerItem(registerItem);
    }

    public ItemInfo modifyItem(ItemRequest.ModifyItem request) {
        ItemCommand.ModifyItem modifyItem = itemObjectMapper.toModifyItem(request);
        return itemService.modifyItem(modifyItem);
    }

    public void deleteItem(Long seqNo) {
        itemService.deleteItem(seqNo);
    }
}
