package org.apache.sysml.intellij.plugin.adaptors;

import com.intellij.lang.Language;
import org.antlr.intellij.adaptor.lexer.AntlrLexerAdapter;
import org.antlr.v4.runtime.Lexer;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLLexerAdaptor extends AntlrLexerAdapter<DMLLexerState> {
    private static final DMLLexerState INITIAL_STATE = new DMLLexerState(Lexer.DEFAULT_MODE, null);

    public DMLLexerAdaptor(Language language, DMLLexer lexer) {
        super(language, lexer);
    }

    @Override
    protected DMLLexerState getInitialState() {
        return INITIAL_STATE;
    }

    @Override
    protected DMLLexerState getLexerState(Lexer lexer) {
        if (lexer._modeStack.isEmpty()) {
            return new DMLLexerState(lexer._mode, null);
        }

        return new DMLLexerState(lexer._mode, lexer._modeStack);
    }
}
