/***************************************************************************
 * CSX Project: Program 5: Code Generator
 * @Authors:  Long Bui and Shane Hall           12/08/2016
 * FileName:  CodeGenerator.java
 ***************************************************************************/


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
        PrintStream stdout = System.out;
        System.setOut(ps);
                
        System.out.println(".class public simple");
        System.out.println(".super java/lang/Object");
        System.out.println("");
        System.out.println(".method public <init>()V");
        System.out.println("\taload_0 ; push this");
        System.out.println("\tinvokespecial java/lang/Object/<init>()V ; call super");
        System.out.println("\treturn");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static computeMin([D)V");
        System.out.println("\t.limit locals 4  ; 0 = array, 1 = index, 2 = temp");
        System.out.println("\t.limit stack 4");
        System.out.println("\ticonst_0");
        System.out.println("\tistore_1            ;initialize an index variable to 0");
        System.out.println("\tldc2_w 100.0");
        System.out.println("\tdstore_2            ;initialize a temp variable to 100.0");
        System.out.println("LOOP:");
        System.out.println("\taload_0             ;load array reference");
        System.out.println("\tiload_1             ;load index");
        System.out.println("\tdaload              ;Load array[index] to stack");
        System.out.println("\tdload_2             ;load temp to stack");
        System.out.println("\tdcmpl               ;compare doubles");
        System.out.println("\tifgt Continue       ;if less than");
        System.out.println("\taload_0             ;load array reference");
        System.out.println("\tiload_1             ;load index");
        System.out.println("\tdaload              ;Load array[index] to stack");
        System.out.println("\tdstore_2");
        System.out.println("Continue:");
        System.out.println("\tiload_1             ;load index value");
        System.out.println("\ticonst_1");            
        System.out.println("\tiadd                ; increase index");           
        System.out.println("\tistore_1");
        System.out.println("\tiload_1             ; load length of array and index value");
        System.out.println("\taload_0");
        System.out.println("\tarraylength         ;Load length of array");
        System.out.println("\tif_icmpne LOOP      ; Compares index value vs length");
        System.out.println("");
        System.out.println("\t; Prints Result");
        System.out.println("\tnew java/lang/StringBuilder");
        System.out.println("\tdup");
        System.out.println("\tldc \"Minimum : \"");
        System.out.println("\tinvokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        System.out.println("\tinvokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V");
        System.out.println("\tdload_2");
        System.out.println("\tinvokevirtual java/lang/StringBuilder/append(D)Ljava/lang/StringBuilder;");
        System.out.println("\tinvokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
        System.out.println("\tastore_3");
        System.out.println("\tgetstatic java/lang/System/out Ljava/io/PrintStream;");
        System.out.println("\taload_3");
        System.out.println("\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        System.out.println("");
        System.out.println("\treturn");
        System.out.println("");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static computeMax([D)V");
        System.out.println("\t.limit locals 4  ; 0 = array, 1 = index, 2 = temp");
        System.out.println("\t.limit stack 4");
        System.out.println("\ticonst_0");
        System.out.println("\tistore_1            ;initialize an index variable to 0");
        System.out.println("\tldc2_w 0.0");
        System.out.println("\tdstore_2            ;initialize a temp variable to 0.0");
        System.out.println("");        
        System.out.println("LOOP:");
        System.out.println("\taload_0             ;load array reference");
        System.out.println("\tiload_1             ;load index");
        System.out.println("\tdaload              ;Load array[index] to stack");
        System.out.println("\tdload_2             ;load temp to stack");
        System.out.println("\tdcmpg               ;compare doubles");
        System.out.println("\tiflt Continue       ;if less than");
        System.out.println("\taload_0             ;load array reference");
        System.out.println("\tiload_1             ;load index");
        System.out.println("\tdaload              ;Load array[index] to stack");
        System.out.println("\tdstore_2");
        System.out.println("");
        System.out.println("Continue:");
        System.out.println("\t; increase index");
        System.out.println("\tiload_1             ;load index value");
        System.out.println("\ticonst_1");
        System.out.println("\tiadd");
        System.out.println("\tistore_1");
        System.out.println("");    
        System.out.println("\t; load length of array and index value");
        System.out.println("\tiload_1");
        System.out.println("\taload_0");
        System.out.println("\tarraylength         ;Load length of array");
        System.out.println("");        
        System.out.println("\tif_icmpne LOOP      ; Compares index value vs length");
        System.out.println("\t; Prints Result");
        System.out.println("\tnew java/lang/StringBuilder");
        System.out.println("\tdup");
        System.out.println("\tldc \"Maximum : \"");
        System.out.println("\tinvokestatic java/lang/String/valueOf(Ljava/lang/Object;)Ljava/lang/String;");
        System.out.println("\tinvokespecial java/lang/StringBuilder/<init>(Ljava/lang/String;)V");
        System.out.println("\tdload_2");
        System.out.println("\tinvokevirtual java/lang/StringBuilder/append(D)Ljava/lang/StringBuilder;");
        System.out.println("\tinvokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
        System.out.println("\tastore_3");
        System.out.println("\tgetstatic java/lang/System/out Ljava/io/PrintStream;");
        System.out.println("\taload_3");
        System.out.println("\tinvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        System.out.println("\treturn");
        System.out.println(".end method");
        System.out.println("");
        System.out.println(".method public static main([Ljava/lang/String;)V");
        System.out.println("\t.limit locals 1");
        System.out.println("\t.limit stack 4");
        System.out.println("\tldc " + index + "               ; array size");
        System.out.println("\tnewarray double     ; create new array");
        System.out.println("\tastore_0");
        System.out.println("\t;starts storing arrays");
        
        for (int i = 0; i < index; i++){
            System.out.println("\taload_0             ; push array reference onto stack");
            System.out.println("\tldc " + i + "               ; push index onto stack");
            System.out.println("\tldc2_w " + num[i] + "         ; push first value onto stack");
            System.out.println("\tdastore             ; store value in array at index");
        }
        
        System.out.println("\t; finished storing arrays");
        System.out.println("");
        System.out.println("\t;Set System.out to a file!");
	System.out.println("\tnew java/io/PrintStream");
	System.out.println("\tdup");
	System.out.println("\tldc \"output.txt\"");
	System.out.println("\tinvokespecial java/io/PrintStream/<init>(Ljava/lang/String;)V");
	System.out.println("\tinvokestatic java/lang/System.setOut(Ljava/io/PrintStream;)V");
        System.out.println("\taload_0");
        System.out.println("\tinvokestatic simple/computeMin([D)V      ; calls computeMin");
        System.out.println("\taload_0");
        System.out.println("\tinvokestatic simple/computeMax([D)V       ; calls computeMax");        
        System.out.println("\treturn");
        System.out.println(".end method");     
        
        System.setOut(stdout);      //Resets output back to System.out
        // execute jasmin code
        try {
            Runtime.getRuntime().exec("java -jar jasmin.jar simple.j");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
