package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ResourceFinder {

    private final static String RESOURCE_DIR = "algs4-data";

    private ResourceFinder() {

    }

    public static In generateInputStream(String[] args) {
        Scanner scanner = new Scanner(String.join(" ", args));
        return new In(scanner);
    }

    public static File findResourceFile(String filename) {
        return new File(String.join(File.separator, RESOURCE_DIR, filename));
    }

    public static In findResourceInputStream(String filename) throws IOException {
        return new In(String.join(File.separator, RESOURCE_DIR, filename));
    }

    public static String readFileContents(File file) throws IOException {
        byte[] contents = Files.readAllBytes(Paths.get(file.toURI()));
        return new String(contents);
    }

    public static void main(String[] args) throws IOException {

        File tinyW = findResourceFile("tinyW.txt");
        assert tinyW.exists();
        String contents = readFileContents(tinyW);

        In inTinyW = findResourceInputStream("tinyW.txt");
        assert inTinyW.exists();
        StringBuilder sb = new StringBuilder();
        while (inTinyW.isEmpty()) {
            sb.append(inTinyW.readLine());
        }
        String contents2 = sb.toString();
        assert contents.equals(contents2);
    }

}
