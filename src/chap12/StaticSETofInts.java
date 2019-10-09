package chap12;

import java.util.Arrays;

/**
 *
 * The {@code StaticSETofInts} class represents a set of integers.
 * It supports searching for a given interger is in the set.
 * It accomplishes this by keeping the set of integers in a sorted array and using
 * binary search to find the given interger.
 * <br>
 * The <em>rank</em> and <em>contains</em> operations take logarithmic time in the worst case.
 * <br>
 * problem 1.1.38
 *   with 100000 samples,
 * using binary search       -- below 0.3 secs.
 * using brute force search  -- above 10  secs.
 *
 */
public class StaticSETofInts {

    private final int[] set;

    public StaticSETofInts(int[] w) {
        set = new int[w.length];
        System.arraycopy(w, 0, set, 0, w.length); // defensive copy
        Arrays.sort(set);

        // check for duplicates
        checkDuplicates();
    }

    private void checkDuplicates() {
        for (int i = 1; i < set.length; i++) {
            if (set[i] == set[i - 1]) throw new IllegalArgumentException("Argument arrays contains duplicate keys.");
        }
    }

    public boolean containsBruteForce(int input) {
        for (int s : set) {
            if (input == s) return true;
        }
        return false;
    }

    public boolean contains(int input) {
        return rank(input) != -1;
    }

    private int rank(int input) {
        int lo = 0;
        int hi = set.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (input < set[mid]) hi = mid - 1;
            else if (input > set[mid]) lo = mid + 1;
            else                       return mid;
        }
        return -1;
    }

}
