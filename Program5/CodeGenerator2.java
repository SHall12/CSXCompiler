import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator2 {

    private static ArrayList<Double> testScores = new ArrayList<Double>();
    private static final String FILENAME = "simple.j";

    public static void main(String[] args) {
        BufferedWriter bw = null;
		FileWriter fw = null;
        Scanner sc = new Scanner(System.in);

        // Create arraylist to store test scores
        double score = 0;

        // While input != -99
        while (score != -99.0) {
            System.out.print("Score: ");
            score = sc.nextDouble();
            testScores.add(score);
        }

        // Generate jasmin code
        try {
			String content = generateJasminProg();
            fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
        }

        // execute jasmin code
        try {
            Process p = Runtime.getRuntime().exec("java -jar jasmin.jar simple.j");
            p.waitFor();
            Runtime.getRuntime().exec("java simple ");
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateJasminProg() {
        String prog = ".class simple\n.super java/lang/Object\n"
            + ".method public <init>()V\n.limit stack 1\n.limit locals 1\n"
            + "aload_0\ninvokespecial java/lang/Object/<init>()V\nreturn\n.end method\n\n"
            + ".method public static main([Ljava/lang/String;)V\n"
            + ".limit stack 5\n.limit locals 3\n"
            + generateArray()
            + generateNewStandardOutput()
            + "aload_0\ninvokestatic simple/computeMax([D)V\n"
            + "aload_0\ninvokestatic simple/computeMin([D)V\nreturn\n.end method\n\n"
            + generateFindingFunction(true)
            + generateFindingFunction(false);
        return prog;
    }


    private static String generateNewStandardOutput() {
        String newStandard = "new java/io/PrintStream\ndup\n"
                + "new java/io/FileOutputStream\ndup\nldc \"output.txt\"\n"
                + "invokespecial java/io/FileOutputStream.<init>(Ljava/lang/String;)V\n"
                + "invokespecial java/io/PrintStream.<init>(Ljava/io/OutputStream;)V\n"
                + "astore_2\naload_2\ninvokestatic  java/lang/System.setOut(Ljava/io/PrintStream;)V\n"
                + "goto callFunctions\nastore_2\naload_2\n"
                + "invokevirtual java/io/FileNotFoundException.printStackTrace()V\n"
                + "callFunctions:\n";
        return newStandard;
    }

    private static String generateArray() {
        String array = "";
        if (testScores.size() < 6) {
            array = "iconst_" + testScores.size() + "\n";
        } else {
            array = "bipush " + testScores.size() + "\n";
        }
        array += "newarray double\nastore_0\n";
        for(int i = 0; i < testScores.size(); ++i) {
            array += "aload_0\n";
            if ( i < 6 ) {
                array += "iconst_" + i + "\n";
            } else {
                array += "bipush " + i + "\n";
            }
            array += "ldc2_w " + testScores.get(i) + "\ndastore\n";
        }
        return array;
    }

    private static String generateFindingFunction(boolean isMax) {
        String method = "";
        if (isMax) {
            method += ".method public static computeMax([D)V\n";
        } else {
            method += ".method public static computeMin([D)V\n";
        }
        method += ".limit stack 4\n.limit locals 6\n"
                + "iconst_0\nistore_1\naload_0\niload_1\ndaload\ndstore_2\n"
                + "aload_0\niload_1\ndaload\ndstore 4\n"
                + "checkScore:\ndload 4\nldc2_w -99.\ndcmpg\n"
                + "ifeq printScore\ndload 4\ndload_2\ndcmpg\n";
        if (isMax) {
            method += "iflt incrementIndex\ndload 4\ndstore_2\n";
        } else {
            method += "ifgt incrementIndex\ndload 4\ndstore_2\n";
        }
        method += "incrementIndex:\niload_1\niconst_1\niadd\nistore_1\n"
                + "aload_0\niload_1\ndaload\ndstore 4\ngoto checkScore\n"
                + "printScore:\ngetstatic java/lang/System/out Ljava/io/PrintStream;\n";
        if (isMax) {
            method += "ldc \"Max: \"\n";
        } else {
            method += "ldc \"Min: \"\n";
        }
        method += "invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V\n"
                + "\ngetstatic java/lang/System/out Ljava/io/PrintStream;\n"
                + "dload_2\ninvokevirtual java/io/PrintStream/println(D)V\n"
                + "return\n.end method\n\n";

        return method;
    }
}
