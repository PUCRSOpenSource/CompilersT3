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
    3,    5,    0,    4,    7,    7,    9,    9,    9,   10,
   11,   11,    1,    1,    1,    8,    8,   12,   12,   13,
   14,   15,   15,   16,   16,    6,   17,   17,   17,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   19,   19,
   21,   21,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,   20,   20,   20,
};
final static short yylen[] = {                            2,
    0,    0,    8,    2,    2,    0,    3,    2,    1,    3,
    1,    3,    1,    1,    1,    2,    0,    4,    4,    4,
    6,    3,    0,    3,    1,    3,    3,    1,    2,    3,
    4,    6,    4,    1,    4,    4,    4,    0,    2,    2,
    2,    0,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    2,    3,    1,    4,    1,
    1,    1,    1,    3,    0,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    9,
    0,    5,    0,    0,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   34,    0,    0,    3,    0,    0,   16,    0,    0,
   12,    7,   13,   14,   15,   10,   29,    0,   61,   62,
    0,   60,    0,    0,    0,    0,    0,    0,    0,   26,
    0,    0,    0,    0,    0,    0,   56,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   27,    0,    0,    0,   20,   18,   19,    0,   57,
    0,   47,   48,   49,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,   33,   35,    0,   39,   40,   36,
    0,   37,    0,   22,    0,   59,    0,   41,   64,   24,
   21,   32,
};
final static short yydgoto[] = {                          1,
   46,   90,    2,    8,    6,   32,    9,   19,   12,   13,
   14,   20,   21,   22,   63,   94,   33,   34,   88,   91,
  118,
};
final static short yysindex[] = {                         0,
    0, -238, -267,  -36,    0, -235, -245, -228, -242,    0,
  -15,    0,   -6,  -14, -143,    9, -224, -223,    0, -242,
 -235, -235, -221, -245, -203,   10,   40,   40,   30,   33,
  -27,    0, -194,   20,    0,   43,   43,    0, -228, -228,
    0,    0,    0,    0,    0,    0,    0,   40,    0,    0,
   44,    0,   40,   75,   92, -199,   59,   40,   40,    0,
 -143, -221,   27,   35,   38,   39,    0,   40,   50, -234,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   40,   40,   40, -234,   73,   71,  109,   78,  143,  126,
   81,    0,   57,   83, -203,    0,    0,    0,   84,    0,
 -141,    0,    0,    0,  -20,    4,    4,    4,  143,    4,
    4,  -20,  -20,    0,    0,    0,   59,    0,    0,    0,
   40,    0, -221,    0,   72,    0, -234,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -244,    0,    0, -134,    0,
   74,    0,    0,    0,  -54,    0,    0,    0,    0, -134,
 -244, -244,    0, -225,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -132,    0,   80,   77,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,    0,   98,    0,
  -54,    0,    0,    0,    0,    0,    0,   98,    0,  -53,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -53,    0,   99,   99,    0,  -49,  100,
    0,    0,  101,    0,    0,    0,    0,    0,    0,    0,
  -47,    0,    0,    0,  -17,   37,  169,  173,  217,  179,
  198,    7,   31,    0,    0,    0,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,  -53,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   48,   29,    0,   41,    0,    1,    0,  124,  123,  -55,
  125,    0,    0,    0,  112,   32,   89,  -39,   42,  -60,
   69,
};
final static int YYTABLESIZE=487;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   58,   58,   58,   58,   38,   38,   93,   99,   16,   30,
   10,   31,   59,    6,    6,   17,   18,   58,    3,    6,
    4,   83,    5,   45,    7,   45,   45,   45,   23,   15,
  101,   27,    8,    8,   28,   15,   29,   30,    8,   65,
   66,   45,   11,   25,  115,   83,   81,   43,   82,   43,
   43,   43,   24,   31,   35,   54,   55,   43,   44,   45,
  129,   39,   40,   36,   37,   43,   11,   93,   47,   56,
   60,   44,   57,   44,   44,   44,   67,   51,   61,   53,
   51,   69,   62,   68,   95,   87,   89,  132,   85,   44,
  100,   83,   81,   96,   82,   51,   97,   98,   53,  102,
  103,  104,  105,  106,  107,  108,  109,  110,  111,  112,
  113,  114,   26,  116,  117,  123,   83,   81,  120,   82,
   15,  122,   27,  124,  126,   28,  127,   29,   30,   17,
  131,   11,   28,   83,   81,   23,   82,   23,   65,   42,
   63,   25,  125,   38,   31,   87,   42,   41,   64,   92,
   83,   81,  117,   82,  130,  119,    0,    0,  128,    0,
    0,    0,    0,    0,    0,    0,    0,   83,   81,  121,
   82,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   83,   81,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   50,
   38,   38,   50,   53,   38,   30,   53,   31,   30,   55,
    0,    0,   55,   58,    0,   58,   58,   50,   58,    0,
    0,   53,   58,   58,   58,   58,    0,   55,   54,   58,
   58,   54,   58,   58,   58,   58,    0,   45,   58,   45,
   45,    0,   45,   71,   72,   73,   54,   52,    0,   45,
   52,    0,    0,   45,   45,    0,   45,   45,   45,    0,
    0,   43,   45,   43,   43,   52,   43,   71,   72,   73,
   74,    0,    0,   43,    0,    0,    0,   43,   43,    0,
   43,   43,   43,    0,    0,   44,   43,   44,   44,    0,
   44,   51,    0,   51,   51,    0,   51,   44,    0,    0,
    0,   44,   44,    0,   44,   44,   44,   48,   49,   50,
   44,   51,    0,   71,   72,   73,   74,   51,   52,    0,
   75,   76,    0,   77,   78,   79,   48,   49,   50,   80,
    0,   70,    0,    0,    0,   86,   51,   52,   71,   72,
   73,   74,    0,    0,    0,   75,   76,    0,   77,   78,
   79,   84,    0,    0,   80,   71,   72,   73,   74,    0,
    0,    0,   75,   76,    0,   77,   78,   79,    0,    0,
    0,   80,   71,   72,   73,   74,    0,    0,    0,   75,
   76,    0,   77,   78,   79,    0,    0,    0,   80,   71,
   72,   73,   74,    0,    0,    0,   75,   76,    0,   77,
   78,   79,    0,    0,    0,   80,   71,   72,   73,   74,
    0,    0,    0,   75,   76,    0,   77,   78,   79,    0,
    0,    0,   80,   50,    0,   50,   50,   53,   50,   53,
   53,    0,   53,   55,    0,   55,   55,    0,   55,    0,
    0,    0,    0,   50,    0,    0,    0,   53,    0,    0,
    0,    0,   54,   55,   54,   54,    0,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,   54,   52,   52,    0,   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   59,   62,   68,    8,   59,
  256,   59,   40,  258,  259,  258,  259,   59,  257,  264,
  288,   42,   59,   41,  260,   43,   44,   45,   44,  264,
   70,  266,  258,  259,  269,  264,  271,  272,  264,   39,
   40,   59,  288,   58,   84,   42,   43,   41,   45,   43,
   44,   45,   59,  288,   46,   27,   28,  261,  262,  263,
  121,   21,   22,  288,  288,   59,  288,  123,   59,   40,
  265,   41,   40,   43,   44,   45,   48,   41,   59,   40,
   44,   53,   40,   40,   58,   57,   58,  127,  288,   59,
   41,   42,   43,   59,   45,   59,   59,   59,   40,   71,
   72,   73,   74,   75,   76,   77,   78,   79,   80,   81,
   82,   83,  256,   41,   44,   59,   42,   43,   41,   45,
  264,   41,  266,   41,   41,  269,  268,  271,  272,  264,
   59,   58,  265,   42,   43,   59,   45,   58,   41,   41,
   41,   41,   95,   20,  288,  117,   24,   23,   37,   61,
   42,   43,   44,   45,  123,   87,   -1,   -1,  117,   -1,
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
final static short YYFINAL=1;
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
"$$1 :",
"$$2 :",
"program : $$1 PROGRAM ID ';' $$2 declarationOpc compoundStmt '.'",
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

