//Author: Long Bui and Shane Hall  

import java_cup.runtime.*;

class CSXToken 
{
	int linenum;
	int colnum;
	
	CSXToken() { 
	}
	
	CSXToken(int line,int col) {
		linenum = line;
		colnum = col;
	}
	
	CSXToken(Position p) {
		linenum = p.linenum;
		colnum = p.colnum;
	}
}

class CSXIntLitToken extends CSXToken {
	int intValue;
	
	CSXIntLitToken(int val, Position p) {
	   super(p);
	   intValue=val; 
	}
}

class CSXFloatLitToken extends CSXToken {
	float floatValue;
	
	CSXFloatLitToken(float val, Position p) {
		super(p);
		floatValue = val;
	}
}

class CSXIdentifierToken extends CSXToken {
	String identifierText;
	
	CSXIdentifierToken(String str, Position p){
		super(p);
		identifierText = str;
	}
}

class CSXCharLitToken extends CSXToken {
	char charValue;
	
	CSXCharLitToken(char chr, Position p){
		super(p);
		charValue = chr;
	}
}

class CSXStringLitToken extends CSXToken {
	String stringText;
	
	CSXStringLitToken(String str, Position p){
		super(p);
		stringText = str;
	}
	
	//Needed new constructor to update line number after a rawstring 
    CSXStringLitToken(String str, Position p, int num){
		super(p);
		stringText = str;
        	for (int i = 0; i < num; i++){
    		p.incLine();
    	}
	}
}

class CSXErrorToken extends CSXToken {
	String errorMessage;
	
	CSXErrorToken(String val, Position p){
		super(p);
		errorMessage = val;
	}
    
	//Needed new constructor to update line number after insertion of a runnaway string 
    CSXErrorToken(String val, Position p, int num){
		super(p);
		errorMessage = val;
                
        for (int i = 0; i < num; i++){
        p.incLine();
        }
	}
}

// This class is used to track line and column numbers
// Feel free to change it and extend it
class Position {
	int  linenum; 			
	int  colnum; 			

	Position() {
  		linenum = 1; 	
  		colnum = 1; 
	}
	void setColumn(int c) {
		// yycolumn is 0 based so we add 1 to it
		colnum = c + 1;
	}
    	
	void incLine() {
    	++linenum;
    }
}


//This class is used by the scanner to return token information that is useful for the parser
//This class will be replaced in our parser project by the java_cup.runtime.Symbol class
/*class Symbol { 
	public int sym;
	public CSXToken value;
	public Symbol(int tokenType, CSXToken theToken) {
		sym = tokenType;
		value = theToken;	
	}
}*/

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
FLOAT = [fF][lL][oO][aA][tT]
RETURN = [rR][eE][tT][uU][rR][nN]
TRUE = [tT][rR][uU][eE]
VOID = [vV][oO][iI][dD]
PRINT = [pP][rR][iI][nN][tT]
WHILE = [wW][hH][iI][lL][eE]
ENDIF = [eE][nN][dD][iI][fF]

//---------------------------Other macros-------------------------------
DIGIT = [0-9]
DIGITS = [0-9]+
LETTER = [A-Za-z]
STRLIT = \"([\040!#-\[\]-~]|\\.)*\"
RAWSTR = @\"([\040!#-~]|\n|\t|\\.)*\"
FLOATLIT = \~?({DIGIT}*\.{DIGITS}|{DIGITS}\.)
CHARLIT = \'([\040-&\(-\[\]-~]|\\\'|\\n|\\t|\\\\)\'
BADCHAR = \'([\040-&(-\[\]-~]|\\.)*\'
IDENTIFIER = {LETTER}({LETTER}|{DIGIT}|_)*
BADIDENTIFIER = (_|{DIGITS}){IDENTIFIER}
SINGLECOMMENT = \/\/[^\n]*\n
MULTICOMMENT = ##(#?[^#])*##
RUNAWAYSTRING = \"([\040!#-\[\]-~]|\\.|\t)*\n
RUNAWAYCHAR = \'(([\040-&(-\[\]-~]|\\.|\t))*\n

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
//------------------------RESERVED WORDS--------------------------------
<YYINITIAL> {
	{IF} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_IF, new CSXToken(Pos));
    }
	{BOOL} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_BOOL, new CSXToken(Pos));
    }
	{BREAK} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_BREAK, new CSXToken(Pos));
    }
	{INT} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_INT, new CSXToken(Pos));
    }
	{CHAR} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_CHAR, new CSXToken(Pos));
    }
	{READ} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_READ, new CSXToken(Pos));
    }
	{CLASS} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_CLASS, new CSXToken(Pos));
    }
	{CONST} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_CONST, new CSXToken(Pos));
    }
	{CONTINUE} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_CONTINUE, new CSXToken(Pos));
    }
	{ELSE} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_ELSE, new CSXToken(Pos));
    }
	{FALSE} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_FALSE, new CSXToken(Pos));
    }
	{FLOAT} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_FLOAT, new CSXToken(Pos));
    }
	{RETURN} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_RETURN, new CSXToken(Pos));
    }
	{TRUE} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_TRUE, new CSXToken(Pos));
    }
	{VOID} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_VOID, new CSXToken(Pos));
    }
    {PRINT} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_PRINT, new CSXToken(Pos));
    }
    {WHILE} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.rw_WHILE, new CSXToken(Pos));
    }
    {ENDIF} {
        Pos.setColumn(yycolumn);
        return new Symbol(sym.ENDIF, new CSXToken(Pos));
    }
