package data_structures.assignment_problems;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

/**
 * Problem 3: Most Popular Canteen Order
 *
 * Re-scanning the whole list to count each item:
 *   Time  : O(n^2)
 *   Space : O(1)
 *
 * Hash map (this solution):
 *   Time  : O(n) - one pass to count, one pass to pick the winner.
 *   Space : O(k) - k distinct items stored in the map (k <= n).
 *
 * The second pass walks the orders in their original order and only replaces the best on a
 * strictly greater count, so ties go to the item that appears first.
 */
public class A3_MostPopularCanteenOrder {

    /** Tuple (item, count). */
    static class Popular {
        final String item;
        final int count;

        Popular(String item, int count) {
            this.item = item;
            this.count = count;
        }

        @Override
        public String toString() {
            return "(\"" + item + "\", " + count + ")";
        }
    }

    static Popular mostPopular(List<String> orders) {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        for (String item : orders) {
            counts.merge(item, 1, Integer::sum);
        }
        String bestItem = null;
        int bestCount = 0;
        for (String item : orders) {
            int c = counts.get(item);
            if (c > bestCount) {
                bestCount = c;
                bestItem = item;
            }
        }
        return new Popular(bestItem, bestCount);
    }

    public static void main(String[] args) {
        System.out.println(mostPopular(Arrays.asList("dosa", "idli", "vada", "dosa", "idli", "dosa", "tea")));
        // ("dosa", 3)
        System.out.println(mostPopular(Arrays.asList("tea", "coffee", "coffee", "tea")));
        // ("tea", 2)
        System.out.println(mostPopular(Arrays.asList("vada")));
        // ("vada", 1)
    }
}
