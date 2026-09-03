// Expr.g4
grammar Expr;

prog: stat+;

stat: expr NEWLINE
    | ID '=' expr NEWLINE
    ;

expr: expr ('+'|'-') expr
    | INT
    | ID
    ;

ID: [a-zA-Z]+;
INT: [0-9]+;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;
