package chap12;

import utilities.In;
import utilities.ResourceFinder;

import java.io.IOException;

/**
 * whitelist filter --> client for StaticSETofInts
 *
 */
public class Whitelist {

    public static void main(String[] args) throws IOException {
        In whitelist = ResourceFinder.findResourceInputStream("largeW.txt");

        int[] w = whitelist.readAllInts();
        StaticSETofInts set = new StaticSETofInts(w);

        // test 1 : use binary search
        In inputs = ResourceFinder.findResourceInputStream("largeT.txt");
        long start1 = System.currentTimeMillis();
        int count1 = 0;
        int inputCount = 0;
        while (!inputs.isEmpty() && ++inputCount < 100000) {
            int input = inputs.readInt();
            if (!set.contains(input)) count1++;
        }
        long end1 = System.currentTimeMillis();
        System.out.println(String.format("test1 : %.3f secs, %d unmatched.", (end1 - start1) / 1000.0, count1));
        inputs.close();

        // test 2 : use brute-force search
        inputs = ResourceFinder.findResourceInputStream("largeT.txt");
        long start2 = System.currentTimeMillis();
        int count2 = 0;
        inputCount = 0;
        while (!inputs.isEmpty() && ++inputCount < 100000) {
            int input = inputs.readInt();
            if (!set.containsBruteForce(input)) count2++;
        }
        long end2 = System.currentTimeMillis();
        System.out.println(String.format("test2 : %.3f secs, %d unmatched.", (end2 - start2) / 1000.0, count2));
        inputs.close();
    }

}
