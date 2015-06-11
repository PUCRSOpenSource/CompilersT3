//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short PROGRAM=257;
public final static short FUNCTION=258;
public final static short PROCEDURE=259;
public final static short VAR=260;
public final static short INTEGER=261;
public final static short BOOLEAN=262;
public final static short REAL=263;
public final static short BEGIN=264;
public final static short END=265;
public final static short IF=266;
public final static short THEN=267;
public final static short ELSE=268;
public final static short WHILE=269;
public final static short DO=270;
public final static short READLN=271;
public final static short WRITELN=272;
public final static short ASSIGN=273;
public final static short DIV=274;
public final static short MOD=275;
public final static short AND=276;
public final static short OR=277;
public final static short NOT=278;
public final static short TRUE=279;
public final static short FALSE=280;
public final static short LEQ=281;
public final static short LE=282;
public final static short GRE=283;
public final static short GEQ=284;
public final static short EQ=285;
public final static short NEQ=286;
public final static short LITERAL=287;
public final static short ID=288;
public final static short NUM=289;
public final static short GR=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    3,    3,    5,    5,    5,    6,    7,    7,
    8,    8,    8,    4,    4,    9,    9,   10,   11,   12,
   12,   13,   13,    2,   14,   14,   14,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   17,   17,   19,   19,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   18,   18,   18,
};
final static short yylen[] = {                            2,
    6,    2,    2,    0,    3,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    0,    4,    4,    4,    6,    3,
    0,    3,    1,    3,    3,    1,    2,    3,    4,    6,
    4,    1,    4,    4,    4,    0,    2,    2,    2,    0,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    3,    1,    4,    1,    1,    1,
    1,    3,    0,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,    3,
    0,    0,    0,    0,    0,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   32,
    0,    0,    1,    0,    0,   14,    0,    0,   10,    5,
   11,   12,   13,    8,   27,    0,   59,   60,    0,   58,
    0,    0,    0,    0,    0,    0,    0,   24,    0,    0,
    0,    0,    0,    0,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
    0,    0,    0,   18,   16,   17,    0,   55,    0,   45,
   46,   47,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   44,   31,   33,    0,   37,   38,   34,    0,   35,
    0,   20,    0,   57,    0,   39,   62,   22,   19,   30,
};
final static short yydgoto[] = {                          2,
    6,   30,    7,   17,   10,   11,   12,   44,   18,   19,
   20,   61,   92,   31,   32,   88,   86,   89,  116,
};
final static short yysindex[] = {                      -238,
 -256,    0,  -25, -223, -245, -220, -242,    0,    1,    0,
   -6,   -4, -233,   14, -227, -221,    0, -242, -223, -223,
 -215, -245, -140,   18,   40,   40,   42,   43,  -27,    0,
 -180,   27,    0,   54,   54,    0, -220, -220,    0,    0,
    0,    0,    0,    0,    0,   40,    0,    0,   57,    0,
   40,   75,   92, -190,   59,   40,   40,    0, -233, -215,
   58,   41,   56,   60,    0,   40,   50, -201,   40,   40,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   40, -201,   83,   81,  109,   85,  143,  126,   86,    0,
   69,   88, -140,    0,    0,    0,   89,    0, -167,    0,
    0,    0,  -20,    4,    4,    4,  143,    4,    4,  -20,
  -20,    0,    0,    0,   59,    0,    0,    0,   40,    0,
 -215,    0,   72,    0, -201,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0, -244,    0,    0, -132,    0,   78,    0,
    0,    0,  -54,    0,    0,    0,    0, -132, -244, -244,
    0, -229,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -127,    0,   82,   74,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -41,    0,
    0,    0,    0,    0,    0,    0,   98,    0,  -54,    0,
    0,    0,    0,    0,    0,   98,    0,  -53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -53,    0,  100,  100,    0,  -49,  101,    0,    0,
  102,    0,    0,    0,    0,    0,    0,    0,  -47,    0,
    0,    0,  -17,   37,  169,  173,  217,  179,  198,    7,
   31,    0,    0,    0,    0,    0,    0,    0,   98,    0,
    0,    0,    0,    0,  -53,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   21,   19,    0,  127,  122,  -52,  125,   62,    0,    0,
    0,  112,   28,   91,  -61,   33,   44,  -57,   71,
};
final static int YYTABLESIZE=487;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         56,
   56,   56,   56,   56,   36,   36,   99,   91,   97,   28,
    8,   29,   57,    4,    4,   15,   16,   56,    1,    4,
  113,   81,   24,   43,   14,   43,   43,   43,    6,    6,
   13,    3,   25,    4,    6,   26,    5,   27,   28,   37,
   38,   43,    9,   13,   21,   81,   79,   41,   80,   41,
   41,   41,   22,   23,   29,   63,   64,   52,   53,   33,
   34,  127,   13,  130,   25,   41,   35,   26,   91,   27,
   28,   42,    9,   42,   42,   42,   45,   49,   65,   51,
   49,   54,   55,   67,   58,   59,   29,   85,   87,   42,
   98,   81,   79,   60,   80,   49,   66,   83,   51,   94,
  125,  100,  101,  102,  103,  104,  105,  106,  107,  108,
  109,  110,  111,  112,   95,   93,   81,   79,   96,   80,
   41,   42,   43,  114,  115,  118,  120,  121,  122,  124,
  129,   15,   21,   81,   79,    9,   80,   26,   63,   21,
   40,   61,   23,   40,   36,   39,   62,   85,  128,   90,
   81,   79,  115,   80,  123,  117,    0,    0,  126,    0,
    0,    0,    0,    0,    0,    0,    0,   81,   79,  119,
   80,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   81,   79,    0,   80,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
   36,   36,   48,   51,   36,   28,   51,   29,   28,   53,
    0,    0,   53,   56,    0,   56,   56,   48,   56,    0,
    0,   51,   56,   56,   56,   56,    0,   53,   52,   56,
   56,   52,   56,   56,   56,   56,    0,   43,   56,   43,
   43,    0,   43,   69,   70,   71,   52,   50,    0,   43,
   50,    0,    0,   43,   43,    0,   43,   43,   43,    0,
    0,   41,   43,   41,   41,   50,   41,   69,   70,   71,
   72,    0,    0,   41,    0,    0,    0,   41,   41,    0,
   41,   41,   41,    0,    0,   42,   41,   42,   42,    0,
   42,   49,    0,   49,   49,    0,   49,   42,    0,    0,
    0,   42,   42,    0,   42,   42,   42,   46,   47,   48,
   42,   49,    0,   69,   70,   71,   72,   49,   50,    0,
   73,   74,    0,   75,   76,   77,   46,   47,   48,   78,
    0,   68,    0,    0,    0,   84,   49,   50,   69,   70,
   71,   72,    0,    0,    0,   73,   74,    0,   75,   76,
   77,   82,    0,    0,   78,   69,   70,   71,   72,    0,
    0,    0,   73,   74,    0,   75,   76,   77,    0,    0,
    0,   78,   69,   70,   71,   72,    0,    0,    0,   73,
   74,    0,   75,   76,   77,    0,    0,    0,   78,   69,
   70,   71,   72,    0,    0,    0,   73,   74,    0,   75,
   76,   77,    0,    0,    0,   78,   69,   70,   71,   72,
    0,    0,    0,   73,   74,    0,   75,   76,   77,    0,
    0,    0,   78,   48,    0,   48,   48,   51,   48,   51,
   51,    0,   51,   53,    0,   53,   53,    0,   53,    0,
    0,    0,    0,   48,    0,    0,    0,   51,    0,    0,
    0,    0,   52,   53,   52,   52,    0,   52,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,   52,   50,   50,    0,   50,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   59,   68,   60,   66,   59,
  256,   59,   40,  258,  259,  258,  259,   59,  257,  264,
   82,   42,  256,   41,    6,   43,   44,   45,  258,  259,
  264,  288,  266,   59,  264,  269,  260,  271,  272,   19,
   20,   59,  288,  264,   44,   42,   43,   41,   45,   43,
   44,   45,   59,   58,  288,   37,   38,   25,   26,   46,
  288,  119,  264,  125,  266,   59,  288,  269,  121,  271,
  272,   41,  288,   43,   44,   45,   59,   41,   46,   40,
   44,   40,   40,   51,  265,   59,  288,   55,   56,   59,
   41,   42,   43,   40,   45,   59,   40,  288,   40,   59,
  268,   69,   70,   71,   72,   73,   74,   75,   76,   77,
   78,   79,   80,   81,   59,   58,   42,   43,   59,   45,
  261,  262,  263,   41,   44,   41,   41,   59,   41,   41,
   59,  264,   59,   42,   43,   58,   45,  265,   41,   58,
   41,   41,   41,   22,   18,   21,   35,  115,  121,   59,
   42,   43,   44,   45,   93,   85,   -1,   -1,  115,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   42,   43,   44,
   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,
  265,  265,   44,   41,  268,  265,   44,  265,  268,   41,
   -1,   -1,   44,  265,   -1,  267,  268,   59,  270,   -1,
   -1,   59,  274,  275,  276,  277,   -1,   59,   41,  281,
  282,   44,  284,  285,  286,  273,   -1,  265,  290,  267,
  268,   -1,  270,  274,  275,  276,   59,   41,   -1,  277,
   44,   -1,   -1,  281,  282,   -1,  284,  285,  286,   -1,
   -1,  265,  290,  267,  268,   59,  270,  274,  275,  276,
  277,   -1,   -1,  277,   -1,   -1,   -1,  281,  282,   -1,
  284,  285,  286,   -1,   -1,  265,  290,  267,  268,   -1,
  270,  265,   -1,  267,  268,   -1,  270,  277,   -1,   -1,
   -1,  281,  282,   -1,  284,  285,  286,  278,  279,  280,
  290,  285,   -1,  274,  275,  276,  277,  288,  289,   -1,
  281,  282,   -1,  284,  285,  286,  278,  279,  280,  290,
   -1,  267,   -1,   -1,   -1,  287,  288,  289,  274,  275,
  276,  277,   -1,   -1,   -1,  281,  282,   -1,  284,  285,
  286,  270,   -1,   -1,  290,  274,  275,  276,  277,   -1,
   -1,   -1,  281,  282,   -1,  284,  285,  286,   -1,   -1,
   -1,  290,  274,  275,  276,  277,   -1,   -1,   -1,  281,
  282,   -1,  284,  285,  286,   -1,   -1,   -1,  290,  274,
  275,  276,  277,   -1,   -1,   -1,  281,  282,   -1,  284,
  285,  286,   -1,   -1,   -1,  290,  274,  275,  276,  277,
   -1,   -1,   -1,  281,  282,   -1,  284,  285,  286,   -1,
   -1,   -1,  290,  265,   -1,  267,  268,  265,  270,  267,
  268,   -1,  270,  265,   -1,  267,  268,   -1,  270,   -1,
   -1,   -1,   -1,  285,   -1,   -1,   -1,  285,   -1,   -1,
   -1,   -1,  265,  285,  267,  268,   -1,  270,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  265,  285,  267,  268,   -1,  270,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=290;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'",null,null,null,null,null,null,null,null,null,null,null,"':'","';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"PROGRAM","FUNCTION","PROCEDURE","VAR","INTEGER",
