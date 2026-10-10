package data_structures.assignment_problems;

import java.util.Arrays;

/**
 * Problem 2: Merging Two Token Queues
 *
 * Two-pointer merge:
 *   Time  : O(m + n) - each token is copied exactly once.
 *   Space : O(m + n) for the output list (O(1) extra beyond the output).
 *
 * Join then sort:
 *   Time  : O((m + n) log(m + n)) - slower, because it ignores that both lists are already sorted.
 *   Space : O(m + n)
 */
public class A2_MergingTwoTokenQueues {

    static int[] mergeTokens(int[] counterA, int[] counterB) {
        int[] merged = new int[counterA.length + counterB.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < counterA.length && j < counterB.length) {
            if (counterA[i] <= counterB[j]) {
                merged[k++] = counterA[i++];
            } else {
                merged[k++] = counterB[j++];
            }
        }
        while (i < counterA.length) {
            merged[k++] = counterA[i++];
        }
        while (j < counterB.length) {
            merged[k++] = counterB[j++];
        }
        return merged;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeTokens(new int[]{3, 8, 15, 20}, new int[]{5, 8, 12})));
        // [3, 5, 8, 8, 12, 15, 20]
        System.out.println(Arrays.toString(mergeTokens(new int[]{}, new int[]{4, 9})));
        // [4, 9]
        System.out.println(Arrays.toString(mergeTokens(new int[]{1, 2}, new int[]{})));
        // [1, 2]
        System.out.println(Arrays.toString(mergeTokens(new int[]{}, new int[]{})));
        // []
    }
}
