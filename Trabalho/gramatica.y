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

%type <sval> ID
%type <ival> NUM
%type <obj> type
%type <obj> expression


%%

program : { currEscopo = ""; } PROGRAM ID ';'{ currClass = ClasseID.VarGlobal; } declarationOpc compoundStmt '.'
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

type : INTEGER { $$ = Tp_INT; }
     | BOOLEAN { $$ = Tp_BOOL; }
     | REAL    { $$ = Tp_FLOAT; }
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
          | IF expression THEN statement                { if (((TS_entry)$2).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)$2).getTipo());
                                                          }
                                                        } 
          | IF expression THEN statement ELSE statement { if (((TS_entry)$2).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)$2).getTipo());
                                                          }
                                                        } 
          | WHILE expression DO statement               { if (((TS_entry)$2).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)$2).getTipo());
                                                          }
                                                        } 
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

expression : expression '+' expression { $$ = validaTipo('+', (TS_entry)$1, (TS_entry)$3); }
           | expression '-' expression { $$ = validaTipo('-', (TS_entry)$1, (TS_entry)$3); }
           | expression OR expression  { $$ = validaTipo(OR, (TS_entry)$1, (TS_entry)$3); }
           | expression '*' expression { $$ = validaTipo('*', (TS_entry)$1, (TS_entry)$3); }
           | expression DIV expression { $$ = validaTipo(DIV, (TS_entry)$1, (TS_entry)$3); }
           | expression MOD expression { $$ = validaTipo(MOD, (TS_entry)$1, (TS_entry)$3); }
           | expression AND expression { $$ = validaTipo(AND, (TS_entry)$1, (TS_entry)$3); }
           | expression LE expression  { $$ = validaTipo(LE, (TS_entry)$1, (TS_entry)$3); }
           | expression LEQ expression { $$ = validaTipo(LEQ, (TS_entry)$1, (TS_entry)$3); }
           | expression EQ expression  { $$ = validaTipo(EQ, (TS_entry)$1, (TS_entry)$3); }
           | expression GEQ expression { $$ = validaTipo(GEQ, (TS_entry)$1, (TS_entry)$3); }
           | expression GR expression  { $$ = validaTipo(GR, (TS_entry)$1, (TS_entry)$3); }
           | expression NEQ expression { $$ = validaTipo(NEQ, (TS_entry)$1, (TS_entry)$3); }
           | NOT expression            { $$ = validaTipo(NOT, (TS_entry)$2, null); }
           | '(' expression ')'        { $$ = $2; }
           | ID                        { TS_entry nodo = ts.pesquisa($1);
                                         if (nodo == null) {
                                            yyerror("(sem) var <" + $1 + "> nao declarada");                
                                         } else {
                                            $$ = nodo.getTipo();
                                         }
                                       }   
           | ID '(' expressionList ')' { $$ = Tp_ERRO; }
           | NUM                       { $$ = Tp_INT; }
           | TRUE                      { $$ = Tp_BOOL; }
           | FALSE                     { $$ = Tp_BOOL; }  
           ;


expressionList : expression
               | expression ',' expressionList
               |
               ;

%%

  private Yylex lexer;

  private TabSimb ts;

  public static TS_entry Tp_INT =  new TS_entry("integer", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_BOOL =  new TS_entry("boolean", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_FLOAT =  new TS_entry("real", null, "", ClasseID.TipoBase);
  public static TS_entry Tp_ERRO = new TS_entry("_erro_", null, "", ClasseID.TipoBase);

  private String currEscopo;
  private ClasseID currClass;

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
    System.err.println ("Erro (linha: "+ lexer.getLine() + ")\tMensagem: "+error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);

    ts = new TabSimb();

    ts.insert(Tp_ERRO);
    ts.insert(Tp_INT);
    ts.insert(Tp_FLOAT);
    ts.insert(Tp_BOOL);

  }

  public void setDebug(boolean debug) {
    yydebug = debug;
  }

  public void listarTS() { ts.listar();}

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

    yyparser.listarTS();
    
    if (interactive) {
      System.out.println();
      System.out.println("done!");
    }
  }

  TS_entry validaTipo (int operador, TS_entry A, TS_entry B) {

    //operacoes aritmeticas

    if (operador == '+' || operador == '-' || operador == '*') {
      if ( A == Tp_INT && B == Tp_INT) {
        return Tp_INT;
      }
      else if ( (A == Tp_FLOAT && (B == Tp_INT || B == Tp_FLOAT)) || (B == Tp_FLOAT && (A == Tp_INT || A == Tp_FLOAT)) ) {
        return Tp_FLOAT;
      }
      else {
        yyerror("(sem) tipos incomp. para soma: " + A.getTipoStr() + " + " + B.getTipoStr());
      }          
    }

    //operacoes logicas

    if (operador == OR || operador == AND || operador == NOT) {
      if (A == Tp_BOOL && B == Tp_BOOL) {
        return Tp_BOOL;
      } else {
        yyerror("(sem) tipos incomp. para op lógica: "+ A.getTipoStr() + " && "+B.getTipoStr());
      }
    }

    //operacoes relacionais

    if (operador == LE || operador == LEQ || operador == EQ || operador == GEQ || operador == GR || operador == NEQ) {
      if (A == Tp_INT && B == Tp_INT) {
        return Tp_BOOL;
      } else {
        yyerror("(sem) tipos incomp. para op relacional: "+ A.getTipoStr() + " > "+B.getTipoStr());
      }
    }

    //erro

    return Tp_ERRO;

  }











