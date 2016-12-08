import java.io.*;
import java.util.Scanner;
import java.io.PrintStream;

public class CodeGenerator{
    public static void main(String[] args) throws Exception {
        double[] num = new double[100]; 
        double input = 0;
        int index = 0;
        
        Scanner scan = new Scanner(System.in);
        
        //Get user input
        while(input != -99){
            System.out.println("Enter a test score. Enter -99 to end");
            input = scan.nextInt();
            if (input != -99){
                num[index] = input;
                index++;
            }
        }
        
        //Create a printstream file for output.
        File file = new File("simple.j");
        PrintStream ps = new PrintStream(file);
        System.setOut(ps);
        
        
        System.out.println(".class public simple");
        System.out.println(".super java/lang/Object");
        System.out.println("");
        System.out.println(".method public <init>()V");
        System.out.println("aload_0 ; push this");
        System.out.println("invokespecial java/lang/Object/<init>()V ; call super");
        System.out.println("return");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static computeMin([D)V");
        System.out.println(".limit locals 4  ; 0 = array, 1 = index, 2 = temp");
        System.out.println(".limit stack 4");
        System.out.println("iconst_0");
        System.out.println("istore_1            ;initialize an index variable to 0");
        System.out.println("ldc2_w 100.0");
        System.out.println("dstore_2            ;initialize a temp variable to 100.0");
        System.out.println("LOOP:");
        System.out.println("aload_0             ;load array reference");
        System.out.println("iload_1             ;load index");
        System.out.println("daload              ;Load array[index] to stack");
        System.out.println("dload_2             ;load temp to stack");
        System.out.println("dcmpl               ;compare doubles");
        System.out.println("ifgt Continue       ;if less than");
        System.out.println("aload_0             ;load array reference");
        System.out.println("iload_1             ;load index");
        System.out.println("daload              ;Load array[index] to stack");
        System.out.println("dstore_2");
        System.out.println("Continue:");
        System.out.println("iload_1             ;load index value");
        System.out.println("iconst_1");            
        System.out.println("iadd                ; increase index");           
        System.out.println("istore_1");
        System.out.println("iload_1             ; load length of array and index value");
        System.out.println("aload_0");
        System.out.println("arraylength         ;Load length of array");
        System.out.println("if_icmpne LOOP      ; Compares index value vs length");
        System.out.println("");
        System.out.println("; Prints Result");
        System.out.println("new java/lang/StringBuilder");
        System.out.println("dup");
        System.out.println("ldc \"Minimum : \"");
        System.out.println("invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        System.out.println("invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V");
        System.out.println("dload_2");
        System.out.println("invokevirtual java/lang/StringBuilder/append(D)Ljava/lang/StringBuilder;");
        System.out.println("invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
        System.out.println("astore_3");
        System.out.println("getstatic java/lang/System/out Ljava/io/PrintStream;");
        System.out.println("aload_3");
        System.out.println("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        System.out.println("");
        System.out.println("return");
        System.out.println("");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static computeMax([D)V");
        System.out.println(".limit locals 4  ; 0 = array, 1 = index, 2 = temp");
        System.out.println(".limit stack 4");
        System.out.println("iconst_0");
        System.out.println("istore_1            ;initialize an index variable to 0");
        System.out.println("ldc2_w 0.0");
        System.out.println("dstore_2            ;initialize a temp variable to 0.0");
        System.out.println("");        
        System.out.println("LOOP:");
        System.out.println("aload_0             ;load array reference");
        System.out.println("iload_1             ;load index");
        System.out.println("daload              ;Load array[index] to stack");
        System.out.println("dload_2             ;load temp to stack");
        System.out.println("dcmpg               ;compare doubles");
        System.out.println("iflt Continue       ;if less than");
        System.out.println("aload_0             ;load array reference");
        System.out.println("iload_1             ;load index");
        System.out.println("daload              ;Load array[index] to stack");
        System.out.println("dstore_2");
        System.out.println("");
        System.out.println("Continue:");
        System.out.println("; increase index");
        System.out.println("iload_1             ;load index value");
        System.out.println("iconst_1");
        System.out.println("iadd");
        System.out.println("istore_1");
        System.out.println("");    
        System.out.println("; load length of array and index value");
        System.out.println("iload_1");
        System.out.println("aload_0");
        System.out.println("arraylength         ;Load length of array");
        System.out.println("");        
        System.out.println("if_icmpne LOOP      ; Compares index value vs length");
        System.out.println("; Prints Result");
        System.out.println("new java/lang/StringBuilder");
        System.out.println("dup");
        System.out.println("ldc \"Maximum : \"");
        System.out.println("invokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        System.out.println("invokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V");
        System.out.println("dload_2");
        System.out.println("invokevirtual java/lang/StringBuilder/append(D)Ljava/lang/StringBuilder;");
        System.out.println("invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
        System.out.println("astore_3");
        System.out.println("getstatic java/lang/System/out Ljava/io/PrintStream;");
        System.out.println("aload_3");
        System.out.println("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        System.out.println("return");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static main([Ljava/lang/String;)V");
        System.out.println(".limit locals 1");
        System.out.println(".limit stack 4");
        System.out.println("ldc " + index + "               ; array size");
        System.out.println("newarray double     ; create new array");
        System.out.println("astore_0");
        System.out.println(";starts storing arrays");
        
        for (int i = 0; i < index; i++){
            System.out.println("aload_0             ; push array reference onto stack");
            System.out.println("ldc " + i + "               ; push index onto stack");
            System.out.println("ldc2_w " + num[i] + "         ; push first value onto stack");
            System.out.println("dastore             ; store value in array at index");
        }
        
        System.out.println("; finished storing arrays");
        System.out.println("aload_0");
        System.out.println("");
        System.out.println("invokestatic simple/computeMin([D)V      ; calls computeMin");
        System.out.println("aload_0");
        System.out.println("invokestatic simple/computeMax([D)V       ; calls computeMax");        
        System.out.println("return");
        System.out.println(".end method");        
    }
}
