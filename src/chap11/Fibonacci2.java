package chap11;

public class Fibonacci2 {

    public static long F(int N) {
        if (N < 0) throw new IllegalArgumentException();
        if (N == 0) return 0;
        if (N == 1) return 1;
        return F(N-1) + F(N-2);
    }

    public static void main(String[] args) {
        for (int N = 0; N < 90; N++) {
            System.out.printf("F(%d)=%d\n", N, F(N));
        }
    }

}
