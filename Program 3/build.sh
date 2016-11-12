#!/bin/bash
#### Generate CUP ####
echo Building parser with csx.cup
java -jar tools/java-cup-10l.jar csx.cup

#### Compile ####

echo Building scanner with csx.jflex
java -jar ./tools/JFlex.jar csx.jflex

for file in sym.java Scanner.java ast.java Yylex.java parser.java P3.java
do
    echo Compiling $file
    javac -cp tools/java-cup-10l.jar:. $file
done
