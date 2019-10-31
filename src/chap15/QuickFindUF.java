package chap15;

import utilities.GraphPanel;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickFindUF {

    private int[] id;
    private int count;

    // Used for plotting amortized costs
    private int operation;
    private int currentCost;
    private int totalCost;
    private List<Integer> total;

    public QuickFindUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }

        operation = 1;
        currentCost = 0;
        totalCost = 0;
        total = new ArrayList<>();
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        boolean isConnected = find(p) == find(q);
        if (isConnected) updateCostAnalysis();
        return isConnected;
    }

    public int find(int p) {
        validate(p);
        currentCost++;
        return id[p];
    }

    public void union(int p, int q) {
        int pId = find(p);  // need to correctness
        int qId = find(q);  // to reduce the number of array accesses
        if (pId == qId) return;  // p and q are already in the same component
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pId) id[i] = qId;
            currentCost++;
        }
        count--;
        updateCostAnalysis();
    }

    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    private void updateCostAnalysis() {
        totalCost += currentCost;
        total.add(totalCost / operation);
        currentCost = 0;
        operation++;
    }

    public void draw() {
        SwingUtilities.invokeLater(() -> {
            GraphPanel graphPanel = new GraphPanel("QuickFind", "Amortized Cost Plot",
                    "Number of Connections", "Number of Array Access", total);
            graphPanel.createAndShowGui();
        });
    }

    public static void main(String[] args) throws IOException {
        In inputs = ResourceFinder.findResourceInputStream("mediumUF.txt");
        int n = inputs.readInt();
        QuickFindUF uf = new QuickFindUF(n);
        while (!inputs.isEmpty()) {
            int p = inputs.readInt();
            int q = inputs.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
        uf.draw();
    }

}