//#line 142 "gramatica.y"

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











//#line 533 "Parser.java"
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
case 1:
//#line 27 "gramatica.y"
{ currEscopo = ""; }
break;
case 2:
//#line 27 "gramatica.y"
{ currClass = ClasseID.VarGlobal; }
break;
case 9:
//#line 38 "gramatica.y"
{ System.out.println("Erro na declaração, linha: " + lexer.getLine() ); }
break;
case 13:
//#line 47 "gramatica.y"
{ yyval.obj = Tp_INT; }
break;
case 14:
//#line 48 "gramatica.y"
{ yyval.obj = Tp_BOOL; }
break;
case 15:
//#line 49 "gramatica.y"
{ yyval.obj = Tp_FLOAT; }
break;
case 29:
//#line 76 "gramatica.y"
{ System.out.println("Erro nos comandos, linha: " + lexer.getLine() ); }
break;
case 31:
//#line 80 "gramatica.y"
{ if (((TS_entry)val_peek(2).obj).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)val_peek(2).obj).getTipo());
                                                          }
                                                        }
break;
case 32:
//#line 84 "gramatica.y"
{ if (((TS_entry)val_peek(4).obj).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)val_peek(4).obj).getTipo());
                                                          }
                                                        }
break;
case 33:
//#line 88 "gramatica.y"
{ if (((TS_entry)val_peek(2).obj).getTipo() != Tp_BOOL.getTipo()) {
                                                            yyerror("expressão deve ser lógica "+((TS_entry)val_peek(2).obj).getTipo());
                                                          }
                                                        }
