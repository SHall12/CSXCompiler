public class simple {

    public static void main(String[] args) {
        double[] grades = {99.5, 33, 17, 85, -99};
        computeMax(grades);
        computeMin(grades);
    }

    public static void computeMax(double[] scores) {
        int i = 0;
        double max = scores[0];
        while (scores[i] != -99) {
            if (scores[i] > max) {
                max = scores[i];
            }
            i = i + 1;
        }
        System.out.println("Max grade: " + max);
    }

    public static void computeMin(double[] scores) {
        int i = 0;
        double min = scores[0];
        double currentScor = scores[0];
        while (currentScor != -99) {
            if (currentScor < min) {
                min = currentScor
            }
            i = i + 1;
            currentScor = scores[i];
        }
        System.out.println("Min grade: " + min);
    }

}
