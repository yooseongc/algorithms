package chap15;

import utilities.GraphPanel;
import utilities.In;
import utilities.ResourceFinder;
import utilities.StdOut;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuickUnionUF {

    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components

    // Used for plotting amortized costs
    private int operation;
    private int currentCost;
    private int totalCost;
    private List<Integer> total;

    public QuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
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
        while (p != parent[p]) {
            p = parent[p];
            currentCost++;
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;  // p and q are already in the same component
        parent[rootP] = rootQ;
        count--;
        currentCost++;
        updateCostAnalysis();
    }

    private void validate(int p) {
        int n = parent.length;
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphPanel graphPanel = new GraphPanel("QuickUnion", "Amortized Cost Plot",
                        "Number of Connections", "Number of Array Access", total);
                graphPanel.createAndShowGui();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        In inputs = ResourceFinder.findResourceInputStream("mediumUF.txt");
        int n = inputs.readInt();
        QuickUnionUF uf = new QuickUnionUF(n);
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
