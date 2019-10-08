package chap12;

import utilities.In;
import utilities.Out;
import utilities.ResourceFinder;

import java.io.IOException;

/**
 * Reads in text files specified as the first command-line arguments,
 * concatenates them, and writes the result to filename specified as
 * the last command-line arguments.
 */
public class Cat {

    private Cat() { }

    public static void main(String[] args) throws IOException {
        args = new String[] { "in1.txt", "in2.txt", "algs4-data/out.txt" };
        Out out = new Out(args[args.length - 1]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++) {
            In in = ResourceFinder.findResourceInputStream(args[i]);
            String s = in.readAll();
            sb.append(s).append("\n");
            out.println(s);
            in.close();
        }
        out.close();

        String actual = "This is\n" +
                "a tiny\n" +
                "test.\n";
        assert sb.toString().equals(actual);
    }

}
