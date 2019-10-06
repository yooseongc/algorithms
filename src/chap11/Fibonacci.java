package chap11;

public class Fibonacci {

    private long prev1;
    private long prev2;
    private long curr;

    public Fibonacci(int N) {
        prev2 = 1;
        prev1 = 1;
        if (N < 0) throw new IllegalArgumentException();
        if (N == 0) {
            curr = prev2;
        } else if (N == 1) {
            curr = prev1;
        } else {
            for (int i = 1; i < N; i++) {
                next();
            }
        }
    }

    public long getResult() {
        return curr;
    }

    public void next() {
        curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 90; i++) {
            Fibonacci fibonacci = new Fibonacci(i);
            System.out.println("step " + i + " Result: " + fibonacci.getResult());
}
    }

}
