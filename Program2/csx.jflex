/*  Expand this into your solution for project 2 */

class CSXToken 
{
	int linenum;
	int colnum;
	
	CSXToken() 
	{

	}
	
	CSXToken(int line,int col) 
	{
		linenum = line;
		colnum = col;
	}
	
	CSXToken(Position p) 
	{
		linenum = p.linenum;
		colnum = p.colnum;
		
	}

}

class CSXIntLitToken extends CSXToken {
	int intValue;
	CSXIntLitToken(int val, Position p) 
	{
	   super(p);
	   intValue=val; 
	}
}

class CSXFloatLitToken extends CSXToken {
	float floatValue;
	
	CSXFloatLitToken(float val, Position P){
		super(p);
		floatValue = val;
}

class CSXIdentifierToken extends CSXToken {
	String identifierText;
	
	CSXIdentifierToken(String str, Position P){
		super(p);
		identifierText = str;
}

class CSXCharLitToken extends CSXToken {
	char charValue;
	
	CSXCharLitToken(char chr, Position P){
		super(p);
		charValue = chr;
}

class CSXStringLitToken extends CSXToken {
	String stringText;
	
	CSXStringLitToken(String str, Position P){
		super(p);
		stringText = str;
}

// This class is used to track line and column numbers
// Feel free to change it and extend it
class Position {
	int  linenum; 			
	int  colnum; 			

	Position()
	{
  		linenum = 1; 	
  		colnum = 1; 
	}
	void setColumn(int c) 
	{ // yycolumn is 0 based so we add 1 to it
		colnum = c + 1;
	}
        void incLine()
        {
            ++linenum;
        }
} ;


//This class is used by the scanner to return token information that is useful for the parser
//This class will be replaced in our parser project by the java_cup.runtime.Symbol class
class Symbol { 
	public int sym;
	public CSXToken value;
	public Symbol(int tokenType, CSXToken theToken) {
		sym = tokenType;
		value = theToken;
	}
}

%%

//--------------------------Reserved words----------------------------
IF = [iI][fF]
BOOL = [bB][oO][oO][lL]
BREAK = [bB][rR][eE][aA][kK]
INT = [iI][nN][tT]
CHAR = [cC][hH][aA][rR]
READ = [rR][eE][aA][dD]
CLASS = [cC][lL][aA][sS][sS]
CONST = [cC][oO][nN][sS][tT]
CONTINUE = [cC][oO][nN][tT][iI][nN][uU][eE]
ELSE = [eE][lL][sS][eE]
FALSE = [fF][aA][lL][sS][eE]
FLOAT = [fL][lL][oO][aA][tT]
RETURN = [rR][eE][tT][uU][rR][nN]
TRUE = [tT][rR][uU][eE]
VOID = [vV][oO][iI][dD]
PRINT = [pP][rR][iI][nN][tT]
WHILE = [wW][hH][iI][lL][eE]

//---------------------------Other macros-------------------------------
DIGIT = [0-9]
DIGITS = [0-9]+
LETTER = [A-Za-z]
/////////for STRLIT, shouldn't it be: \"([ !#-\[\]-~]|\\n|\\t|\\|\\\")*\"    ???
STRLIT = \"([ !#-\[\]-~]|\\n|\\t|\\\\|\\\")*\"
RAWSTR = @"([ !#-\[\]-~]|\\\"|\\|\n|\t)*\"
INTLIT = ~?{DIGITS}
FLOATLIT = ~?({DIGIT}*\.{DIGITS}|{DIGITS}\.)
CHARLIT = \'([ -&(-\[-\]-~]|\\\'|\\n|\\t|\\\\)\'
IDENTIFIER = {LETTER}({LETTER}|{DIGIT}|_)*

%type Symbol
%column
%eofval{
  return new Symbol(sym.EOF, new CSXToken(0,0));
%eofval}
%{
    Position Pos = new Position();
%}

%%
/***********************************************************************
 Tokens for the CSX language are defined here using regular expressions
************************************************************************/
"+"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.PLUS, new CSXToken(Pos));
}
"!="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.NOTEQ, new CSXToken(Pos));
}
";"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.SEMI, new CSXToken(Pos));
}
"["	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.LBRACKET, new CSXToken(Pos));
}
"/"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.SLASH, new CSXToken(Pos));
}
"~"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.MINUS, new CSXToken(Pos));
}
")"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.RPAREN, new CSXToken(Pos));
}
"!"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.NOT, new CSXToken(Pos));
}
"<"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.LT, new CSXToken(Pos));
}
","	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.COMMA, new CSXToken(Pos));
}
"++"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.INC, new CSXToken(Pos));
}
">="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.GEQ, new CSXToken(Pos));
}
"}"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.RBBRACKET, new CSXToken(Pos));
}
"||"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.COR, new CSXToken(Pos));
}
"=="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.EQ, new CSXToken(Pos));
}
"="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.ASG, new CSXToken(Pos));
}
"*"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.TIMES, new CSXToken(Pos));
}
";"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.COLON, new CSXToken(Pos));
}
"["	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.LBRACE, new CSXToken(Pos));
}
"]"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.RBRACE, new CSXToken(Pos));
}
"&&"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.CAND, new CSXToken(Pos));
}
"<="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.LEQ, new CSXToken(Pos));
}
"--"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.DEC, new CSXToken(Pos));
}
">"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.GT, new CSXToken(Pos));
}

//////////////////////////////////////////////////////////////////
{DIGIT}+	{
	// This def doesn't check for overflow -- be sure to update it
	Pos.setColumn(yycolumn);
	return new Symbol(sym.INTLIT,
		new CSXIntLitToken(Integer.parseInt(yytext()),
			Pos));
}
//EOL to be fixed so that it accepts different formats

\n	{
	Pos.incLine();
	Pos.setColumn(1);
}
" "	{
	Pos.setColumn(yycolumn);
}
