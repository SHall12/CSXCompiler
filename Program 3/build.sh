#!/bin/bash
### Instructions UNIX ###
#### Generate CUP ####

java -jar tools/java-cup-10l.jar csx.cup

#### Compile ####

echo Building scanner ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
java -jar ./tools/JFlex.jar csx.jflex 
echo Building sym ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. sym.java
echo Compiling scanner ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. Scanner.java
echo Building ast ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. ast.java
echo Building Yylex ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. Yylex.java
echo Building parser ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. parser.java
echo Building P3 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
javac -cp tools/java-cup-10l.jar:. P3.java

#### Test ####

#echo Testing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#java -cp tools/java-cup-10l.jar:. P3 test1.csx



#java -jar ./tools/java-cup-10l.jar csx.cup
#javac sym.java
#javac parser.java
#createScanner
#java -jar ./tools/JFlex.jar csx.jflex
#javac Yylex.java

#javac ast.java
#javac P3.java
