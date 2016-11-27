import java.io.*;
import java.util.*;

class SymbolTable {
    private LinkedList<Hashtable<String, Symb>> symbolTable;
    private Hashtable<String, Symb> currentScope;

    SymbolTable() {
        symbolTable = new LinkedList<Hashtable<String, Symb>>();
        currentScope = null;
    }

    public void openScope() {
        currentScope = new Hashtable<String, Symb>();
        symbolTable.add(currentScope);
    }

    public void closeScope() throws EmptySTException {
        if (!symbolTable.isEmpty()) {
            symbolTable.removeLast(); // Remove top scope
            // Make current scope the new top scope (null if empty)
            currentScope = symbolTable.peekLast();
        } else {
            throw new EmptySTException();
        }
    }

    public void insert(Symb s) throws DuplicateException, EmptySTException {
        if (symbolTable.size() > 0) {
            // Check if symbol already in table
            if(currentScope.get(s.name().toLowerCase()) == null) {
                currentScope.put(s.name().toLowerCase(), s);
            } else {
                throw new DuplicateException();
            }
        } else {
            throw new EmptySTException();
        }
    }

    public Symb localLookup(String s) {
        if (!symbolTable.isEmpty()) {
            return currentScope.get(s.toLowerCase()); // Search current scope
        } else {
            return null;
        }
    }

    public Symb globalLookup(String s) {
        // Iterate through each scope from top scope to bottom.
        Symb symbol = null;
        Iterator<Hashtable<String, Symb>> scopes = symbolTable.descendingIterator();
        while(scopes.hasNext() && symbol == null) {
            currentScope = scopes.next();      // Set currentScope = next level
            symbol = localLookup(s.toLowerCase());   // Use currentScope to find
        }
        currentScope = symbolTable.peekLast(); // Return currentScope back to top scope
        return symbol;
    }

    public String toString() {
        // Iterate though each scope from top to bottom
        String tableString = "";
        Iterator<Hashtable<String, Symb>> scopes = symbolTable.descendingIterator();
        while(scopes.hasNext() && symbolTable != null) {
            Hashtable<String, Symb> scope = scopes.next();      // Get next scope
            Set<String> keys = scope.keySet();                  // Get set of symbols stored in scope
            tableString += "{";
            for(String key: keys){                              // Iterate through scope and get pairs
                tableString += (scope.get(key).toString() + " ");
            }
            tableString += "}\n";
        }
        return tableString;
    }

    void dump(PrintStream ps) {
        // Iterate through each scope from top to bottom
        Iterator<Hashtable<String, Symb>> scopes = symbolTable.descendingIterator();
        while(scopes.hasNext() && symbolTable != null) {
            Hashtable<String, Symb> scope = scopes.next();      // Get next scope
            Set<String> keys = scope.keySet();                  // Get set of symbols stored in scope
            ps.append("{");
            for(String key: keys){                              // Iterate through scope and get pairs
                ps.append(key + "=" + scope.get(key).toString() + " ");
            }
            ps.append("}\n");
        }
        ps.flush();
    }
} // class SymbolTable
