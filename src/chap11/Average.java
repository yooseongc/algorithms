package chap11;

import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;

public class Average {

    public static void main(String[] args) throws IOException {

        In in = ResourceFinder.findResourceInputStream("tinyW.txt");
        double sum = 0.0;
        int cnt = 0;
        while (!in.isEmpty()) {
            sum += in.readDouble();
            cnt++;
        }
        double avg = sum / cnt;
        StdOut.printf("Average is %.5f\n", avg);
    }

}
