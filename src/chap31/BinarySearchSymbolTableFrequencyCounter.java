package chap31;

import interfaces.SymbolTable;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;

public class BinarySearchSymbolTableFrequencyCounter {

    public static void main(String[] args) throws IOException {
        int minlen = 8;

        SymbolTable<String, Integer> st = new BinarySearchSymbolTable<>();
        In input = ResourceFinder.findResourceInputStream("leipzig1M.txt");
        while (!input.isEmpty()) {
            // calculate frequency of each word
            String word = input.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) {
                st.put(word, 1);
            } else {
                st.put(word, st.get(word) + 1);
            }
        }

        // find most frequent word
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }
        StdOut.println(max + " " + st.get(max));
    }

}
