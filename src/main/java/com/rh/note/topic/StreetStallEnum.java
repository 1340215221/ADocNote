package com.rh.note.topic;

import com.rh.note.topic.ss.ConvexDownwardSS;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 地摊枚举
 */
public enum StreetStallEnum {
    convex_downward() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return ConvexDownwardSS.class;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            if (CollectionUtils.isEmpty(numbers)) {
                return Stream.empty();
            }
            return ConvexDownwardSS.match(numbers);
        }
    },
    horizontal_line() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return null;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            return null;
        }
    },
    raised_to_the_right() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return null;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            return null;
        }
    },
    square() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return null;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            return null;
        }
    },
    upper_right_corner() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return null;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            return null;
        }
    },
    vertical_line() {
        @Override
        public Class<? extends BaseStreetStall> getType() {
            return null;
        }

        @Override
        public Stream<BaseStreetStall> match(List<Integer> numbers) {
            return null;
        }
    },
    ;

    /**
     * 获得地摊类型
     */
    public abstract Class<? extends BaseStreetStall> getType();

    /**
     * 匹配生成对应的类型
     */
    public abstract Stream<BaseStreetStall> match(List<Integer> numbers);
    /**
     * 通过数字生成地摊列表
     */
    public static List<BaseStreetStall> generateList(List<Integer> numbers) {
        return Arrays.stream(StreetStallEnum.values())
                .flatMap(e -> e.match(numbers))
                .collect(Collectors.toList());
    }
}
