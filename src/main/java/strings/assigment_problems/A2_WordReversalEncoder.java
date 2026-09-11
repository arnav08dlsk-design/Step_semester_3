public class A2_WordReversalEncoder {

    static String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();
        for (int w = 0; w < words.length; w++) {
            StringBuilder reversedWord = new StringBuilder();
            for (int i = words[w].length() - 1; i >= 0; i--) {
                reversedWord.append(words[w].charAt(i));
            }
            result.append(reversedWord);
            if (w < words.length - 1) result.append(" ");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseEachWord("hello club"));
    }
}
