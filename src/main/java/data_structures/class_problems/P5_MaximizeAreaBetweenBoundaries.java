package data_structures.class_problems;

/**
 * Problem 5: Maximize Area Between Two Boundaries
 *
 * Brute force (every pair of walls):
 *   Time  : O(n^2)
 *   Space : O(1)
 *
 * Two pointers (optimal):
 *   Time  : O(n) - each step moves one pointer inward, so at most n - 1 steps.
 *   Space : O(1)
 *
 * Why moving the shorter wall is safe: the area is limited by the shorter wall. Moving the
 * taller wall inward can only reduce the width without raising that limit, so it can never
 * give a better area. Moving the shorter wall is the only move that might improve it.
 * For very large inputs (say n = 10^5), O(n^2) is about 10^10 operations, while O(n) is 10^5.
 */
public class P5_MaximizeAreaBetweenBoundaries {

    static long maxContainerAreaBruteForce(int[] heights) {
        long best = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                best = Math.max(best, (long) Math.min(heights[i], heights[j]) * (j - i));
            }
        }
        return best;
    }

    static long maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        long best = 0;
        while (left < right) {
            long area = (long) Math.min(heights[left], heights[right]) * (right - left);
            best = Math.max(best, area);
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(heights));           // 49
        System.out.println(maxContainerAreaBruteForce(heights)); // 49
        System.out.println(maxContainerArea(new int[]{1, 1}));   // 1
        System.out.println(maxContainerArea(new int[]{5}));      // 0 (needs two walls)
    }
}
