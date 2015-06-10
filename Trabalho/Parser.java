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
  import java.util.*;
//#line 20 "Parser.java"




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
    3,    5,    0,    4,    7,    7,    9,    9,    9,   12,
   10,   11,   11,    1,    1,    1,    8,    8,   13,   13,
   14,   15,   16,   16,   17,   17,    6,   18,   18,   18,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   20,
   20,   22,   22,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,   21,   21,   21,
};
final static short yylen[] = {                            2,
    0,    0,    8,    2,    2,    0,    3,    2,    1,    0,
    4,    1,    3,    1,    1,    1,    2,    0,    4,    4,
    4,    6,    3,    0,    3,    1,    3,    3,    1,    2,
    3,    4,    6,    4,    1,    4,    4,    4,    0,    2,
    2,    2,    0,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    3,    1,    4,
    1,    1,    1,    1,    3,    0,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    9,
    5,    0,    0,    0,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   35,    0,    0,    3,    0,    0,   17,    0,    0,    7,
    0,    0,   30,    0,   62,   63,    0,   61,    0,    0,
    0,    0,    0,    0,    0,   27,    0,   10,    0,    0,
    0,    0,   13,   14,   15,   16,   11,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   28,    0,    0,    0,   21,   19,   20,    0,
   58,    0,   48,   49,   50,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,   34,   36,    0,   40,   41,
   37,    0,   38,   10,   23,    0,   60,    0,   42,   65,
   25,   22,   33,
};
final static short yydgoto[] = {                          1,
   67,   91,    2,    8,    6,   31,    9,   18,   11,   12,
   24,   13,   19,   20,   21,   59,   95,   32,   33,   89,
   92,  119,
};
final static short yysindex[] = {                         0,
    0, -238, -265,  -29,    0, -225, -215, -221, -242,    0,
    0,  -14, -234, -235,   10, -224, -223,    0, -242, -225,
 -225, -215,   23,   19,   24,   40,   40,   42,   44,  -27,
    0, -180,   27,    0,   47,   47,    0, -221, -221,    0,
 -234, -192,    0,   40,    0,    0,   48,    0,   40,   75,
   92, -199,   59,   40,   40,    0, -235,    0,   36,   39,
   41,   43,    0,    0,    0,    0,    0,    0,   40,   50,
 -209,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   40,   40,   40,   40, -209,   56,   57,  109,   62,  143,
  126,   63,    0,   46,   65, -192,    0,    0,    0,   66,
    0, -160,    0,    0,    0,  -20,    4,    4,    4,  143,
    4,    4,  -20,  -20,    0,    0,    0,   59,    0,    0,
    0,   40,    0,    0,    0,   51,    0, -209,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -226, -179,    0, -153,    0,
    0,    0,    0,  -54,    0,    0,    0,    0, -153, -226,
 -226, -244,   54,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -152,    0,   58,   55,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,    0,   74,    0,  -54,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   74,    0,
  -53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -53,    0,   78,   78,    0,  -49,
   80,    0,    0,   81,    0,    0,    0,    0,    0,    0,
    0,  -47,    0,    0,    0,  -17,   37,  169,  173,  217,
  179,  198,    7,   31,    0,    0,    0,    0,    0,    0,
    0,   74,    0,    0,    0,    0,    0,  -53,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   28,  425,    0,   38,    0,    1,    0,  104,  103,  -51,
   85,    0,    0,    0,    0,   91,    5,   71,  -60,   12,
  -61,   45,
};
final static int YYTABLESIZE=543;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         59,
   59,   59,   59,   59,   39,   39,   94,  100,   15,   31,
  102,   32,   55,    8,    8,   16,   17,   59,    3,    8,
   25,   84,    4,   46,  116,   46,   46,   46,   14,    5,
   26,    6,    6,   27,    7,   28,   29,    6,   61,   62,
   10,   46,   14,   10,   22,   84,   82,   44,   83,   44,
   44,   44,   30,   23,   14,   34,   26,   38,   39,   27,
  130,   28,   29,   35,   36,   44,   41,  133,   64,   65,
   66,   45,   94,   45,   45,   45,   42,   52,   30,   49,
   52,   52,   43,   53,   56,   57,   58,   69,   86,   45,
  101,   84,   82,   96,   83,   52,  117,   97,   49,   98,
  118,   99,  121,  123,  124,  125,  127,  128,   10,  132,
   18,   12,   29,   24,   66,   24,   84,   82,   43,   83,
   64,   26,   37,  126,   40,   63,   60,   93,  131,  129,
    0,    0,  120,   84,   82,    0,   83,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   84,   82,  118,   83,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,   82,  122,
   83,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   84,   82,    0,   83,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   39,   39,   51,   54,   39,   31,   54,   32,   31,   56,
    0,    0,   56,   59,    0,   59,   59,   51,   59,    0,
    0,   54,   59,   59,   59,   59,    0,   56,   55,   59,
   59,   55,   59,   59,   59,   54,    0,   46,   59,   46,
   46,    0,   46,   72,   73,   74,   55,   53,    0,   46,
   53,    0,    0,   46,   46,    0,   46,   46,   46,    0,
    0,   44,   46,   44,   44,   53,   44,   72,   73,   74,
   75,    0,    0,   44,    0,    0,    0,   44,   44,    0,
   44,   44,   44,    0,    0,   45,   44,   45,   45,    0,
   45,   52,    0,   52,   52,    0,   52,   45,    0,    0,
    0,   45,   45,    0,   45,   45,   45,   44,   45,   46,
   45,   52,    0,   72,   73,   74,   75,   47,   48,    0,
   76,   77,    0,   78,   79,   80,   44,   45,   46,   81,
    0,   71,    0,    0,    0,   87,   47,   48,   72,   73,
   74,   75,    0,    0,    0,   76,   77,    0,   78,   79,
   80,   85,    0,    0,   81,   72,   73,   74,   75,    0,
    0,    0,   76,   77,    0,   78,   79,   80,    0,    0,
    0,   81,   72,   73,   74,   75,    0,    0,    0,   76,
   77,    0,   78,   79,   80,    0,    0,    0,   81,   72,
   73,   74,   75,    0,    0,    0,   76,   77,    0,   78,
   79,   80,    0,    0,    0,   81,   72,   73,   74,   75,
    0,    0,    0,   76,   77,    0,   78,   79,   80,    0,
    0,    0,   81,   51,    0,   51,   51,   54,   51,   54,
   54,    0,   54,   56,    0,   56,   56,    0,   56,    0,
   50,   51,    0,   51,    0,    0,    0,   54,    0,    0,
    0,    0,   55,   56,   55,   55,    0,   55,   68,    0,
    0,    0,    0,   70,    0,    0,    0,   88,   90,    0,
    0,   53,   55,   53,   53,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  103,  104,  105,  106,
  107,  108,  109,  110,  111,  112,  113,  114,  115,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   88,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   59,   58,   69,    8,   59,
   71,   59,   40,  258,  259,  258,  259,   59,  257,  264,
  256,   42,  288,   41,   85,   43,   44,   45,  264,   59,
  266,  258,  259,  269,  260,  271,  272,  264,   38,   39,
  256,   59,  264,  288,   59,   42,   43,   41,   45,   43,
   44,   45,  288,  288,  264,   46,  266,   20,   21,  269,
  122,  271,  272,  288,  288,   59,   44,  128,  261,  262,
  263,   41,  124,   43,   44,   45,   58,   41,  288,   40,
   44,   40,   59,   40,  265,   59,   40,   40,  288,   59,
   41,   42,   43,   58,   45,   59,   41,   59,   40,   59,
   44,   59,   41,   41,   59,   41,   41,  268,  288,   59,
  264,   58,  265,   59,   41,   58,   42,   43,   41,   45,
   41,   41,   19,   96,   22,   41,   36,   57,  124,  118,
   -1,   -1,   88,   42,   43,   -1,   45,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   42,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,
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
   26,   27,   -1,  285,   -1,   -1,   -1,  285,   -1,   -1,
   -1,   -1,  265,  285,  267,  268,   -1,  270,   44,   -1,
   -1,   -1,   -1,   49,   -1,   -1,   -1,   53,   54,   -1,
   -1,  265,  285,  267,  268,   -1,  270,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   72,   73,   74,   75,
   76,   77,   78,   79,   80,   81,   82,   83,   84,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  118,
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
"$$3 :",
"declaration : $$3 idList ':' type",
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

