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
 
 /** A grammar for SystemML DML tokens */
lexer grammar DMLLexer;

tokens {
	FUNCID,
	FUNCCALL
}

IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
PARFOR: 'parfor';
IN: 'in';
SEQ: 'seq';
SOURCE: 'source';
IFDEF: 'ifdef';
SETWD: 'setwd';
AS: 'as';
IMPLEMENTED: 'implemented';
PRINT: 'print';
STOP: 'stop';

COLON        : ':'                    ;
COLONCOLON   : '::'                   ;
COMMA        : ','                    ;
SEMI         : ';'                    ;
ASSIGN       : '='                    ;
LARROW       : '<-'                   ;
LPAREN       : '('                    ;
RPAREN       : ')'                    ;
LBRACKET     : '['                    ;
RBRACKET     : ']'                    ;
LBRACE       : '{'                    ;
RBRACE       : '}'                    ;

POWER        : '^'                    ;
MATRIXMUL     : '%*%'                 ;
MOD          : '%%'                   ;
INTDIV       : '%/%'                  ;
MULTIPLY     : '*'                    ;
DIVIDE       : '/'                    ;
PLUS         : '+'                    ;
MINUS        : '-'                    ;

LT           : '<'                    ;
LE           : '<='                   ;
GT           : '>'                    ;
GE           : '>='                   ;
EQ           : '=='                   ;
NE          : '!='                    ;    

NOT          : '!'                    ;
OR           : '|' | '||'             ;
AND          : '&' | '&&'             ;



FUNCTION: 'function';
EXTERNAL_FUNCTION: 'externalFunction';
RETURN: 'return';

ValueType: 'int' | 'integer' | 'string' | 'boolean' | 'double'
            | 'Int' | 'Integer' | 'String' | 'Boolean' | 'Double';
            
Matrix: 'Matrix' | 'MATRIX';

INT : DIGIT+  [Ll]?;
// BOOLEAN : 'TRUE' | 'FALSE';
DOUBLE: DIGIT+ '.' DIGIT* EXP? [Ll]?
| DIGIT+ EXP? [Ll]?
| '.' DIGIT+ EXP? [Ll]?
;
BOOLEAN: 'TRUE' | 'FALSE';
DIGIT: '0'..'9';
fragment EXP : ('E' | 'e') ('+' | '-')? INT ;

COMMANDLINE_NAMED_ID: '$' ALPHABET (ALPHABET|DIGIT|'_')*;
COMMANDLINE_POSITION_ID: '$' DIGIT+;

BUILTIN_FUNCTION_ID:  't' | 'read' | 'write'
    | 'as.scalar' | 'as.matrix' | 'as.double' | 'as.integer' | 'as.logical'
    | 'append' | 'cbind' | 'min' | 'max' | 'nrow' | 'ncol' | 'length' 
    | 'prod' | 'matrix' | 'rand' | 'rbind' | 'removeEmpty' | 'replace' | 'sum' 
    | 'pmin' | 'pmax' | 'rowIndexMax' | 'rowIndexMin' | 'ppred'
    | 'mean' | 'avg' | 'moment' | 'colSums' | 'colMeans' | 'colMaxs' | 'colMins'
    | 'cov' | 'table' | 'cdf' | 'pnorm' | 'pexp' | 'pchisq' | 'pf' | 'pt'
    | 'icdf' | 'qnorm' | 'qexp' | 'qchisq' | 'qf' | 'qt'
    | 'aggregate' | 'interQuartileMean' | 'quantile' | 'median'
    | 'rowSums' | 'rowMeans' | 'rowMaxs' | 'rowMins'
;

ALPHABET : [a-zA-Z];
ID : (ALPHABET (ALPHABET|DIGIT|'_')*  '::')? ALPHABET (ALPHABET|DIGIT|'_')*;



// supports single and double quoted string with escape characters
STRING: '"' ( ESC | ~[\\"] )*? '"' | '\'' ( ESC | ~[\\'] )*? '\'';
fragment ESC : '\\' [abtnfrv"'\\] ;
// Comments, whitespaces and new line
LINE_COMMENT : '#' .*? '\r'? '\n' -> channel(HIDDEN) ;
MULTILINE_BLOCK_COMMENT : '/*' .*? '*/' -> channel(HIDDEN) ;
WHITESPACE : (' ' | '\t' | '\r' | '\n')+ -> channel(HIDDEN) ;