package com.rh.note.register;

import com.rh.note.component.NoteTextPane;
import com.rh.note.exception.GErrorCodeEnum;
import com.rh.note.exception.GNoteException;
import com.rh.note.grammar.ITitleGrammar;
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
            throw new GNoteException(GErrorCodeEnum.PARAMETER_ERROR);
        }
        Object titleGrammar = attributes.get("titleGrammar");
        if (!(titleGrammar instanceof ITitleGrammar)) {
            throw new GNoteException(GErrorCodeEnum.PARAMETER_ERROR);
        }
        attributes.remove("titleGrammar");
        return new NoteTextPane((ITitleGrammar) titleGrammar);
    }
}