package com.fashion_search.api.controller;

import com.fashion_search.api.common.exception.BadRequestException;
import com.fashion_search.api.controller.dto.ItemRequest;
import com.fashion_search.api.controller.dto.ItemResponse;
import com.fashion_search.api.facade.ItemFacade;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(profiles = {"local"})
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemFacade itemFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리별 최저가 상품 조회 정상 동작 테스트")
    void getLowestPriceItemsByCategory() throws Exception {
        // given
        ItemResponse.LowPriceItemsByCategory lowPriceItemsByCategory = ItemResponse.LowPriceItemsByCategory.builder()
                .categoryItemInfos(List.of(
                        ItemResponse.LowPriceItemsByCategory.CategoryItemInfo.builder().categoryName(CategoryType.TOP.name()).brandCode(BrandCode.C.name()).price(10000).build()
                ))
                .totalPrice(34100)
                .build();
        given(itemFacade.getLowestPriceItemsByCategory()).willReturn(lowPriceItemsByCategory);

        // when & then
        mockMvc.perform(get("/api/v1/items/categories/lowest-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalPrice").value(34100))
                .andExpect(jsonPath("$.data.categoryItemInfos[0].categoryName").value(CategoryType.TOP.name()))
                .andExpect(jsonPath("$.data.categoryItemInfos[0].brandCode").value(BrandCode.C.name()));
    }

    @Test
    @DisplayName("브랜드별 최저가 상품 조회 정상 동작 테스트")
    void getLowestPriceItemsByBrands() throws Exception {
        // given
        ItemResponse.LowPriceItemsByBrand lowPriceItemsByBrand = ItemResponse.LowPriceItemsByBrand.builder()
                .brandItemInfos(List.of(
                        ItemResponse.LowPriceItemsByBrand.BrandItemInfo.builder().categoryName(CategoryType.TOP.name()).price(10000).build()
                ))
                .brandCode(BrandCode.A.name())
                .totalPrice(34100)
                .build();
        given(itemFacade.getLowestPriceItemsByBrands()).willReturn(lowPriceItemsByBrand);

        // when & then
        mockMvc.perform(get("/api/v1/items/brands/lowest-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalPrice").value(34100))
                .andExpect(jsonPath("$.data.brandCode").value(BrandCode.A.name()))
                .andExpect(jsonPath("$.data.brandItemInfos[0].categoryName").value(CategoryType.TOP.name()))
                .andExpect(jsonPath("$.data.brandItemInfos[0].price").value(10000));
    }

    @Test
    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회 정상 동작 테스트")
    void getCategoryHighAndLowItem() throws Exception {
        // given
        String categoryName = CategoryType.TOP.name();
        ItemResponse.CategoryHighAndLowItem categoryHighAndLowItem = ItemResponse.CategoryHighAndLowItem.builder()
                .categoryName(categoryName)
                .lowPriceItem(ItemResponse.CategoryHighAndLowItem.CategoryBrandItemInfo.builder().brandCode(BrandCode.A.name()).price(10000).build())
                .highPriceItem(ItemResponse.CategoryHighAndLowItem.CategoryBrandItemInfo.builder().brandCode(BrandCode.C.name()).price(20000).build())
                .build();
        given(itemFacade.getCategoryHighAndLowItem(categoryName)).willReturn(categoryHighAndLowItem);

        // when & then
        mockMvc.perform(get("/api/v1/{categoryName}/high-and-low-item", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.categoryName").value(categoryName))
                .andExpect(jsonPath("$.data.lowPriceItem.brandCode").value(BrandCode.A.name()))
                .andExpect(jsonPath("$.data.lowPriceItem.price").value(10000))
                .andExpect(jsonPath("$.data.highPriceItem.brandCode").value(BrandCode.C.name()))
                .andExpect(jsonPath("$.data.highPriceItem.price").value(20000));
    }

    @Test
    @DisplayName("상품 등록 정상 동작 테스트")
    void registerItem() throws Exception {
        // given
        ItemRequest.RegisterItem registerItemReq = ItemRequest.RegisterItem.builder()
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(10000)
                .build();

        given(itemFacade.registerItem(any())).willReturn(ItemInfo.builder()
                .seqNo(1L)
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(10000)
                .build());

        // when & then
        mockMvc.perform(post("/api/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerItemReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.price").value(10000));
    }

    @Test
    @DisplayName("상품 등록 입력값 오류 테스트")
    void registerItemWithInvalidInput() throws Exception {
        // given
        ItemRequest.RegisterItem registerItemReq = ItemRequest.RegisterItem.builder()
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(-10000)
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerItemReq)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수정 정상 동작 테스트")
    void modifyItem() throws Exception {
        // given
        ItemRequest.ModifyItem modifyItem = ItemRequest.ModifyItem.builder()
                .seqNo(1L)
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(10000)
                .build();
        given(itemFacade.modifyItem(any())).willReturn(ItemInfo.builder()
                .seqNo(1L)
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(10000)
                .build());

        // when & then
        mockMvc.perform(put("/api/v1/items/{seqNo}", modifyItem.getSeqNo())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.price").value(10000));
    }

    @Test
    @DisplayName("상품 수정 입력값 오류 테스트")
    void modifyItemWithInvalidInput() throws Exception {
        // given
        ItemRequest.ModifyItem modifyItem = ItemRequest.ModifyItem.builder()
                .seqNo(1L)
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.TOP)
                .price(10000)
                .build();

        given(itemFacade.modifyItem(any())).willThrow(new BadRequestException("Item not found"));

        // when & then
        mockMvc.perform(put("/api/v1/items/{seqNo}", modifyItem.getSeqNo())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifyItem)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 삭제 정상 동작 테스트")
    void deleteItem() {
        // given
        Long seqNo = 1L;

        // when & then
        assertDoesNotThrow(() -> mockMvc.perform(delete("/api/v1/items/{seqNo}", seqNo)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seqNo)))
                .andExpect(status().isNoContent()));
    }
}