package com.rh.note.factory.component

import com.rh.note.constant.ErrorMessage
import com.rh.note.exception.AdocException
import com.rh.note.model.file.AdocFile
import com.rh.note.view.AdocTextArea
import org.apache.commons.collections4.MapUtils

/**
 * 编辑区域工厂
 */
class TextAreaFactory extends AbstractFactory {
    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        if (MapUtils.isEmpty(attributes)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR)
        }
        def file = attributes.adocFile
        if (!(file instanceof AdocFile)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR)
        }
        attributes.remove('adocFile')
        return new AdocTextArea(file)
    }
}