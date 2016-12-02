class testDecls {
    bool z = true;

    void voidFunc() {
        int x;
        int x;                                  //This should error
        x = thisVarNotDeclaredYet;              //This should error
    }

    int intFunc() {          // NEED TO HAVE RETURNS
        int x;
        x = 1;
    }

    bool boolFunc(){         // NEED TO HAVE RETURNS OF CORRCT TYPE
        int x;
        x = 1;
        return x;
    }

    int funcWithParams(int a, int b, char c[]) {
        print(a);
        return (a + b);
    }

    void main() {
        int intVar = 1;
        char charVar = 1;
        float floatVar = 1.1;
        bool boolVar = true;

        char charArray[10];
        bool boolArray[10];
        int badArray[0];                       //This should error

        const constIntVar = 1;
        const constFloatVar = 1.1;
        const constBoolVar = true;
        intVar = constIntVar;
        intVar = constFloatVar;                 //This should error
        intVar = constBoolVar;                  //This should error
        constIntVar = 2;                        //This should error

        //Function Calls
        intVar = intFunc();
        intVar = voidFunc();                    //This should error
        intVar = funcWithParams(intVar, charVar, charArray);
        intVar = funcWithParams();              // This should error
        intVar = funcWithParams(intVar, floatVar, boolArray);   // This should error

        //Procedure Call
        intFunc();
        voidFunc();                             //This should error

        //Print and Read
        print(intVar);
        print(charVar);
        print(floatVar);
        print(boolVar);
        read(intVar);
        read(floatVar);
        read(charVar);                          //This should error
        read(boolVar);                          //This should error

        //Binary Operators
        intVar = intVar + intVar;
        intVar = charVar - intVar;
        intVar = floatVar * intVar;             //This should error
        intVar = boolVar / intVar;              //This should error

        //Logical Operators
        boolVar = boolVar || boolVar;
        boolVar = !boolVar;
        intVar = boolVar && boolVar;            //This should error
        boolVar = 3 && 2;                       //This should error
        boolVar = 'a' || 2;                     //This should error
        boolVar = !1;                           //This should error

        boolVar = 1 == 2;
        boolVar = boolVar != boolVar;
        boolVar = intVar < boolVar;             //This should error
        boolVar = boolVar > intVar;             //This should error

        whileID: while(true){
            int x;
            x = 2;
            while(intVar){                      //This should error
                int x;       //same variable declared in new scope should be okay.
                x = 2;
            }
            while(true){
                break whileID;
                continue whileID;
            }
            break whileID;
            continue whileID; 
        }   
        break whileID;                          //This should error
        continue whileID;                          //This should error
        
        if(true){
            if(intVar){                         //This should error
                intVar = 2;
            } ENDIF
        } ENDIF
        
        intVar = (intVar ? intVar -: intVar +: intVar);
        intVar = (intVar ? boolVar -: intVar +: intVar);    //This should error
        
        if (intVar? boolVar -: boolVar +: boolVar){
            intVar = 1;
        } ENDIF
        if (intVar? intVar -: intVar +: intVar){            //This should error
            intVar = 1;
        } ENDIF

    }
}