"BOOLEAN","REAL","BEGIN","END","IF","THEN","ELSE","WHILE","DO","READLN",
"WRITELN","ASSIGN","DIV","MOD","AND","OR","NOT","TRUE","FALSE","LEQ","LE","GRE",
"GEQ","EQ","NEQ","LITERAL","ID","NUM","GR",
};
final static String yyrule[] = {
"$accept : program",
"program : PROGRAM ID ';' declarationOpc compoundStmt '.'",
"declarationOpc : varDeclarations procDeclarations",
"varDeclarations : VAR declarationList",
"varDeclarations :",
"declarationList : declaration ';' declarationList",
"declarationList : declaration ';'",
"declarationList : error",
"declaration : idList ':' type",
"idList : ID",
"idList : ID ',' idList",
"type : INTEGER",
"type : BOOLEAN",
"type : REAL",
"procDeclarations : procDec procDeclarations",
"procDeclarations :",
"procDec : procHeader declarationOpc compoundStmt ';'",
"procDec : funcHeader declarationOpc compoundStmt ';'",
"procHeader : PROCEDURE ID argumentList ';'",
"funcHeader : FUNCTION ID argumentList ':' type ';'",
"argumentList : '(' arguments ')'",
"argumentList :",
"arguments : declaration ';' arguments",
"arguments : declaration",
"compoundStmt : BEGIN statementList END",
"statementList : statement ';' statementList",
"statementList : statement",
"statementList : error ';'",
"statement : ID ASSIGN expression",
"statement : IF expression THEN statement",
"statement : IF expression THEN statement ELSE statement",
"statement : WHILE expression DO statement",
"statement : compoundStmt",
"statement : READLN '(' ID ')'",
"statement : WRITELN '(' printList ')'",
"statement : ID '(' expressionList ')'",
"statement :",
"printList : LITERAL printList2",
"printList : expression printList2",
"printList2 : ',' printList",
"printList2 :",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression OR expression",
"expression : expression '*' expression",
"expression : expression DIV expression",
"expression : expression MOD expression",
"expression : expression AND expression",
"expression : expression LE expression",
"expression : expression LEQ expression",
"expression : expression EQ expression",
"expression : expression GEQ expression",
"expression : expression GR expression",
"expression : expression NEQ expression",
"expression : NOT expression",
"expression : '(' expression ')'",
"expression : ID",
"expression : ID '(' expressionList ')'",
"expression : NUM",
"expression : TRUE",
"expression : FALSE",
"expressionList : expression",
"expressionList : expression ',' expressionList",
"expressionList :",
};

//#line 124 "gramatica.y"

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
//#line 449 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 7:
//#line 35 "gramatica.y"
{ System.out.println("Erro na declaração, linha: " + lexer.getLine() ); }
break;
case 9:
//#line 40 "gramatica.y"
{System.out.println(val_peek(0).sval);}
break;
case 27:
//#line 73 "gramatica.y"
{ System.out.println("Erro nos comandos, linha: " + lexer.getLine() ); }
break;
//#line 610 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
