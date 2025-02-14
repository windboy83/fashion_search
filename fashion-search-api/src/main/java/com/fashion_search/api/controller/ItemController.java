package com.fashion_search.api.controller;

import com.fashion_search.api.common.dto.CommonResponse;
import com.fashion_search.api.controller.dto.ItemRequest;
import com.fashion_search.api.controller.dto.ItemResponse;
import com.fashion_search.api.facade.ItemFacade;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemFacade itemFacade;

    @GetMapping(value = "/api/v1/items/categories/lowest-price", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    public CommonResponse<ItemResponse.LowPriceItemsByCategory> getLowestPriceItemsByCategory() {
        return CommonResponse.success(itemFacade.getLowestPriceItemsByCategory());
    }

    @GetMapping(value = "/api/v1/items/brands/lowest-price", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "브랜드 별 최저가격 상품 가격, 총액을 조회", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    public CommonResponse<ItemResponse.LowPriceItemsByBrand> getLowestPriceItemsByBrands() {
        return CommonResponse.success(itemFacade.getLowestPriceItemsByBrands());
    }

    @GetMapping(value = "/api/v1/{categoryName}/high-and-low-item", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    public CommonResponse<ItemResponse.CategoryHighAndLowItem> getCategoryHighAndLowItem(
            @Schema(description = "카테고리 이름 : {상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리}", defaultValue = "상의", example = "상의")
            @PathVariable String categoryName
    ) {
        return CommonResponse.success(itemFacade.getCategoryHighAndLowItem(categoryName));
    }

    @PostMapping(value = "/api/v1/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "상품등록", description = "상품을 등록하는 API")
    public CommonResponse<ItemInfo> registerItem(
            @Valid @RequestBody ItemRequest.RegisterItem request
            ) {
        return CommonResponse.success(itemFacade.registerItem(request));
    }

    @PutMapping(value = "/api/v1/items/{seqNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "상품수정", description = "상품을 수정하는 API")
    public CommonResponse<ItemInfo> modifyItem(
            @PathVariable Long seqNo,
            @Valid @RequestBody ItemRequest.ModifyItem request
    ) {
        request.setSeqNo(seqNo);
        return CommonResponse.success(itemFacade.modifyItem(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/api/v1/items/{seqNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "상품삭제", description = "상품을 삭제하는 API")
    public CommonResponse<Void> deleteItem(
            @PathVariable Long seqNo
    ) {
        itemFacade.deleteItem(seqNo);
        return CommonResponse.success(null);
    }
}
