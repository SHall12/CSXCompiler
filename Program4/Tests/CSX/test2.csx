class testDecls {
    bool z = true;
    
    void hey() {
        int w = 3;
        w = 3 + 1;    
    }
    void main() {
        int y = 7;
        int x = 6;
        x = y + x;
        y = hey(); 
        print(y);
    }
}
