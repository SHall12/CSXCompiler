//Test2: Statements and expressions
class Test {
    void main(){

            // Expressions
            {
                i = Expr || Term;
                i = Expr && Term;
                i = Factor < Factor;
                i = Factor > Factor;
                i = Factor <= Factor;
                i = Factor >= Factor;
                i = Factor == Factor;
                i = Factor != Factor;
                i = Factor + Pri;
                i = Factor - Pri;
                i = Pri * Unary;
                i = Pri / Unary;
                i = Unary;
                i = !Unary;
                i = ( bool ) Unary;
                i = id();
                i = id ( a, b );
                i = 745465;
                i = 'a';
                i = 7.4;
                i = "STRING";
                i = true;
                i = false;
                i = ( true );
                i = id;
                i = id [ Expr ];
            }

            // Statements
            {
                if ( cond ) i = stmt;
                endif

                if (cond) i = stmt;
                else i = other;
                endif

                while (a+b){
                    c = d;
                }

                test : while (a+b){
                    c = d;
                }

                // Statements
                food = burger + bun;
                food[1] = burger + bun;

                read(a);
                read(a,b);
                print(a);
                print(a,b);
                id();
                id(1);
                id(1,2,3);
                return;
                return a;
                break abc;
                continue abc;
            };

    }
}
