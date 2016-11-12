Files:
	ast.java		    //Class for all the Nodes
	csx.cup		        //Java Parser Generator
	csx.jflex		    //Java Scanner Generator
	parser.java		    //Parser that is generated from csx.cup
	Scanner.java	    //Scanner that is generated from csx.jflex
	Sym.java		    //Symbol class generated from csx.cup
	Yylex.java		    //Token scanner for the Scanner
    P3.java		        //Driver for the CSXParser
    Java-cup-10l.jar    //Used to create parser.java
	JFlex.jar		    //Used to create Scanner.java


How to compile:
	java -jar ./tools/java-cup-10l.jar csx.cup
    java -jar ./tools/JFlex.jar csx.jflex
    javac -cp tools/java-cup-10l.jar:. sym.java
    javac -cp tools/java-cup-10l.jar:. Scanner.java
    javac -cp tools/java-cup-10l.jar:. ast.java
    javac -cp tools/java-cup-10l.jar:. Yylex.java
    javac -cp tools/java-cup-10l.jar:. parser.java
    javac -cp tools/java-cup-10l.jar:. P3.java


How to test:
	java -cp tools/java-cup-10l.jar:. P3 test1.csx
	java -cp tools/java-cup-10l.jar:. P3 test2.csx
	java -cp tools/java-cup-10l.jar:. P3 test3.csx
	java -cp tools/java-cup-10l.jar:. P3 test4.csx
    java -cp tools/java-cup-10l.jar:. P3 test5.csx
    java -cp tools/java-cup-10l.jar:. P3 test6.csx
    java -cp tools/java-cup-10l.jar:. P3 test7.csx
    java -cp tools/java-cup-10l.jar:. P3 test8.csx
