- The scope of a field declared in the CSX class comprises all fields and methods that follow it; forward references to fields not yet declared are not allowed.
- The scope of a method comprises its own body and all methods that follow it. Recursive calls are allowed, but calls to methods not yet declared are not allowed.
- A formal parameter of a method is considered local to the body of the method.
- int, bool, float, and char values, char arrays and string literals may be printed.
- Only int and float values may be read.
- The types of an actual parameter and its corresponding formal parameter must be identical.
- Arrays may only be passed as reference parameters.
 return statements with an expression may only appear in functions. The expression returned by a return statement must have the same type as the function within which it appears.
- return statements without an expression may only appear in procedures (void result type).
- If necessary, an implicit return statement is assumed at the end of a method.
- Any expression (including variables, constants and literals) of type int, char or bool may be type-cast to an int, char, float or bool value. These are the only type casts allowed.
- An identifier that labels a while statement is considered to be a local declaration in the scope immediately containing the while statement. No other declaration of the identifier in the same scope is allowed.
- An identifier referenced in a break or continue statement must denote a label (on a while statement). Moreover, the break or continue statement must appear within the body of the while statement that is selected by the label.

- Only expressions of type int or char may be used to index arrays.
