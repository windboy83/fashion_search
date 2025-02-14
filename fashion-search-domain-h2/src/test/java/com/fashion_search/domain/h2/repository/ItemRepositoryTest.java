package com.fashion_search.domain.h2.repository;

import com.fashion_search.domain.h2.config.JpaConfig;
import com.fashion_search.domain.h2.entities.ItemEntity;
import com.fashion_search.domain.h2.enums.BrandCode;
import com.fashion_search.domain.h2.enums.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles(profiles = {"local", "domain-h2-local"})
@DataJpaTest
@Import(JpaConfig.class)
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

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
        List<ItemEntity> itemEntities = new ArrayList<>();
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
                itemEntities.add(ItemEntity.builder()
                        .brandCode(brandCodes[i])
                        .categoryType(categoryTypes[j])
                        .price(prices.get(i)[j])
                        .build());
            }
        }
        itemRepository.saveAll(itemEntities);
    }

    @Test
    @DisplayName("상품 목록 by 카테고리명")
    void findByCategoryType() {
        // when
        List<ItemEntity> itemEntities = itemRepository.findAllByCategoryType(CategoryType.OUTER);

        // then
        assertFalse(itemEntities.isEmpty());
    }
}