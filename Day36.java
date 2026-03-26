// 3548. Equal Sum Grid Partition II

// You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

// Each of the two resulting sections formed by the cut is non-empty.
// The sum of elements in both sections is equal, or can be made equal by discounting at most one single cell in total (from either section).
// If a cell is discounted, the rest of the section must remain connected.
// Return true if such a partition exists; otherwise, return false.

// Note: A section is connected if every cell in it can be reached from any other cell by moving up, down, left, or right through other cells in the section.

 

// Example 1:

// Input: grid = [[1,4],[2,3]]

// Output: true

// Explanation:



// A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
// Example 2:

// Input: grid = [[1,2],[3,4]]

// Output: true

// Explanation:



// A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
// By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
// Example 3:

// Input: grid = [[1,2,4],[2,3,5]]

// Output: false

// Explanation:



// A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
// By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
// Example 4:

// Input: grid = [[4,1,8],[3,2,6]]

// Output: false

// Explanation:

// No valid cut exists, so the answer is false.

 

// Constraints:

// 1 <= m == grid.length <= 105
// 1 <= n == grid[i].length <= 105
// 2 <= m * n <= 105
// 1 <= grid[i][j] <= 105


import java.util.*;

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        long total = 0;
        for (int[] r : grid) {
            for (int v : r) total += v;
        }

        for (int t = 0; t < 4; t++) {
            m = grid.length;
            n = grid[0].length;

            if (m < 2) {
                grid = rotate(grid);
                continue;
            }

            long prefix = 0;
            Set<Long> set = new HashSet<>();

            if (n == 1) {
                for (int i = 0; i < m - 1; i++) {
                    prefix += grid[i][0];
                    long diff = prefix * 2 - total;
                    if (diff == 0) return true;
                    if (diff == grid[i][0] || diff == grid[0][0]) return true;
                }
                grid = rotate(grid);
                continue;
            }

            for (int i = 0; i < m - 1; i++) {
                for (int j = 0; j < n; j++) {
                    prefix += grid[i][j];
                    set.add((long) grid[i][j]);
                }

                long diff = prefix * 2 - total;

                if (diff == 0) return true;

                if (i == 0) {
                    if (diff == grid[0][0] || diff == grid[0][n - 1]) return true;
                } else {
                    if (set.contains(diff)) return true;
                }
            }

            grid = rotate(grid);
        }

        return false;
    }

    private int[][] rotate(int[][] g) {
        int r = g.length, c = g[0].length;
        int[][] res = new int[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[j][r - 1 - i] = g[i][j];
            }
        }
        return res;
    }
}