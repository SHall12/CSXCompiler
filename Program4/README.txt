Files:
	ast.java		    //Class for all the Nodes
	csx.cup		        //Java Parser Generator
	csx.jflex		    //Java Scanner Generator
	parser.java		    //Parser that is generated from csx.cup
	Scanner.java	    //Scanner that is generated from csx.jflex
	Sym.java		    //Symbol class generated from csx.cup
	Yylex.java		    //Token scanner for the Scanner
    P4.java		        //Driver for the CSXParser
    Java-cup-10l.jar    //Used to create parser.java
	JFlex.jar		    //Used to create Scanner.java
    Kinds.java          //Stores different node kinds
    Types.java          //Stores different node types
    SymbolInfo.java     //Stores name, kind, and type
    FunctSymbol.java    //Extends SymbolInfo, contains list of parameters
    SymbolTable.java    //Used to keep track of symbol information


How to compile:
    ./build.sh
    or
	java -jar ./tools/java-cup-10l.jar csx.cup
    java -jar ./tools/JFlex.jar csx.jflex
    javac -cp tools/java-cup-10l.jar:. sym.java
    javac -cp tools/java-cup-10l.jar:. Scanner.java
    javac -cp tools/java-cup-10l.jar:. Yylex.java
    javac -cp tools/java-cup-10l.jar:. DuplicateException.java
    javac -cp tools/java-cup-10l.jar:. EmptySTException.java
    javac -cp tools/java-cup-10l.jar:. Symb.java
    javac -cp tools/java-cup-10l.jar:. Kinds.java
    javac -cp tools/java-cup-10l.jar:. Types.java
    javac -cp tools/java-cup-10l.jar:. SymbolInfo.java
    javac -cp tools/java-cup-10l.jar:. FunctSymbol.java
    javac -cp tools/java-cup-10l.jar:. SymbolTable.java
    javac -cp tools/java-cup-10l.jar:. ast.java
    javac -cp tools/java-cup-10l.jar:. parser.java
    javac -cp tools/java-cup-10l.jar:. P4.java


How to test:
	java -cp tools/java-cup-10l.jar:. P4 Tests/CSX/test2.csx
    java -cp tools/java-cup-10l.jar:. P4 Tests/CSX/insertionSort.csx
