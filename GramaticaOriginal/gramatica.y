%{
  import java.io.*;
%}

%token  PROGRAM FUNCTION PROCEDURE
%token VAR INTEGER BOOLEAN REAL 
%token BEGIN END IF THEN ELSE WHILE DO READLN WRITELN 
%token ASSIGN DIV MOD AND OR NOT
%token TRUE FALSE
%token LEQ LE GRE GEQ EQ NEQ
%token LITERAL ID NUM 

%right EQ
%nonassoc LEQ LE GR GEQ NEQ 
%left '+' '-' OR
%left '*' DIV MOD AND
%left NOT


%%

program : PROGRAM ID ';' declarationOpc compoundStmt '.'
        ;

declarationOpc : varDeclarations procDeclarations ;

varDeclarations : VAR declarationList
                |
                ;

declarationList : declaration ';' declarationList
                | declaration ';'
          	| error { System.out.println("Erro na declaração, linha: " + lexer.getLine() ); }
                ;

declaration : idList ':' type ;

idList : ID
       | ID ',' idList
       ;

type : INTEGER
     | BOOLEAN
     | REAL
     ;

procDeclarations : procDec procDeclarations
                 |
                 ;

procDec : procHeader declarationOpc compoundStmt ';'
        | funcHeader declarationOpc compoundStmt ';'
        ;

procHeader : PROCEDURE ID argumentList ';' ;

funcHeader : FUNCTION ID argumentList ':' type ';' ;

argumentList : '(' arguments ')'
             | 
             ;

arguments : declaration ';' arguments
          | declaration
          ;

compoundStmt : BEGIN statementList END ;

statementList : statement ';' statementList
              | statement
              | error ';' { System.out.println("Erro nos comandos, linha: " + lexer.getLine() ); }
              ;

statement : ID ASSIGN expression
          | IF expression THEN statement
          | IF expression THEN statement ELSE statement
          | WHILE expression DO statement
          | compoundStmt
          | READLN '(' ID ')' 
          | WRITELN '(' printList ')'
          | ID '(' expressionList ')'
          |
          ;

printList : LITERAL printList2
          | expression printList2
          ;

printList2 : ',' printList
           |
           ;

expression : expression '+' expression
           | expression '-' expression
           | expression OR expression
           | expression '*' expression
           | expression DIV expression
           | expression MOD expression
           | expression AND expression
           | expression LE expression
           | expression LEQ expression
           | expression EQ expression
           | expression GEQ expression
           | expression GR expression
           | expression NEQ expression
           | NOT expression
           | '(' expression ')'
           | ID
           | ID '(' expressionList ')'
           | NUM
           | TRUE
           | FALSE                 
           ;


expressionList : expression
               | expression ',' expressionList
               |
               ;

%%

  private Yylex lexer;


  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e.getMessage());
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("> ");
      interactive = true;
      yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();
    
    if (interactive) {
      System.out.println();
      System.out.println("done!");
    }
  }
