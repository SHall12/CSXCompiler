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
	// Expand - should contain floatValue
}

class CSXIdentifierToken extends CSXToken {
	// Expand - should contain identifierText
	
}

class CSXCharLitToken extends CSXToken {
	// Expand - should contain charValue
}

class CSXStringLitToken extends CSXToken {
	// Expand - should contain stringText
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

DIGIT=[0-9]
STRLIT = \"([^\" \\ ]|\\n|\\t|\\\"|\\\\)*\"		// to be fixed

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
	return new Symbol(sym.PLUS,
		new CSXToken(Pos));
}
"!="	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.NOTEQ,
		new CSXToken(Pos));
}
";"	{
	Pos.setColumn(yycolumn);
	return new Symbol(sym.SEMI,
		new CSXToken(Pos));
}
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
