package org.apache.sysml.intellij.plugin;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenElementType;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLTokenTypes {
    public static IElementType BAD_TOKEN_TYPE = new IElementType("BAD_TOKEN", DMLLanguage.INSTANCE);

    public static final List<TokenElementType> ELEMENT_TYPES =
            ElementTypeFactory.getTokenElementTypes(DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames));
    
    public static final TokenSet COMMENTS = ElementTypeFactory.createTokenSet(
            DMLLanguage.INSTANCE,
            Arrays.asList(DMLLexer.tokenNames),
            DMLLexer.MULTILINE_BLOCK_COMMENT,
            DMLLexer.LINE_COMMENT);

    public static final TokenSet WHITESPACES =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.WHITESPACE);

    public static final TokenSet KEYWORDS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.IF, DMLLexer.ELSE, DMLLexer.WHILE, DMLLexer.FOR, DMLLexer.PARFOR, DMLLexer.IN,
                    DMLLexer.SEQ, DMLLexer.SOURCE, DMLLexer.IFDEF, DMLLexer.SETWD, DMLLexer.AS,DMLLexer.IMPLEMENTED,
                    DMLLexer.FUNCTION, DMLLexer.EXTERNAL_FUNCTION, DMLLexer.RETURN, DMLLexer.COMMA, DMLLexer.SEMI,
                    DMLLexer.PRINT, DMLLexer.STOP);

    public static final TokenSet TYPES =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.ValueType,
                    DMLLexer.Matrix);

    public static final TokenSet FUNCIDS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.FUNCID);

    public static final TokenSet FUNCCALLS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.FUNCCALL);

    public static final TokenSet NUMBERS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.INT, 
                    DMLLexer.DOUBLE);

    public static final TokenSet BOOLEANS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.BOOLEAN);

    public static final TokenSet CMDVARS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.COMMANDLINE_NAMED_ID,
                    DMLLexer.COMMANDLINE_POSITION_ID);

    public static final TokenSet STRINGS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.STRING);

    public static final TokenSet BUILTINS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.BUILTIN_FUNCTION_ID);

    public static final TokenSet SEQS =
            ElementTypeFactory.createTokenSet(
                    DMLLanguage.INSTANCE,
                    Arrays.asList(DMLLexer.tokenNames),
                    DMLLexer.SEQ);
}
