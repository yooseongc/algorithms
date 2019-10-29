package chap13p;

import chap13.Queue;
import utilities.StdOut;

import java.io.File;

/**
 * Problem 1.3.43
 */
public class ListingFiles {

    private Queue<String> fileQueue;

    public ListingFiles() {
        fileQueue = new Queue<>();
    }

    private void listFiles(File file, int depth) {
        if (!file.exists()) return;
        // add directory
        addFileToQueue(file, depth);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (File fileItem : fileList) {
                if (fileItem.isFile()) {
                    addFileToQueue(fileItem, depth);
                } else if (fileItem.isDirectory()) {
                    listFiles(fileItem, depth + 1);
                }
            }
        }
    }

    private void addFileToQueue(File file, int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuilder.append("  ");
        }
        stringBuilder.append((file.isDirectory() ? "[DIR] " : "") + file.getName());
        fileQueue.enqueue(stringBuilder.toString());
    }

    public static void main(String[] args) {
        String folderPath = "./src";
        File folder = new File(folderPath);
        ListingFiles listingFiles = new ListingFiles();
        listingFiles.listFiles(folder, 0);
        for (String fileName : listingFiles.fileQueue) {
            StdOut.println(fileName);
        }
    }

}