break;
case 43:
//#line 107 "gramatica.y"
{ yyval.obj = validaTipo('+', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 44:
//#line 108 "gramatica.y"
{ yyval.obj = validaTipo('-', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 45:
//#line 109 "gramatica.y"
{ yyval.obj = validaTipo(OR, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 46:
//#line 110 "gramatica.y"
{ yyval.obj = validaTipo('*', (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 47:
//#line 111 "gramatica.y"
{ yyval.obj = validaTipo(DIV, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 48:
//#line 112 "gramatica.y"
{ yyval.obj = validaTipo(MOD, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 49:
//#line 113 "gramatica.y"
{ yyval.obj = validaTipo(AND, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 50:
//#line 114 "gramatica.y"
{ yyval.obj = validaTipo(LE, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 51:
//#line 115 "gramatica.y"
{ yyval.obj = validaTipo(LEQ, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 52:
//#line 116 "gramatica.y"
{ yyval.obj = validaTipo(EQ, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 53:
//#line 117 "gramatica.y"
{ yyval.obj = validaTipo(GEQ, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 54:
//#line 118 "gramatica.y"
{ yyval.obj = validaTipo(GR, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 55:
//#line 119 "gramatica.y"
{ yyval.obj = validaTipo(NEQ, (TS_entry)val_peek(2).obj, (TS_entry)val_peek(0).obj); }
break;
case 56:
//#line 120 "gramatica.y"
{ yyval.obj = validaTipo(NOT, (TS_entry)val_peek(0).obj, null); }
break;
case 57:
//#line 121 "gramatica.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 58:
//#line 122 "gramatica.y"
{ TS_entry nodo = ts.pesquisa(val_peek(0).sval);
                                         if (nodo == null) {
                                            yyerror("(sem) var <" + val_peek(0).sval + "> nao declarada");                
                                         } else {
                                            yyval.obj = nodo.getTipo();
                                         }
                                       }
break;
case 59:
//#line 129 "gramatica.y"
{ yyval.obj = Tp_ERRO; }
break;
case 60:
//#line 130 "gramatica.y"
{ yyval.obj = Tp_INT; }
break;
case 61:
//#line 131 "gramatica.y"
{ yyval.obj = Tp_BOOL; }
break;
case 62:
//#line 132 "gramatica.y"
{ yyval.obj = Tp_BOOL; }
break;
//#line 817 "Parser.java"
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
