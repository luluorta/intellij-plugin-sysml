package org.apache.sysml.intellij.plugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.apache.sysml.intellij.plugin.adaptors.DMLLexerAdaptor;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey BUILTIN;
    public static final TextAttributesKey TYPE;
    public static final TextAttributes CONSTANT;
    public static final TextAttributes FUNCTION_CALL;
    static{
        Color color = DefaultLanguageHighlighterColors.FUNCTION_CALL.getDefaultAttributes().getForegroundColor();
        TextAttributes attr = DefaultLanguageHighlighterColors.CONSTANT.getDefaultAttributes().clone();
        BUILTIN = createTextAttributesKey("BUILTIN", attr);
        BUILTIN.getDefaultAttributes().setForegroundColor(color);

        FUNCTION_CALL = attr.clone();
        FUNCTION_CALL.setForegroundColor(color);

        attr = CodeInsightColors.INSTANCE_FIELD_ATTRIBUTES.getDefaultAttributes().clone();
        TYPE = createTextAttributesKey("TYPE", attr);
        TYPE.getDefaultAttributes().setForegroundColor(new Color(5, 112, 135));

        CONSTANT = attr.clone();
        color = DefaultLanguageHighlighterColors.CONSTANT.getDefaultAttributes().getForegroundColor();
        CONSTANT.setForegroundColor(color);


    }
    

    public static final TextAttributesKey KEYWORD = createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey FUNCID = createTextAttributesKey("FUNCID", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey FUNCCALL = createTextAttributesKey("FUNCCALL", FUNCTION_CALL);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey BOOLEAN =
            createTextAttributesKey("BOOLEAN", CONSTANT);
    public static final TextAttributesKey CMDVAR =
            createTextAttributesKey("CMDVAR", CONSTANT);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{HighlighterColors.BAD_CHARACTER};
    private static final TextAttributesKey[] BUILTIN_KEYS = new TextAttributesKey[]{BUILTIN};
    private static final TextAttributesKey[] TYPE_KEYS = new TextAttributesKey[]{TYPE};
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] FUNCID_KEYS = new TextAttributesKey[]{FUNCID};
    private static final TextAttributesKey[] FUNCCALL_KEYS = new TextAttributesKey[]{FUNCCALL};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] BOOLEAN_KEYS = new TextAttributesKey[]{BOOLEAN};
    private static final TextAttributesKey[] CMDVAR_KEYS = new TextAttributesKey[]{CMDVAR};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT, BLOCK_COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
    
    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        DMLLexer lexer = new DMLHighlightLexer();
        return new DMLLexerAdaptor(DMLLanguage.INSTANCE, lexer);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (DMLTokenTypes.BUILTINS.contains(tokenType) || DMLTokenTypes.SEQS.contains(tokenType) ) {
            return BUILTIN_KEYS;
        } else if (DMLTokenTypes.TYPES.contains(tokenType)) {
            return TYPE_KEYS;
        } else if (DMLTokenTypes.KEYWORDS.contains(tokenType)) {
            return KEYWORD_KEYS;
        } else if (DMLTokenTypes.FUNCIDS.contains(tokenType)) {
            return FUNCID_KEYS;
        } else if (DMLTokenTypes.FUNCCALLS.contains(tokenType)) {
            return FUNCCALL_KEYS;
        } else if (DMLTokenTypes.NUMBERS.contains(tokenType)) {
            return NUMBER_KEYS;
        } else if (DMLTokenTypes.BOOLEANS.contains(tokenType)) {
            return BOOLEAN_KEYS;
        } else if (DMLTokenTypes.CMDVARS.contains(tokenType)) {
            return CMDVAR_KEYS;
        } else if (DMLTokenTypes.STRINGS.contains(tokenType)) {
            return STRING_KEYS;
        } else if (DMLTokenTypes.COMMENTS.contains(tokenType)) {
            return COMMENT_KEYS;
        } else if (tokenType == DMLTokenTypes.BAD_TOKEN_TYPE) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }

    }
}
