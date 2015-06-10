%{
  import java.io.*;
  import java.util.*;
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

declaration : { varList = new ArrayList<String>(); } idList ':' type ;  { for (String s : varList) {
                                                                           TS_entry nodo = new TS_entry(s, (Type)$4, currEscopo, currClass);
                                                                           //ts.insert(nodo); 
                                                                          }
                                                                        } 

idList : ID { varList.add($1); }
       | ID ',' idList { varList.add($1); }
       ;

type : INTEGER { $$ = Type.Int; }
     | BOOLEAN { $$ = Type.Bool; }
     | REAL    { $$ = Type.Double; }
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

statement : ID ASSIGN expression                        { TS_entry nodo = ts.pesquisa($1);
                                                          if (nodo == null) {
                                                            yyerror("(sem) var <" + $1 + "> nao declarada");                
                                                          } else {
                                                              if (nodo.getTipo() != (Type)$3) {
                                                                yyerror("atribuindo valor diferente: " + nodo.getTipo() + " e " + (Type)$3);
                                                              }
                                                          }
                                                        } 

          | IF expression THEN statement                { if ((Type)$2 != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)$2);
                                                          }
                                                        }

          | IF expression THEN statement ELSE statement { if ((Type)$2 != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)$2);
                                                          }
                                                        }

          | WHILE expression DO statement               { if ((Type)$2 != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)$2);
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

expression : expression '+' expression { $$ = validaTipo('+', (Type)$1, (Type)$3); }
           | expression '-' expression { $$ = validaTipo('-', (Type)$1, (Type)$3); }
           | expression OR expression  { $$ = validaTipo(OR, (Type)$1, (Type)$3); }
           | expression '*' expression { $$ = validaTipo('*', (Type)$1, (Type)$3); }
           | expression DIV expression { $$ = validaTipo(DIV, (Type)$1, (Type)$3); }
           | expression MOD expression { $$ = validaTipo(MOD, (Type)$1, (Type)$3); }
           | expression AND expression { $$ = validaTipo(AND, (Type)$1, (Type)$3); }
           | expression LE expression  { $$ = validaTipo(LE, (Type)$1, (Type)$3); }
           | expression LEQ expression { $$ = validaTipo(LEQ, (Type)$1, (Type)$3); }
           | expression EQ expression  { $$ = validaTipo(EQ, (Type)$1, (Type)$3); }
           | expression GEQ expression { $$ = validaTipo(GEQ, (Type)$1, (Type)$3); }
           | expression GR expression  { $$ = validaTipo(GR, (Type)$1, (Type)$3); }
           | expression NEQ expression { $$ = validaTipo(NEQ, (Type)$1, (Type)$3); }
           | NOT expression            { $$ = validaTipo(NOT, (Type)$2, null); }
           | '(' expression ')'        { $$ = $2; }
           | ID                        { TS_entry nodo = ts.pesquisa($1);
                                         if (nodo == null) {
                                            yyerror("(sem) var <" + $1 + "> nao declarada");                
                                         } else {
                                            $$ = nodo.getTipo();
                                         }
                                       }   
           | ID '(' expressionList ')' { $$ = Type.Error; }
           | NUM                       { $$ = Type.Int;}
           | TRUE                      { $$ = Type.Bool; }
           | FALSE                     { $$ = Type.Bool; }  
           ;


expressionList : expression
               | expression ',' expressionList
               |
               ;

%%

  private Yylex lexer;

  private TabSimb ts;

  private String currEscopo;
  private ClasseID currClass;
  private ArrayList<String> varList = new ArrayList<String>();

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

  Type validaTipo (int operador, Type A, Type B) {

    //operacoes aritmeticas

    if (operador == '+' || operador == '-' || operador == '*') {
      if ( A == Type.Int && B == Type.Int) {
        return Type.Int;
      }
      else if ( (A == Type.Double && (B == Type.Int || B == Type.Double)) || (B == Type.Double && (A == Type.Int || A == Type.Double)) ) {
        return Type.Double;
      }
      else {
        yyerror("(sem) tipos incomp. para soma: " + A + " + " + B);
      }          
    }

    //operacoes logicas

    if (operador == OR || operador == AND || operador == NOT) {
      if (A == Type.Bool && B == Type.Bool) {
        return Type.Bool;
      } else {
        yyerror("(sem) tipos incomp. para op lógica: "+ A + " && "+B);
      }
    }

    //operacoes relacionais

    if (operador == LE || operador == LEQ || operador == EQ || operador == GEQ || operador == GR || operador == NEQ) {
      if (A == Type.Int && B == Type.Int) {
        return Type.Bool;
      } else {
        yyerror("(sem) tipos incomp. para op relacional: "+ A + " > "+B);
      }
    }

    //erro

    return Type.Error;

  }











