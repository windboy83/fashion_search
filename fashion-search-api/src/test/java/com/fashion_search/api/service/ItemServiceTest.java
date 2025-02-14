package com.fashion_search.api.service;

import com.fashion_search.api.common.exception.NotFoundException;
import com.fashion_search.domain.h2.dto.command.ItemCommand;
import com.fashion_search.domain.h2.dto.info.ItemInfo;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = {"local"})
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    List<ItemCommand.RegisterItem> registerItems = new ArrayList<>();

    @BeforeEach
    void setUp() {
        /*
        브랜드 상의 아우터 바지 스니커즈 가방 모자 양말 액세서리
        A 11,200 5,500 4,200 9,000 2,000 1,700 1,800 2,300
        B 10,500 5,900 3,800 9,100 2,100 2,000 2,000 2,200
        C 10,000 6,200 3,300 9,200 2,200 1,900 2,200 2,100
        D 10,100 5,100 3,000 9,500 2,500 1,500 2,400 2,000
        E 10,700 5,000 3,800 9,900 2,300 1,800 2,100 2,100
        F 11,200 7,200 4,000 9,300 2,100 1,600 2,300 1,900
        G 10,500 5,800 3,900 9,000 2,200 1,700 2,100 2,000
        H 10,800 6,300 3,100 9,700 2,100 1,600 2,000 2,000
        I 11,400 6,700 3,200 9,500 2,400 1,700 1,700 2,400
        */

        // given
        BrandCode[] brandCodes = BrandCode.values();
        CategoryType[] categoryTypes = CategoryType.values();
        List<Integer[]> prices = List.of(
                new Integer[]{11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300},
                new Integer[]{10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200},
                new Integer[]{10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100},
                new Integer[]{10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000},
                new Integer[]{10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100},
                new Integer[]{11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900},
                new Integer[]{10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000},
                new Integer[]{10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000},
                new Integer[]{11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400}
        );
        for (int i = 0; i < brandCodes.length; i++) {
            for (int j = 0; j < categoryTypes.length; j++) {
                ItemCommand.RegisterItem registerItem = ItemCommand.RegisterItem.builder()
                        .brandCode(brandCodes[i])
                        .categoryType(categoryTypes[j])
                        .price(prices.get(i)[j])
                        .build();
                registerItems.add(registerItem);

                itemService.registerItem(registerItem);
            }
        }
    }

    @Test
    @DisplayName("상품 수정")
    void modifyItem() {
        ItemCommand.RegisterItem registerItem = registerItems.getFirst();
        ItemInfo itemInfo = itemService.registerItem(registerItem);
        ItemCommand.ModifyItem modifyItem = ItemCommand.ModifyItem.builder()
                .seqNo(itemInfo.getSeqNo())
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.OUTER)
                .price(20000)
                .build();
        ItemInfo modifiedItem = itemService.modifyItem(modifyItem);

        assertEquals(modifyItem.getPrice(), modifiedItem.getPrice());
    }

    @Test
    @DisplayName("상품이 존제하지 않는 경우 수정 오류")
    void modifyItemWithInvalidSeqNo() {
        ItemCommand.ModifyItem modifyItem = ItemCommand.ModifyItem.builder()
                .seqNo(9999L)
                .brandCode(BrandCode.A)
                .categoryType(CategoryType.OUTER)
                .price(20000)
                .build();

        assertThrows(NotFoundException.class, () -> itemService.modifyItem(modifyItem));
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteItem() {
        ItemCommand.RegisterItem registerItem = registerItems.getFirst();
        ItemInfo itemInfo = itemService.registerItem(registerItem);
        itemService.deleteItem(itemInfo.getSeqNo());
        ItemInfo deletedItem = itemService.getItemBy(itemInfo.getSeqNo());

        assertNull(deletedItem);
    }

    @Test
    @DisplayName("카테고리별 최저가 상품 조회")
    void getLowestPriceItemsByCategory() {
        // when
        List<ItemInfo> items = itemService.getLowestPriceItemsByCategory();

        // then
        assertEquals(8, items.size());
        assertEquals(10000, items.get(0).getPrice());
        assertEquals(5000, items.get(1).getPrice());
        assertEquals(3000, items.get(2).getPrice());
        assertEquals(9000, items.get(3).getPrice());
        assertEquals(2000, items.get(4).getPrice());
        assertEquals(1500, items.get(5).getPrice());
        assertEquals(1700, items.get(6).getPrice());
        assertEquals(1900, items.get(7).getPrice());
    }

    @Test
    @DisplayName("브랜드별 최저가 상품 조회")
    void getLowestPriceItemsByBrands() {
        // when
        List<ItemInfo> items = itemService.getLowestPriceItemsByBrands();

        // then
        assertEquals(8, items.size());
        assertEquals(10100, items.get(0).getPrice());
        assertEquals(5100, items.get(1).getPrice());
        assertEquals(3000, items.get(2).getPrice());
        assertEquals(9500, items.get(3).getPrice());
        assertEquals(2500, items.get(4).getPrice());
        assertEquals(1500, items.get(5).getPrice());
        assertEquals(2400, items.get(6).getPrice());
        assertEquals(2000, items.get(7).getPrice());
    }
}