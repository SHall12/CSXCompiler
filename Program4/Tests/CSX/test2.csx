class testDecls {
    bool z = true;
    
    void voidFunc() {
        int x;
        x = 1;
    }

    int intFunc() {              // NEED TO HAVE RETURNS
        int x;
        x = 1;
    }

    bool boolFunc(){
        int x;
        x = 1;
    }

    void main() {
        int intVar = 1;
        char charVar = 1;
        float floatVar = 1.1;
        bool boolVar = true;
        
        
        intVar = intVar + intVar;
        intVar = charVar + intVar;
        intVar = floatVar + intVar;             //This should error
        intVar = boolVar + intVar;              //This should error

        intVar = intFunc();                     
        intVar = voidFunc();                    //This should error
        
        print(intVar);
        print(charVar);
        print(floatVar);
        print(boolVar);
        
        read(intVar);
        read(floatVar);
        read(charVar);
        read(boolVar);

        
        
        
    }

}
