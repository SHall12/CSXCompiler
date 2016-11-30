class Kinds{
 public static final int Var = 0;
 public static final int Value = 1;
 public static final int Array = 2;
 public static final int Scalar_Parameter = 3;
 public static final int Array_Parameter = 4;
 public static final int Method = 5;
 public static final int Label = 6;
 public static final int Other = 7;

 Kinds(int i){val = i;}
 Kinds(){val = Other;}

 public String toString() {
        switch(val){
          case 0: return "Var";
          case 1: return "Value";
          case 2: return "Array";
          case 3: return "Scalar_Parameter";
          case 4: return "Array_Parameter";
          case 5: return "Method";
          case 6: return "Label";
          case 7: return "Other";
          default: throw new RuntimeException();
        }
 }

 int val;
}
