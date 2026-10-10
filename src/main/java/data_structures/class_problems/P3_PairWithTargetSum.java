package data_structures.class_problems;

import java.util.HashSet;
import java.util.Set;

/**
 * Problem 3: Pair With Target Sum (Unsorted)
 *
 * Approach 1 - brute force (check every pair):
 *   Time  : O(n^2)
 *   Space : O(1)
 *
 * Approach 2 - hash set of values seen so far (look up the complement):
 *   Time  : O(n)    - one pass, O(1) average lookup.
 *   Space : O(n)    - the set can hold up to n values.
 *
 * Trade-off: the hash set buys speed with extra memory. "Distinct elements" means two
 * different positions, so a value may pair with an equal duplicate (e.g. 3 + 3 = 6).
 */
public class P3_PairWithTargetSum {

    static boolean hasPairWithSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasPairWithSum(int[] nums, int target) {
        Set<Long> seen = new HashSet<Long>();
        for (int x : nums) {
            long complement = (long) target - x;
            if (seen.contains(complement)) {
                return true;
            }
            seen.add((long) x);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));  // true
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));      // false
        System.out.println(hasPairWithSum(new int[]{3, 3}, 6));          // true (two different elements)
        System.out.println(hasPairWithSum(new int[]{3}, 6));             // false (needs two elements)
        System.out.println(hasPairWithSum(new int[]{-4, 10, 4}, 0));     // true (negatives)

        System.out.println(hasPairWithSumBruteForce(new int[]{2, 7, 11, 15}, 9)); // true
        System.out.println(hasPairWithSumBruteForce(new int[]{3, 4, 6}, 20));     // false
    }
}
