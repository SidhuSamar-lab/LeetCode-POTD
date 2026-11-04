// 3505. Minimum Operations to Make Elements Within K Subarrays Equal
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// You are given an integer array nums and two integers, x and k. You can perform the following operation any number of times (including zero):

// Increase or decrease any element of nums by 1.
// Return the minimum number of operations needed to have at least k non-overlapping subarrays of size exactly x in nums, where all elements within each subarray are equal.

 

// Example 1:

// Input: nums = [5,-2,1,3,7,3,6,4,-1], x = 3, k = 2

// Output: 8

// Explanation:

// Use 3 operations to add 3 to nums[1] and use 2 operations to subtract 2 from nums[3]. The resulting array is [5, 1, 1, 1, 7, 3, 6, 4, -1].
// Use 1 operation to add 1 to nums[5] and use 2 operations to subtract 2 from nums[6]. The resulting array is [5, 1, 1, 1, 7, 4, 4, 4, -1].
// Now, all elements within each subarray [1, 1, 1] (from indices 1 to 3) and [4, 4, 4] (from indices 5 to 7) are equal. Since 8 total operations were used, 8 is the output.
// Example 2:

// Input: nums = [9,-2,-2,-2,1,5], x = 2, k = 2

// Output: 3

// Explanation:

// Use 3 operations to subtract 3 from nums[4]. The resulting array is [9, -2, -2, -2, -2, 5].
// Now, all elements within each subarray [-2, -2] (from indices 1 to 2) and [-2, -2] (from indices 3 to 4) are equal. Since 3 operations were used, 3 is the output.
 

// Constraints:

// 2 <= nums.length <= 105
// -106 <= nums[i] <= 106
// 2 <= x <= nums.length
// 1 <= k <= 15
// 2 <= k * x <= nums.length

import java.util.Arrays;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int[] vals = new int[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) {
                vals[m++] = sorted[i];
            }
        }

        int[] cnt = new int[m + 1];
        long[] sum = new long[m + 1];
        int log = Integer.highestOneBit(m);
        int r = (x + 1) / 2;
        long[] cost = new long[n - x + 1];
        long total = 0;

        for (int i = 0; i < n; i++) {
            int idx = Arrays.binarySearch(vals, 0, m, nums[i]) + 1;
            for (int p = idx; p <= m; p += p & -p) {
                cnt[p]++;
                sum[p] += nums[i];
            }
            total += nums[i];

            if (i >= x) {
                int old = nums[i - x];
                int oidx = Arrays.binarySearch(vals, 0, m, old) + 1;
                for (int p = oidx; p <= m; p += p & -p) {
                    cnt[p]--;
                    sum[p] -= old;
                }
                total -= old;
            }

            if (i >= x - 1) {
                int pos = 0;
                int c = 0;
                long s = 0;
                for (int step = log; step > 0; step >>= 1) {
                    int np = pos + step;
                    if (np <= m && c + cnt[np] < r) {
                        pos = np;
                        c += cnt[np];
                        s += sum[np];
                    }
                }
                long med = vals[pos];
                long sumLow = s + (long) (r - c) * med;
                cost[i - x + 1] = med * r - sumLow + (total - sumLow) - med * (x - r);
            }
        }

        long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1];
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            cur[0] = INF;
            for (int i = 1; i <= n; i++) {
                long best = cur[i - 1];
                if (i >= x && prev[i - x] < INF) {
                    best = Math.min(best, prev[i - x] + cost[i - x]);
                }
                cur[i] = best;
            }
            prev = cur;
        }
        return prev[n];
    }
}