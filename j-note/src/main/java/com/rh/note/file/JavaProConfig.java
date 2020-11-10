package com.rh.note.file;

import com.rh.note.ao.IncludePromptAO;
import com.rh.note.ao.InputPromptItemAO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * java项目目录配置
 */
public class JavaProConfig {

    private final ProLabel proLabel = new ProLabel();

    public @Nullable IncludePromptAO copyToAndFilter(String proLabel) {
        if (MapUtils.isEmpty(this.proLabel.proLabel_proName)) {
            return null;
        }

        List<InputPromptItemAO> itemList = this.proLabel.proLabel_proName.entrySet().stream()
                .map(entry -> new InputPromptItemAO().setDescription(entry.getValue()).setCompleteValue(entry.getKey()))
                .filter(ao -> ao.match(proLabel))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(itemList)) {
            return null;
        }
        return new IncludePromptAO().setAoList(itemList);
    }

    /**
     * 获得项目名
     */
    public String getProPath(String proLabel) {
        return this.proLabel.getProPath(proLabel);
    }

    public String getProLabel(String result) {
        if (StringUtils.isBlank(result)) {
            return null;
        }
        MutableObject<String> mo = new MutableObject<>();
        proLabel.proLabel_proName.forEach((key, value) -> {
            if (value.equals(result)) {
                mo.setValue(key);
            }
        });
        return mo.getValue();
    }

    /**
     * 项目标识
     */
    public static class ProLabel {

        /**
         * key是标识
         * value是项目路径
         */
        private Map<String, String> proLabel_proName = new HashMap<>();

        {
            addPro("m", "maven"); // maven项目
            addPro("sm", "spring"); // spring项目
            addPro("sbm", "spring-boot"); // spring boot项目
            addPro("sbmw", "spring-boot-war"); // spring boot 的war包项目
        }

        /**
         * 添加
         */
        public void addPro(String proLabel, String proName) {
            if (StringUtils.isBlank(proLabel) || StringUtils.isBlank(proName)) {
                return;
            }

            proLabel_proName.put(proLabel, proName);
        }

        /**
         * 获得
         */
        public @Nullable String getProPath(String proLabel) {
            if (StringUtils.isBlank(proLabel)) {
                return null;
            }
            String proName = proLabel_proName.get(proLabel);
            if (StringUtils.isBlank(proName)) {
                return null;
            }
            return proName + "/src/main/java/";
        }
    }

}
