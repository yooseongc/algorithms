package chap12;

import utilities.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Data type for commercial transactions
 */
public class Transaction implements Comparable<Transaction> {

    private final String who;
    private final Date   when;
    private final double amount;

    public Transaction(String who, Date when, double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) throw new IllegalArgumentException("Amount cannot be NaN or infinite.");
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");  // split by [space] token
        who = a[0];
        when = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount)) throw new IllegalArgumentException("Amount cannot be NaN or infinite.");
    }

    public String who() { return who; }
    public Date when() { return when; }
    public double amount() { return amount; }

    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }

    /**
     * compares two transactions by amount
     */
    @Override
    public int compareTo(Transaction that) {
        return Double.compare(this.amount, that.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                who.equals(that.who) &&
                when.equals(that.when);
    }

    @Override
    public int hashCode() {
        return Objects.hash(who, when, amount);
    }

    /**
     * Compares two transactions by customer name.
     */
    public static class WhoOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction v, Transaction w) {
            return v.who.compareTo(w.who);
        }
    }

    /**
     * Compares two transactions by date.
     */
    public static class WhenOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction v, Transaction w) {
            return v.when.compareTo(w.when);
        }
    }

    /**
     * Compares two transactions by amount.
     */
    public static class HowMuchOrder implements Comparator<Transaction> {

        @Override
        public int compare(Transaction v, Transaction w) {
            return Double.compare(v.amount, w.amount);
        }
    }

    public static void main(String[] args) {
        // setup
        Transaction[] a = new Transaction[4];
        a[0] = new Transaction("Turing   6/17/1990  644.08");
        a[1] = new Transaction("Tarjan   3/26/2002 4121.85");
        a[2] = new Transaction("Knuth    6/14/1999  288.34");
        a[3] = new Transaction("Dijkstra 8/22/2007 2678.40");

        StdOut.println("Unsorted");
        for (Transaction transaction : a) StdOut.println(transaction);
        StdOut.println();

        StdOut.println("Sort by date");
        Arrays.sort(a, new Transaction.WhenOrder());
        for (Transaction transaction : a) StdOut.println(transaction);
        StdOut.println();

        StdOut.println("Sort by customer");
        Arrays.sort(a, new Transaction.WhoOrder());
        for (Transaction transaction : a) StdOut.println(transaction);
        StdOut.println();

        StdOut.println("Sort by amount");
        Arrays.sort(a, new Transaction.HowMuchOrder());
        for (Transaction transaction : a) StdOut.println(transaction);
        StdOut.println();
    }

}
