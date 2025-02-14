package com.fashion_search.api.service;

import com.fashion_search.api.common.exception.NotFoundException;
import com.fashion_search.domain.h2.dto.command.ItemCommand;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import com.fashion_search.domain.h2.entities.ItemEntity;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import com.fashion_search.domain.h2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ItemInfo registerItem(ItemCommand.RegisterItem registerItem) {
        ItemEntity itemEntity = ItemEntity.builder()
                .brandCode(registerItem.getBrandCode())
                .categoryType(registerItem.getCategoryType())
                .price(registerItem.getPrice())
                .build();
        itemRepository.save(itemEntity);
        return new ItemInfo(itemEntity);
    }

    @Transactional
    public ItemInfo modifyItem(ItemCommand.ModifyItem modifyItem) {
        ItemEntity itemEntity = itemRepository.findById(modifyItem.getSeqNo())
                .orElseThrow(() -> new NotFoundException("Item not found"));
        itemEntity.modifyItem(modifyItem);
        return new ItemInfo(itemEntity);
    }

    @Transactional
    public void deleteItem(Long seqNo) {
        itemRepository.deleteById(seqNo);
    }

    @Transactional(readOnly = true)
    public ItemInfo getItemBy(Long seqNo) {
        ItemEntity itemEntity = itemRepository.findById(seqNo)
                .orElse(null);

        return itemEntity == null ? null : new ItemInfo(itemEntity);
    }

    @Transactional(readOnly = true)
    public List<ItemInfo> getLowestPriceItemsByCategory() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        // 최저가 상품만 필터링
        Map<CategoryType, ItemEntity> categoryTypeMap = itemEntities.stream()
                .collect(Collectors.toMap(ItemEntity::getCategoryType, Function.identity(),
                        (existing, replacement) -> existing.getPrice() < replacement.getPrice() ? existing : replacement));

        return categoryTypeMap.values().stream()
                .map(ItemInfo::new).sorted(Comparator.comparing(ItemInfo::getCategoryType))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemInfo> getLowestPriceItemsByBrands() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        // 브랜드별 카테고리별 최저가 상품 필터링(중복제거)
        Map<BrandCode, Map<CategoryType, ItemEntity>> brandCategoryPriceMap = itemEntities.stream()
                .collect(Collectors.groupingBy(ItemEntity::getBrandCode,
                        Collectors.toMap(ItemEntity::getCategoryType, Function.identity(),
                                (existing, replacement) -> existing.getPrice() < replacement.getPrice() ? existing : replacement)));

        // 브랜드별 총 가격 계산
        Map<BrandCode, Integer> brandSumPriceMap = brandCategoryPriceMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().values().stream()
                                .map(ItemEntity::getPrice)
                                .reduce(0, Integer::sum)));

        // 최저가 브랜드 찾기
        BrandCode lowestPriceBrand = brandSumPriceMap.entrySet().stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);

        // 최저가 브랜드 존재 여부 확인
        if (lowestPriceBrand == null) {
            return List.of();
        }

        // 최저가 브랜드 상품 필터링
        return brandCategoryPriceMap.get(lowestPriceBrand).values().stream()
                .map(ItemInfo::new)
                .sorted(Comparator.comparing(ItemInfo::getCategoryType))
                .toList();
    }

    public ItemInfo getLowestPriceItemBy(CategoryType categoryType) {
        List<ItemEntity> itemEntities = itemRepository.findAllByCategoryType(categoryType);
        return itemEntities.stream()
                .min(Comparator.comparingInt(ItemEntity::getPrice))
                .map(ItemInfo::new)
                .orElse(null);
    }

    public ItemInfo getHighestPriceItemBy(CategoryType categoryType) {
        List<ItemEntity> itemEntities = itemRepository.findAllByCategoryType(categoryType);
        return itemEntities.stream()
                .max(Comparator.comparingInt(ItemEntity::getPrice))
                .map(ItemInfo::new)
                .orElse(null);
    }
}
