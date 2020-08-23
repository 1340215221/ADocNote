package com.rh.note.register;

import com.rh.note.component.NoteTextPane;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import com.rh.note.file.AdocFile;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * 编辑区域工厂
 */
public class TextPaneFactory extends AbstractFactory {
    @Override
    public NoteTextPane newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        if (MapUtils.isEmpty(attributes)) {
            throw new NoteException(ErrorMessage.PARAMETER_ERROR);
        }
        Object file = attributes.get("adocFile");
        if (!(file instanceof AdocFile)) {
            throw new NoteException(ErrorMessage.PARAMETER_ERROR);
        }
        attributes.remove("adocFile");
        return new NoteTextPane((AdocFile) file);
    }
}