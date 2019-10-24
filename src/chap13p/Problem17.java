package chap13p;

import chap12.Transaction;
import chap13.Queue;
import utilities.StdIn;

public class Problem17 {

    private static Transaction[] readAllTransactions() {
        Queue<Transaction> queue = new Queue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(new Transaction(StdIn.readString()));
        }
        int N = queue.size();
        Transaction[] transactions = new Transaction[N];
        for (int i = 0; i < N; i++) {
            transactions[i] = queue.dequeue();
        }
        return transactions;
    }

    public static void main(String[] args) {
        readAllTransactions();
    }

}
