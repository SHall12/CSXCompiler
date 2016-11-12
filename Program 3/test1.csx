//Test1: expressions
class Test1 {
    void main(){
        // Expressions
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
        i = (a ? b -: c +: d);
    }
}
