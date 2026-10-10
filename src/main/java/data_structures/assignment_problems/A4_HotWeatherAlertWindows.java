package data_structures.assignment_problems;

/**
 * Problem 4: Hot Weather Alert Windows
 *
 * Recalculating every block's sum from scratch:
 *   Time  : O(n * k)
 *   Space : O(1)
 *
 * Sliding window (this solution):
 *   Time  : O(n) - the window sum is updated by adding the new reading and removing the old one.
 *   Space : O(1)
 *
 * The check "sum >= k * threshold" avoids decimal division. long is used so that
 * k * threshold (up to 10^5 * 10^4) can never overflow.
 */
public class A4_HotWeatherAlertWindows {

    static int countAlerts(int[] readings, int k, int threshold) {
        long target = (long) k * threshold;
        long windowSum = 0;
        int alerts = 0;
        for (int i = 0; i < readings.length; i++) {
            windowSum += readings[i];
            if (i >= k) {
                windowSum -= readings[i - k];
            }
            if (i >= k - 1 && windowSum >= target) {
                alerts++;
            }
        }
        return alerts;
    }

    public static void main(String[] args) {
        System.out.println(countAlerts(new int[]{2, 2, 2, 2, 5, 5, 5, 8}, 3, 4)); // 3
        System.out.println(countAlerts(new int[]{10}, 1, 10));                      // 1 (average equals threshold)
        System.out.println(countAlerts(new int[]{1, 2, 3}, 3, 5));                  // 0
    }
}
