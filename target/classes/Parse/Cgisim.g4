grammar Cgisim;
/** 起始规则 语法分析器起点 */
prog:	stat+ ;

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
    |	integer             # Int
    |   ID                  # id
    |   BOOL                # bool
    |	'(' expr ')'        # parens
    ;

integer:    '-'integer  # negInt
       |    NATURAL     # posInt
       ;

ID      : [a-zA-Z_][a-zA-Z0-9_]* ;   // 匹配标识符
BOOL    : 'true'|'false' ;
NATURAL : [0-9]+ ;      // 匹配整数
NEWLINE : '\r'? '\n' ;     // 新行 即语句终止标志
WS      : [ \t]+ -> skip ; // 丢弃空白字符

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

