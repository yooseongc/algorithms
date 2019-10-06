package chap11;

public class Fibonacci3 {

    private final long[] result;

    public Fibonacci3(int N) {
        result = new long[N];
        for (int i = 0; i < N; i++) {
            result[i] = -1;  // initialize
        }
        result[0] = 0;
        result[1] = 1;
    }

    public long F(int N) {
        if (result[N] >= 0) {
            return result[N];
        } else {
            result[N] =  F(N-1) + F(N-2);
            return F(N);
        }
    }

    public static void main(String[] args) {
        Fibonacci3 fibonacci3 = new Fibonacci3(90);
        for (int i = 0; i < 90; i++) {
            System.out.println("step " + i + " Result: " + fibonacci3.F(i));
        }
    }

}
