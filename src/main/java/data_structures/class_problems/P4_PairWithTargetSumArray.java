package data_structures.class_problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem 4: Pair With Target Sum (Unsorted Array)
 *
 * Approach 1 - brute force (every pair):
 *   Time  : O(n^2)
 *   Space : O(1)
 *
 * Approach 2 - hash set of seen values:
 *   Time  : O(n) average
 *   Space : O(n)
 *
 * Approach 3 - sort a copy, then two pointers from both ends:
 *   Time  : O(n log n) - dominated by the sort; the two-pointer sweep is O(n).
 *   Space : O(n) for the copy (O(1) extra if the input may be sorted in place).
 *
 * Choose the hash set when speed matters most, the sorted two-pointer version when memory
 * is tight, and brute force only for very small inputs.
 */
public class P4_PairWithTargetSumArray {

    static boolean bruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean withHashSet(int[] nums, int target) {
        Set<Long> seen = new HashSet<Long>();
        for (int x : nums) {
            if (seen.contains((long) target - x)) {
                return true;
            }
            seen.add((long) x);
        }
        return false;
    }

    static boolean withTwoPointers(int[] nums, int target) {
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int left = 0;
        int right = sorted.length - 1;
        while (left < right) {
            long sum = (long) sorted[left] + sorted[right];
            if (sum == target) {
                return true;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    /** Main entry point used for the problem: the optimal O(n) approach. */
    static boolean hasPairWithSum(int[] nums, int target) {
        return withHashSet(nums, target);
    }

    public static void main(String[] args) {
        int[][] inputs = {{2, 7, 11, 15}, {3, 4, 6}, {-3, 4, 3, 90}, {5}};
        int[] targets = {9, 20, 0, 10};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println(Arrays.toString(inputs[i]) + ", target " + targets[i] + " -> "
                    + bruteForce(inputs[i], targets[i]) + " / "
                    + withHashSet(inputs[i], targets[i]) + " / "
                    + withTwoPointers(inputs[i], targets[i]));
        }
        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9)); // true
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));     // false
    }
}
