public class C2_PalindromeChecker {

    static boolean isPalindromeIterative(String text) {
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) return true;
        if (text.charAt(0) != text.charAt(text.length() - 1)) return false;
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    static boolean isPalindromeArrayReversal(String text) {
        char[] chars = text.toCharArray();
        int n = chars.length;
        char[] reversed = new char[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = chars[n - 1 - i];
        }
        return new String(reversed).equals(text);
    }

    static void report(String text) {
        String iter = isPalindromeIterative(text) ? "Palindrome" : "Not Palindrome";
        String rec = isPalindromeRecursive(text) ? "Palindrome" : "Not Palindrome";
        String arr = isPalindromeArrayReversal(text) ? "Palindrome" : "Not Palindrome";
        System.out.println("Iterative: " + iter + " | Recursive: " + rec + " | Array Reversal: " + arr);
    }

    public static void main(String[] args) {
        report("madam");
        report("hello");
    }
}
