public class C4_FirstNonRepeatingChar {

    static Character findFirstNonRepeatingChar(String text) {
        int[] freq = new int[256];
        for (char c : text.toCharArray()) {
            freq[c]++;
        }
        for (char c : text.toCharArray()) {
            if (freq[c] == 1) return c;
        }
        return null;
    }

    static void report(String text) {
        Character result = findFirstNonRepeatingChar(text);
        if (result != null) {
            System.out.println("First Non-Repeating Character: '" + result + "'");
        } else {
            System.out.println("No Non-Repeating Character Found");
        }
    }

    public static void main(String[] args) {
        report("swiss");
        report("aabbcc");
    }
}
