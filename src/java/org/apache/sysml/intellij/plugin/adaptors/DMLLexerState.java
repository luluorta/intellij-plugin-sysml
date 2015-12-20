package org.apache.sysml.intellij.plugin.adaptors;

import org.antlr.intellij.adaptor.lexer.AntlrLexerState;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.IntegerStack;
import org.antlr.v4.runtime.misc.MurmurHash;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLLexerState extends AntlrLexerState {

    public DMLLexerState(int mode, IntegerStack modeStack) {
        super(mode, modeStack);
    }

    @Override
    protected int hashCodeImpl() {
        int hash = MurmurHash.initialize();
        hash = MurmurHash.update(hash, getMode());
        hash = MurmurHash.update(hash, getModeStack());
        return MurmurHash.finish(hash, 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DMLLexerState)) {
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }
        
        return true;
    }
}