//#line 159 "gramatica.y"

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











//#line 538 "Parser.java"
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
//#line 28 "gramatica.y"
{ currEscopo = ""; }
break;
case 2:
//#line 28 "gramatica.y"
{ currClass = ClasseID.VarGlobal; }
break;
case 9:
//#line 39 "gramatica.y"
{ System.out.println("Erro na declaração, linha: " + lexer.getLine() ); }
break;
case 10:
//#line 42 "gramatica.y"
{ varList = new ArrayList<String>(); }
break;
case 11:
//#line 42 "gramatica.y"
{ for (String s : varList) {
                                                                           TS_entry nodo = new TS_entry(s, (Type)val_peek(0).obj, currEscopo, currClass);
                                                                           /*ts.insert(nodo); */
                                                                          }
                                                                        }
break;
case 12:
//#line 48 "gramatica.y"
{ varList.add(val_peek(0).sval); }
break;
case 13:
//#line 49 "gramatica.y"
{ varList.add(val_peek(2).sval); }
break;
case 14:
//#line 52 "gramatica.y"
{ yyval.obj = Type.Int; }
break;
case 15:
//#line 53 "gramatica.y"
{ yyval.obj = Type.Bool; }
break;
case 16:
//#line 54 "gramatica.y"
{ yyval.obj = Type.Double; }
break;
case 30:
//#line 81 "gramatica.y"
{ System.out.println("Erro nos comandos, linha: " + lexer.getLine() ); }
break;
case 31:
//#line 84 "gramatica.y"
{ TS_entry nodo = ts.pesquisa(val_peek(2).sval);
                                                          if (nodo == null) {
                                                            yyerror("(sem) var <" + val_peek(2).sval + "> nao declarada");                
                                                          } else {
                                                              if (nodo.getTipo() != (Type)val_peek(0).obj) {
                                                                yyerror("atribuindo valor diferente: " + nodo.getTipo() + " e " + (Type)val_peek(0).obj);
                                                              }
                                                          }
                                                        }
