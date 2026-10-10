package data_structures.class_problems;

/**
 * Problem 2: Warehouse Grid Summary
 *
 * Complexity:
 *   Time  : O(m * n) - every cell is visited exactly once (m rows, n columns).
 *   Space : O(1)     - only a running total and the best value/coordinates.
 * Scanning row by row, left to right, and updating only on a strictly greater value
 * keeps the first maximum when there are ties.
 */
public class P2_WarehouseGridSummary {

    /** Tuple (total_items, (row, col)). */
    static class Summary {
        final int totalItems;
        final int row;
        final int col;

        Summary(int totalItems, int row, int col) {
            this.totalItems = totalItems;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + totalItems + ", (" + row + ", " + col + "))";
        }
    }

    static Summary warehouseSummary(int[][] grid) {
        int total = 0;
        int maxValue = -1;
        int maxRow = -1;
        int maxCol = -1;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > maxValue) {
                    maxValue = grid[r][c];
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return new Summary(total, maxRow, maxCol);
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };
        System.out.println(warehouseSummary(grid)); // (49, (2, 1))

        int[][] ties = {{5, 5}, {5, 1}};
        System.out.println(warehouseSummary(ties)); // (16, (0, 0)) first maximum wins
    }
}
