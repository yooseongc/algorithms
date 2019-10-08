package chap12;

public class StringOperations {

    /**
     * check given string is palindrome (ex. abcba)
     */
    public static boolean isPalindrome(String s) {
        int N = s.length();
        for (int i = 0; i < N/2; i++) {
            if (s.charAt(i) != s.charAt(N-1-i)) return false;
        }
        return true;
    }

    /**
     * get base from filename string
     */
    public static String getBaseFromFilename(String filename) {
        int dot = filename.indexOf(".");
        return filename.substring(0, dot);
    }

    /**
     * get extension from filename string
     */
    public static String getExtFromFilename(String filename) {
        int dot = filename.indexOf(".");
        return filename.substring(dot + 1, filename.length());
    }

    public static String[] splitBySpace(String input) {
        return input.split("\\s+");
    }

    public static boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1].compareTo(a[i]) > 0) return false;
        }
        return true;
    }

}
