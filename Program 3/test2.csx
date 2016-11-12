//Test2: Statements
class Test {
    void main(){
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
            i++;
            ++i;
            i--;
            --i;
            return;
            return a;
            break abc;
            continue abc;
        };

    }
}
