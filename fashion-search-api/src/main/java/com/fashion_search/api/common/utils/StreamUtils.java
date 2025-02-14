package com.fashion_search.api.common.utils;

import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.ToIntFunction;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class StreamUtils {

    public static <E> int sum(List<E> list, ToIntFunction<E> function) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return list.stream().mapToInt(function).sum();
    }
}
