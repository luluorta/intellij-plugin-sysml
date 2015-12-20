package org.apache.sysml.intellij.plugin;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.Token;
import org.apache.sysml.intellij.plugin.parser.DMLLexer;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLHighlightLexer extends DMLLexer {
    
    private DMLLexer lookaheadLexer;
    
    public DMLHighlightLexer() { super(null); }

    @Override
    public void setInputStream(IntStream input) {
        super.setInputStream(input);
        lookaheadLexer = new DMLLexer(_input);
    }
    
    private boolean isFuncName(int index) {
        lookaheadLexer.reset();
        _input.seek(index);
        Token lookahead = lookaheadLexer.nextToken();
        if (lookahead.getType() == WHITESPACE) {
            lookahead = lookaheadLexer.nextToken();
        }
        if (lookahead.getType() == ASSIGN || lookahead.getType() == LARROW) {
            lookahead = lookaheadLexer.nextToken();
            if (lookahead.getType() == WHITESPACE) {
                lookahead = lookaheadLexer.nextToken();
            }
            if (lookahead.getType() == FUNCTION ||
                    lookahead.getType() == EXTERNAL_FUNCTION) {
                _input.seek(index);
                return true;
            }
        }
        _input.seek(index);
        return false;
    }

    private boolean isFuncCall(int index) {
        lookaheadLexer.reset();
        _input.seek(index);
        Token lookahead = lookaheadLexer.nextToken();
        if (lookahead.getType() == WHITESPACE) {
            lookahead = lookaheadLexer.nextToken();
        }
        if (lookahead.getType() == LPAREN) {
            _input.seek(index);
            return true;
        }
        _input.seek(index);
        return false;
    }

    private boolean isVarName(int index) {
        lookaheadLexer.reset();
        _input.seek(index);
        Token lookahead = lookaheadLexer.nextToken();
        if (lookahead.getType() == WHITESPACE) {
            lookahead = lookaheadLexer.nextToken();
        }
        if (lookahead.getType() == ASSIGN) {
            _input.seek(index);
            return true;
        }
        _input.seek(index);
        return false;
    }

    private boolean isMatrix(int index) {
        lookaheadLexer.reset();
        _input.seek(index);
        Token lookahead = lookaheadLexer.nextToken();
        if (lookahead.getType() == WHITESPACE) {
            lookahead = lookaheadLexer.nextToken();
        }
        if (lookahead.getType() == LBRACKET) {
            _input.seek(index);
            return true;
        }
        _input.seek(index);
        return false;
    }
    
    @Override
    public Token emit() {
        int currentIndex = _input.index();
        
        if (_type == ID) {
            if (isFuncName(currentIndex)) {
                _type = FUNCID;
            } else if (isFuncCall(currentIndex)) {
                _type = FUNCCALL;
            }
        } else if (_type == BUILTIN_FUNCTION_ID) {
            if (isFuncName(currentIndex)) {
                _type = FUNCID;
            } else if (!isFuncCall(currentIndex)) {
                if (isMatrix(currentIndex)) {
                    _type = Matrix;
                } else {
                    _type = ID;
                }
            }
        }

        return super.emit();
    }
    
}
