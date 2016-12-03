- The scope of a field declared in the CSX class comprises all fields and methods that follow it; forward references to fields not yet declared are not allowed.
- Arrays may only be passed as reference parameters.
- If necessary, an implicit return statement is assumed at the end of a method.
- An identifier that labels a while statement is considered to be a local declaration in the scope immediately containing the while statement. No other declaration of the identifier in the same scope is allowed.
- An identifier referenced in a break or continue statement must denote a label (on a while statement). Moreover, the break or continue statement must appear within the body of the while statement that is selected by the label.

- Only expressions of type int or char may be used to index arrays.
