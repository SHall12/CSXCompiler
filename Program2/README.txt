CSX Compiler : Program 2 : by Shane Hall and Long Bui
                                              CMPSC 470                                               
                                              10/14/2016

Scanner Generator:
	Csx.flex - Macros and rules used to generate the csx scanner
	JFlex.jar - Used on csx.flex to generate the csx scanner: Yylex.java
	Yylex.java - CSX Scanner class that is generated from csx.flex

Classes:
	CSXToken - A class to hold all the different token for the scanner
	CSXIntLitToken - A class that extends CSXToken to hold Integer Literal tokens
	CSXFloatLitToken - A class that extends CSXToken to hold Float Literal tokens
	CSXIdentifierToken - A class that extends CSXToken to hold Identifier tokens
	CSXCharLitToken - A class that extends CSXToken to hold Character Literal tokens
	CSXStringLitToken - A class that extends CSXToken to hold String Literal tokens
	CSXErrorToken - A class that extends CSXToken to hold Error tokens
		Stores error message that changes based on matched regex
	Position - A Global class that is used to keep track of the current row and column .
	Symbol - A class that is used by the scanner to return token information
	P2 - Driver to test the CSX Scanner

Test Files: 
	ReservedWordList.txt - Tests the reserve words 
	IdentiferTest.txt - Tests the identifiers literals
	NumberTest.tx - Tests the integer and float literals
	SymbolTest.txt - Tests the symbols and operators
	StringTest.txt - Tests the string, rawstring, and running string literals
	CharacterTest.txt - Tests the character and runaway char literals
	CommentTest.txt - Tests the single line and multiline comments

How to Build:
	java -jar ./tools/JFlex.jar csx.jflex
	javac Yylex.java
	javac sym.java
	javac P2.java

How to Test:
	java P2 ./Test\\ Programs/*Test.txt
