package org.apache.sysml.intellij.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.apache.sysml.intellij.plugin.adaptors.DMLGrammarParser;
import org.apache.sysml.intellij.plugin.adaptors.DMLLexerAdaptor;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by luluorta on 15-12-17.
 */
public class DMLParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE =
            new IFileElementType(DMLLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        DMLLexer lexer = new DMLLexer(null);
        return new DMLLexerAdaptor(DMLLanguage.INSTANCE, lexer);
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new DMLGrammarParser();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return DMLTokenTypes.WHITESPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return DMLTokenTypes.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return DMLTokenTypes.STRINGS;
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new DMLFileRoot(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    /** Convert from internal parse node (AST they call it) to final PSI node. This
     *  converts only internal rule nodes apparently, not leaf nodes. Leaves
     *  are just tokens I guess.
     */
    @NotNull
    public PsiElement createElement(ASTNode node) {
        return null;
        //return ANTLRv4ASTFactory.createInternalParseTreeNode(node);
    }
}