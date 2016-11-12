//Test4: Dangling else

class Test4 {
    void main(){
        if (1<2)
        return true;
        ENDIF

        if (2>1)
        return false;
        ENDIF

		// Throw in conditional here too
        while (7 ? 8 -: 6+5 +: ~5){
            while (2>=2){
                a++;
            }
        }

        if (2==2)
            if (a != b)
                if (a+b*c-!(d*e))
                    a = (a ? b -: c +: d);
                else
                    a = (7 ? 8 -: 6+5 +: ~5);
                ENDIF
            ENDIF
            else
                a = jk;
        ENDIF
    }
}