break;
case 32:
//#line 94 "gramatica.y"
{ if ((Type)val_peek(2).obj != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)val_peek(2).obj);
                                                          }
                                                        }
break;
case 33:
//#line 99 "gramatica.y"
{ if ((Type)val_peek(4).obj != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)val_peek(4).obj);
                                                          }
                                                        }
break;
case 34:
//#line 104 "gramatica.y"
{ if ((Type)val_peek(2).obj != Type.Bool) {
                                                            yyerror("expressão deve ser lógica "+(Type)val_peek(2).obj);
                                                          }
                                                        }
break;
case 44:
//#line 124 "gramatica.y"
{ yyval.obj = validaTipo('+', (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 45:
//#line 125 "gramatica.y"
{ yyval.obj = validaTipo('-', (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 46:
//#line 126 "gramatica.y"
{ yyval.obj = validaTipo(OR, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 47:
//#line 127 "gramatica.y"
{ yyval.obj = validaTipo('*', (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 48:
//#line 128 "gramatica.y"
{ yyval.obj = validaTipo(DIV, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 49:
//#line 129 "gramatica.y"
{ yyval.obj = validaTipo(MOD, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 50:
//#line 130 "gramatica.y"
{ yyval.obj = validaTipo(AND, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 51:
//#line 131 "gramatica.y"
{ yyval.obj = validaTipo(LE, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 52:
//#line 132 "gramatica.y"
{ yyval.obj = validaTipo(LEQ, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 53:
//#line 133 "gramatica.y"
{ yyval.obj = validaTipo(EQ, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 54:
//#line 134 "gramatica.y"
{ yyval.obj = validaTipo(GEQ, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 55:
//#line 135 "gramatica.y"
{ yyval.obj = validaTipo(GR, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 56:
//#line 136 "gramatica.y"
{ yyval.obj = validaTipo(NEQ, (Type)val_peek(2).obj, (Type)val_peek(0).obj); }
break;
case 57:
//#line 137 "gramatica.y"
{ yyval.obj = validaTipo(NOT, (Type)val_peek(0).obj, null); }
break;
case 58:
//#line 138 "gramatica.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 59:
//#line 139 "gramatica.y"
{ TS_entry nodo = ts.pesquisa(val_peek(0).sval);
                                         if (nodo == null) {
                                            yyerror("(sem) var <" + val_peek(0).sval + "> nao declarada");                
                                         } else {
                                            yyval.obj = nodo.getTipo();
                                         }
                                       }
break;
case 60:
//#line 146 "gramatica.y"
{ yyval.obj = Type.Error; }
break;
case 61:
//#line 147 "gramatica.y"
{ yyval.obj = Type.Int;}
break;
case 62:
//#line 148 "gramatica.y"
{ yyval.obj = Type.Bool; }
break;
case 63:
//#line 149 "gramatica.y"
{ yyval.obj = Type.Bool; }
break;
//#line 854 "Parser.java"
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
