#!/bin/bash
#### Build Scanner and Parser ####
echo "****Building parser with csx.cup****"
java -jar tools/java-cup-10l.jar csx.cup
echo "****Building scanner with csx.jflex****"
java -jar ./tools/JFlex.jar csx.jflex
for file in sym.java Scanner.java Yylex.java
do
    echo Compiling $file
    javac -cp tools/java-cup-10l.jar:. $file
done

#### Build SymbolTable ####
echo "****Building SymbolTable****"
for file in DuplicateException.java EmptySTException.java Symb.java
do
    echo Compiling $file
    javac -cp tools/java-cup-10l.jar:. $file
done

echo "****Building Project****"
for file in Kinds.java Types.java SymbolInfo.java FunctSymbol.java SymbolTable.java ast.java parser.java P4.java
do
    echo Compiling $file
    javac -cp tools/java-cup-10l.jar:. $file
done
