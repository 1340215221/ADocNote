package com.rh.note.syntax;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url地址语法
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUrlSyntax {
    /**
     * url地址
     */
    @NonNull
    private String url;
    /**
     * 显示内容
     */
    private String showContent;

    private static @Nullable HttpUrlSyntax getInstance(String url, String showContent) {
        if (StringUtils.isBlank(url) || !url.startsWith("http://") && !url.startsWith("https://")) {
            return null;
        }
        HttpUrlSyntax sugar = new HttpUrlSyntax();
        sugar.url = url;
        sugar.showContent = showContent;
        return sugar;
    }

    /**
     * 网络请求地址语法 集合
     */
    public static class HttpUrlSyntaxList {

        private static final String regex = "(http[s]?://.+?)\\[(.*?)\\]";
        private final List<Item> list = new ArrayList<>();

        public @Nullable HttpUrlSyntax.HttpUrlSyntaxList init(String lineContent) {
            if (StringUtils.isBlank(lineContent)) {
                return null;
            }
            Matcher matcher = Pattern.compile(regex).matcher(lineContent);
            while (matcher.find()) {
                String url = matcher.group(1);
                if (StringUtils.isBlank(url)) {
                    continue;
                }
                HttpUrlSyntax sugar = getInstance(url, matcher.group(2));
                if (sugar == null) {
                    continue;
                }
                Item item = new Item();
                item.sugar = sugar;
                item.startOffset = matcher.start(1);
                item.endOffset = matcher.end(1);
                list.add(item);
            }
            return list.isEmpty() ? null : this;
        }

        public @Nullable HttpUrlSyntax get(int offset) {
            if (list.isEmpty()) {
                return null;
            }
            return list.stream()
                    .filter(item -> item.match(offset))
                    .map(item -> item.sugar)
                    .findFirst()
                    .orElse(null);
        }
    }

    private static class Item {
        /**
         * 起始偏移量
         */
        @NonNull
        private int startOffset;
        /**
         * 结束偏移量
         */
        @NonNull
        private int endOffset;
        /**
         * 语法对象
         */
        @NonNull
        private HttpUrlSyntax sugar;

        public boolean match(int offset) {
            return startOffset <= offset && offset < endOffset;
        }
    }
}
