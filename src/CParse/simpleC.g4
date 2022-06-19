grammar simpleC;
/** 起始规则 语法分析器起点 */
prog:	'int' 'main' '(' ')' block ;

block:  '{' statement+ '}'  # NormalBlock
     |  '{' '}'             # EmptyBlock
     ;

statement   :   'if' '(' expr ')' block else    # If
            |   'while'  '(' expr ')' block     # While
            |   'return' expr NEWLINE           # Return
            |   stat                            # Stat_only
            ;

else:   'else' block        # ElseBlock
    |   'else' statement    # ElseState
    |                       # ElseEmpty
    ;

//elif:   'else' 'if' '(' expr ')' block elif     # Elif_
//    |   'else' block                            # Else
//    |                                           # Empty
//    ;


stat:   expr NEWLINE        # printExpr
    |   ID '=' expr NEWLINE # assign
    |   NEWLINE             # blank
    ;

expr:	expr op=('*'|'/'|'%') expr # MulDiv
    |	expr op=('+'|'-') expr # AddSub
    |   expr op=('<='|'<'|'>='|'>') expr # Compare
    |   expr op=('=='|'!=') expr    # Equal
    |   expr '&&' expr # And
    |   expr '||' expr # Or
    |   arr                 # Array
    |	integer             # Int
    |   BOOL                # bool
    |   ID                  # id
    |	'(' expr ')'        # parens
    ;

arr:    ID '[' expr ']' ;

integer:    '-'integer  # negInt
       |    NATURAL     # posInt
       ;

ID      : [a-zA-Z_][a-zA-Z0-9_]* ;   // 匹配标识符
BOOL    : 'true'|'false' ;
NATURAL : [0-9]+ ;      // 匹配整数
NEWLINE : ';' ;     // 新行 即语句终止标志
WS      : [ \t\n\r]+ -> skip ; // 丢弃空白字符

MUL     : '*' ;
DIV     : '/' ;
ADD     : '+' ;
SUB     : '-' ;
MOD     : '%' ;
L       : '<' ;
G       : '>' ;
EQUAL   : '==' ;
NE      : '!=' ;
LE      : '<=' ;
GE      : '>=' ;
AND     : '&&' ;
OR      : '||' ;