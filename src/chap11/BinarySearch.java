package chap11;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;
import java.util.Arrays;

public class BinarySearch {

    /**
     *
     * @param key element to find in the sorted array
     * @param a sorted array
     * @return index of key in the sorted array. if not exist, return -1
     */
    public static int rank(int key, int[] a) {

        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // end condition :
            //      lo always go up(mid+1) and hi always go down(mid-1) if a[mid] != key
            //      if lo = mid = high, and a[mid] != key,  for next, lo > hi ==> true
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) args = new String[] { "tinyW.txt" };
        In in = ResourceFinder.findResourceInputStream(args[0]);
        int[] whitelist = in.readAllInts();
        Arrays.sort(whitelist);

        // problem : tinyT entry exists in whitelist?
        In entry = ResourceFinder.findResourceInputStream("tinyT.txt");
        while (!entry.isEmpty()) {
            int key = entry.readInt();
            if (rank(key, whitelist) == -1) {
                StdOut.printf("%d don't exist on whitelist.\n", key);
            }
        }
    }

}
