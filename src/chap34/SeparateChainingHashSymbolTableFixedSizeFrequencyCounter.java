package chap34;

import interfaces.SymbolTable;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import java.io.IOException;

public class SeparateChainingHashSymbolTableFixedSizeFrequencyCounter {

    public static void main(String[] args) throws IOException {
        int minlen = 8;

        SymbolTable<String, Integer> bst = new SeparateChainingHashSymbolTableFixedSize<>();
        In input = ResourceFinder.findResourceInputStream("leipzig1M.txt");
        while (!input.isEmpty()) {
            // calculate frequency of each word
            String word = input.readString();
            if (word.length() < minlen) continue;
            if (!bst.contains(word)) {
                bst.put(word, 1);
            } else {
                bst.put(word, bst.get(word) + 1);
            }
        }

        // find most frequent word
        String max = "";
        bst.put(max, 0);
        for (String word : bst.keys()) {
            if (bst.get(word) > bst.get(max)) {
                max = word;
            }
        }
        StdOut.println(max + " " + bst.get(max));
    }

}