//------------------IDENTIFIERS AND LITERALS--------------------
    "~"?{DIGIT}+ {
        Pos.setColumn(yycolumn);
        int val = 0;
        boolean negative = false;
        String text = yytext();
		//Detect negative sign
        if (text.charAt(0) == '~') {
            text = "-"+text.substring(1, text.length());
            negative = true;
        }
        try {
            val = Integer.parseInt(text);
        //Catches overflow and underflow
		} catch (Exception e) {
            if (negative) {
				System.out.println(Pos.linenum + ":" + Pos.colnum + "\tERROR: Integer underflow: " + text);
                val = Integer.MIN_VALUE;
            } else {
				System.out.println(Pos.linenum + ":" + Pos.colnum + "\tERROR: Integer overflow: " + text);
                val = Integer.MAX_VALUE;
            }
        }
        return new Symbol(sym.INTLIT, new CSXIntLitToken(val, Pos));
    }
	{FLOATLIT} {
		Pos.setColumn(yycolumn);
        float val = 0;
        boolean negative = false;
        String text = yytext();
		//Tests for negative sign
        if (text.charAt(0) == '~') {
            text = "-"+text.substring(1, text.length());
            negative = true;
        }
        try {
            val = Float.parseFloat(text);
         //Catches overflow and underflow
		} catch (Exception e) {
            if (negative) {
				System.out.println(Pos.linenum + ":" + Pos.colnum + "\tERROR: Float underflow: " + text);
                val = Float.MIN_VALUE;
            } else {
				System.out.println(Pos.linenum + ":" + Pos.colnum + "\tERROR: Float overflow: " + text);
                val = Float.MAX_VALUE;
            }
        }
        return new Symbol(sym.FLOATLIT, new CSXFloatLitToken(val, Pos));
	}
	{STRLIT} {
		Pos.setColumn(yycolumn);
		String val = yytext();
		val = val.substring(1, val.length()-1); // Remove ""
		return new Symbol(sym.STRLIT, new CSXStringLitToken(val, Pos));
	}
	{RAWSTR} {
		String str = yytext();
		int numNewLines = str.split("\r\n|\r|\n").length -1;
		
  		Pos.setColumn(yycolumn);
		String val = yytext();
		//Replace escaped characters
		val = val.replace("\t", "\\t");
		val = val.replace("\n", "\\n");
		val = val.substring(2, val.length()-1);
		return new Symbol(sym.STRLIT, new CSXStringLitToken(val, Pos, numNewLines));
	}
	{CHARLIT} {
		Pos.setColumn(yycolumn);
		String str = yytext();
		//Detect special escaped characters
		if (str.charAt(1) == '\\') {
			char val;
			switch (str.charAt(2)) {
				case '\\':
					val = '\\';
					break;
				case 'n':
					val = '\n';
					break;
				case 't':
					val = '\t';
					break;
				case '\'':
					val = '\'';
					break;
				default:
					val = str.charAt(2);
			}
			//Returns correct escaped character
			return new Symbol(sym.CHARLIT, new CSXCharLitToken(val, Pos));
		} else {
			//Returns the a normal character
            return new Symbol(sym.CHARLIT, new CSXCharLitToken(str.charAt(1), Pos));
		}
  	}
	{BADCHAR} {
		Pos.setColumn(yycolumn);
		return new Symbol(sym.error, new CSXErrorToken("Invalid Character: " + yytext(), Pos));
	}
	{IDENTIFIER} {
        Pos.setColumn(yycolumn);
		return new Symbol(sym.IDENTIFIER, new CSXIdentifierToken(yytext(), Pos));
	}
	{BADIDENTIFIER} {
		Pos.setColumn(yycolumn);
		return new Symbol(sym.error, new CSXErrorToken("Invalid Identifier: " + yytext(), Pos));
	}
	{RUNAWAYSTRING} {
		String str = yytext();
		Pos.setColumn(yycolumn);
		return new Symbol(sym.error, new CSXErrorToken("Runaway String: " + str.substring(0, str.length()-1), Pos, 1));
	}
	{RUNAWAYCHAR} {
		String chr = yytext();
		Pos.setColumn(yycolumn);
		return new Symbol(sym.error, new CSXErrorToken("Runaway Character: " + chr.substring(0, chr.length()-1), Pos, 1));
   	}
	{SINGLECOMMENT} {
		Pos.incLine();
		Pos.setColumn(yycolumn);
        }
	{MULTICOMMENT} {
		String comment = yytext();
		//Count how many newlines are in the comment to update linenum correctly
		int numNewLines = comment.split("\r\n|\r|\n").length -1;
		for (int i = 0; i < numNewLines; ++i) {
			Pos.incLine();
		}
		Pos.setColumn(yycolumn);
  	}
//-------------------------OPERATORS----------------------------
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
	"/"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.SLASH, new CSXToken(Pos));
	}
	")"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.RPAREN, new CSXToken(Pos));
	}
	"("	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.LPAREN, new CSXToken(Pos));
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
		return new Symbol(sym.RBRACE, new CSXToken(Pos));
	}
	"{"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.LBRACE, new CSXToken(Pos));
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
	":"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.COLON, new CSXToken(Pos));
	}
	"["	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.LBRACKET, new CSXToken(Pos));
	}
	"]"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.RBRACKET, new CSXToken(Pos));
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
	"-"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.MINUS, new CSXToken(Pos));
	}
	"?"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.CONDEXPR, new CSXToken(Pos));
	}
    "-:"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.MINUSCOLON, new CSXToken(Pos));
	}
    "+:"	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.PLUSCOLON, new CSXToken(Pos));
	}
	

//------------------------WHITESPACE----------------------------
	\n|\r\n	{
	 	Pos.incLine();
		Pos.setColumn(1);
	}

	[\040\t] {
	 	Pos.setColumn(yycolumn);
	}

	.	{
		Pos.setColumn(yycolumn);
		return new Symbol(sym.error, new CSXErrorToken("Invalid Symbol: " + yytext(), Pos));
	}
}
