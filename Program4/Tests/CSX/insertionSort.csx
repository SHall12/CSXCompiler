class insertionSort {
    int A[20];

    void main() {
        int i = 1;
        int length = 20;
        while (i < length) {
            int x = A[i];
            int j = i - 1;
            while ( J >= 0 && A[j] > x) {
                A[j+1] = A[j];
                j = j - 1;
            }
            A[j+1] = x;
        }
    }
}
