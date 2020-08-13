package com.rh.note.register;

import com.rh.note.component.AdocTextArea;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.AdocException;
import com.rh.note.file.AdocFile;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * 编辑区域工厂
 */
public class TextAreaFactory extends AbstractFactory {
    @Override
    public AdocTextArea newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        if (MapUtils.isEmpty(attributes)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }
        Object file = attributes.get("adocFile");
        if (!(file instanceof AdocFile)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }
        attributes.remove("adocFile");
        return new AdocTextArea((AdocFile) file);
    }
}