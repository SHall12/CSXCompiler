//Tests: IF, WHILE, INC, DEC, and CondExpressions
class test {
    int main(){
        
        if (A ? B -: C +: D)
            i++;

            if (A)
                ++i;
            else
                --i;
            endif
        else
            i--;
        endif

        while (a+2 ? 2 -: 4 +: c){
            i = (a ? b -: c +: d); 
        }    
    }
}