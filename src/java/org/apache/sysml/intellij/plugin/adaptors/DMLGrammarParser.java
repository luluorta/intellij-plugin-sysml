package org.apache.sysml.intellij.plugin.adaptors;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.parser.AntlrParser;
import org.antlr.intellij.adaptor.parser.SyntaxErrorListener;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.sysml.intellij.plugin.DMLLanguage;
import org.apache.sysml.intellij.plugin.parser.DMLParser;

/**
 * Created by luluorta on 15-12-17.
 */
public class DMLGrammarParser extends AntlrParser<DMLParser> {
    public DMLGrammarParser() {
        super(DMLLanguage.INSTANCE);
    }

    @Override
    protected DMLParser createParserImpl(TokenStream tokenStream, IElementType root, PsiBuilder builder) {
        DMLParser parser = new DMLParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        return parser;
    }

    @Override
    protected ParseTree parseImpl(DMLParser parser, IElementType root, PsiBuilder builder) {
        return parser.dmlprogram();
    }
}