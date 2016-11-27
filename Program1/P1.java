import java.io.*;
import java.util.Scanner;

class P1 {
    public static void main(String args[]){

    Scanner scan = new Scanner(System.in);
    SymbolTable table = new SymbolTable();
    Symb symb;

    int num;
    String input = "";
    String menu = "Enter any of the following commands:\n"+
	"----------------------------------------------------\n"+
        "open (a new scope)\n"+
        "close (innermost current scope)\n"+
        "insert (symbol,integer pair into symbol table)\n"+
        "lookup (lookup symbol in top scope)\n"+
        "global (global lookup of symbol in symbol table)\n"+
        "dump (contents of symbol table)\n"+
        "quit (quits test driver)\n"+
        "menu (displays menu\n";

    System.out.println(menu);

    while (input != "quit"){
	input = scan.next();

        switch (input.toLowerCase()){
            case "open":
                table.openScope();

                System.out.println("New scope opened!");
                break;

	    case "close":
                try {
                    table.closeScope();
                    System.out.println("Scope closed!");
                }
                catch ( EmptySTException e ) {
                    System.out.println("No scope found!");
                }
	        break;

	    case "insert":
                System.out.printf("Enter Symbol: ");
                input = scan.next();
                System.out.printf("Enter associated integer: ");
                num = Integer.parseInt(scan.next());
		TestSym symbl = new TestSym(input, num);

		try {
                    table.insert(symbl);
                    System.out.println(symbl + " entered into symbol table.");
                }
                catch ( EmptySTException e ) {
                    System.out.println("No scope found!");
		}
 		catch ( DuplicateException e ) {
                    System.out.println(input + " already entered into top scope.");
		}
		break;

	    case "lookup":
		System.out.printf("Enter a symbol for local lookup: ");
                input = scan.next();
		symb = table.localLookup(input);

		if (symb == null)
                    System.out.println(input + " not found in top scope.");
		else
                    System.out.println(symb + " found in top scope.");

		break;

            case "global":
                System.out.printf("Enter a symbol for global lookup: ");
                input = scan.next();
                symb = table.globalLookup(input);
		if (symb == null)
                    System.out.println(input + " not found in any scope.");
		else
                    System.out.println(symb + " found in symbol table");

                break;

            case "dump":
                System.out.println("Dumping contents............");
                table.dump(System.out);
                break;

            case "menu":
                System.out.println(menu);
                break;

            case "quit":
                input = "quit";
                break;

            default:
                System.out.println("Invalid Command!");
                break;
        }
    }

    System.out.println("Testing Done!");
    System.exit(0);

      // Complete this
    } // main
} // class P1
