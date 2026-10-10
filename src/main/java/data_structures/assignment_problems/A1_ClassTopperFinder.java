package data_structures.assignment_problems;

/**
 * Problem 1: Class Topper Finder
 *
 * Complexity:
 *   Time  : O(m * n) - every mark is added once (m students, n subjects).
 *   Space : O(1)     - only the best row and best total are stored.
 * Updating the best only on a strictly greater total keeps the smallest row index on ties.
 */
public class A1_ClassTopperFinder {

    /** Tuple (rowIndex, total). */
    static class Topper {
        final int rowIndex;
        final int total;

        Topper(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        @Override
        public String toString() {
            return "(" + rowIndex + ", " + total + ")";
        }
    }

    static Topper findTopper(int[][] marks) {
        int bestRow = 0;
        int bestTotal = -1;
        for (int i = 0; i < marks.length; i++) {
            int total = 0;
            for (int j = 0; j < marks[i].length; j++) {
                total += marks[i][j];
            }
            if (total > bestTotal) {
                bestTotal = total;
                bestRow = i;
            }
        }
        return new Topper(bestRow, bestTotal);
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };
        System.out.println(findTopper(marks)); // (1, 259)

        int[][] tie = {{50, 50}, {60, 40}, {10, 10}};
        System.out.println(findTopper(tie));   // (0, 100) smallest row index wins
    }
}
