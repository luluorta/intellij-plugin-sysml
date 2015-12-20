/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/** A grammar for SystemMl DML written in ANTLR v4. */
parser grammar DMLParser;

options {
	tokenVocab=DMLLexer;
}


// DML Program is a list of expression
// For now, we only allow global function definitions (not nested or inside a while block)
dmlprogram: (statement | functionStatement)* EOF;

statement:
    // ------------------------------------------
    // ImportStatement
    SOURCE LPAREN STRING RPAREN  AS ID SEMI*       # ImportStatement
    | SETWD  LPAREN STRING RPAREN SEMI*                          # PathStatement
    // ------------------------------------------
    // Treat function call as AssignmentStatement or MultiAssignmentStatement
    // For backward compatibility and also since the behavior of foo() * A + foo() ... where foo returns A
    // Convert FunctionCallIdentifier(paramExprs, ..) -> source
    | // TODO: Throw an informative error if user doesnot provide the optional assignment
    ( dataIdentifier (ASSIGN|LARROW) )? functionIdentifier LPAREN (parameterizedExpression (COMMA parameterizedExpression)* )? RPAREN SEMI*  # FunctionCallAssignmentStatement
    | LBRACKET dataIdentifier (COMMA dataIdentifier)* RBRACKET ASSIGN functionIdentifier LPAREN (parameterizedExpression (COMMA parameterizedExpression)* )? RPAREN SEMI*  # FunctionCallMultiAssignmentStatement
    // {notifyErrorListeners("Too many parentheses");}
    // ------------------------------------------
    // AssignmentStatement
    | dataIdentifier (ASSIGN|LARROW) IFDEF LPAREN dataIdentifier COMMA  expression RPAREN SEMI*   # IfdefAssignmentStatement
    | dataIdentifier (ASSIGN|LARROW) expression SEMI*   # AssignmentStatement
    // ------------------------------------------
    // We don't support block statement
    // | '{' body+=expression ';'* ( body+=expression ';'* )*  '}' # BlockStatement
    // ------------------------------------------
    // IfStatement
    | IF LPAREN expression RPAREN (statement SEMI* | LBRACE (statement SEMI*)*  RBRACE)  (ELSE (statement SEMI* | LBRACE (statement SEMI*)*  RBRACE))?  # IfStatement
    // ------------------------------------------
    // ForStatement & ParForStatement
    | FOR LPAREN ID IN iterablePredicate (COMMA strictParameterizedExpression)* RPAREN (statement SEMI* | LBRACE (statement SEMI* )*  RBRACE)     # ForStatement
    // Convert strictParameterizedExpression to HashMap<String, String> for parForParams
    | PARFOR LPAREN ID IN iterablePredicate (COMMA strictParameterizedExpression)* RPAREN (statement SEMI* | LBRACE (statement SEMI*)*  RBRACE)   # ParForStatement
    | WHILE LPAREN expression RPAREN (statement SEMI* | LBRACE (statement SEMI*)* RBRACE)  # WhileStatement
    // ------------------------------------------
;

iterablePredicate:
    expression COLON expression                              #IterablePredicateColonExpression
    | SEQ LPAREN expression COMMA expression COMMA expression RPAREN #IterablePredicateSeqExpression
    ;

functionStatement:
    // ------------------------------------------
    // FunctionStatement & ExternalFunctionStatement
    // small change: only allow typed arguments here ... instead of data identifier
    ID (ASSIGN|LARROW) FUNCTION LPAREN ( typedArgNoAssign (COMMA typedArgNoAssign)* )? RPAREN  ( RETURN LPAREN ( typedArgNoAssign (COMMA typedArgNoAssign)* )? RPAREN )? LBRACE (statement SEMI*)* RBRACE # InternalFunctionDefExpression
    | ID (ASSIGN|LARROW) EXTERNAL_FUNCTION LPAREN ( typedArgNoAssign (COMMA typedArgNoAssign)* )? RPAREN  ( RETURN LPAREN ( typedArgNoAssign (COMMA typedArgNoAssign)* )? RPAREN )?   IMPLEMENTED IN LPAREN ( strictParameterizedKeyValueString (COMMA strictParameterizedKeyValueString)* )? RPAREN SEMI*    # ExternalFunctionDefExpression
    // ------------------------------------------
;

// Other data identifiers are typedArgNoAssign, parameterizedExpression and strictParameterizedExpression
dataIdentifier:
    // ------------------------------------------
    // IndexedIdentifier
    ID LBRACKET (expression (COLON expression)?)? COMMA (expression (COLON expression)?)? RBRACKET # IndexedExpression
    // ------------------------------------------
    | ID                                            # SimpleDataIdentifierExpression
    | COMMANDLINE_NAMED_ID                          # CommandlineParamExpression
    | COMMANDLINE_POSITION_ID                       # CommandlinePositionExpression
;

expression:
    // ------------------------------------------
    // BinaryExpression
    // power
    <assoc=right> expression POWER expression  # PowerExpression
    // unary plus and minus
    | (MINUS|PLUS) expression                        # UnaryExpression
    // sequence - since we are only using this into for
    //| left=expression op=':' right=expression             # SequenceExpression
    // matrix multiply
    | expression MATRIXMUL expression           # MatrixMulExpression
    // modulus and integer division
    | expression (MOD | INTDIV ) expression # ModIntDivExpression
    // arithmetic multiply and divide
    | expression (MULTIPLY|DIVIDE) expression       # MultDivExpression
    // arithmetic addition and subtraction
    | expression (PLUS|MINUS) expression       # AddSubExpression
    // ------------------------------------------
    // RelationalExpression
    | expression (GT|GE|LT|LE|EQ|NE) expression # RelationalExpression
    // ------------------------------------------
    // BooleanExpression
    // boolean not
    | NOT expression # BooleanNotExpression
    // boolean and
    | expression AND expression # BooleanAndExpression
    // boolean or
    | expression OR expression # BooleanOrExpression

    // ---------------------------------
    // only applicable for builtin function expressions
    | BUILTIN_FUNCTION_ID LPAREN (parameterizedExpression (COMMA parameterizedExpression)* )? RPAREN SEMI*  # BuiltinFunctionExpression

    // 4. Atomic
    | LPAREN expression RPAREN                       # AtomicExpression

    // Should you allow indexed expression here ?
    // | '[' targetList+=expression (',' targetList+=expression)* ']'  # MultiIdExpression

    | BOOLEAN                                       # ConstBooleanIdExpression
    | INT                                           # ConstIntIdExpression
    | DOUBLE                                        # ConstDoubleIdExpression
    | STRING                                        # ConstStringIdExpression
    | dataIdentifier                                # DataIdExpression
    // Special
    // | 'NULL' | 'NA' | 'Inf' | 'NaN'
;

typedArgNoAssign : ml_type ID;
parameterizedExpression : (ID ASSIGN)? expression;
strictParameterizedExpression : ID ASSIGN expression ;
strictParameterizedKeyValueString : ID ASSIGN STRING ;

ml_type :  ValueType | Matrix LBRACKET ValueType RBRACKET;


functionIdentifier: BUILTIN_FUNCTION_ID | ID;