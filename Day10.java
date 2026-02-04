// 3640. Trionic Array II

// You are given an integer array nums of length n.

// A trionic subarray is a contiguous subarray nums[l...r] (with 0 <= l < r < n) for which there exist indices l < p < q < r such that:

// nums[l...p] is strictly increasing,
// nums[p...q] is strictly decreasing,
// nums[q...r] is strictly increasing.
// Return the maximum sum of any trionic subarray in nums.

 

// Example 1:

// Input: nums = [0,-2,-1,-3,0,2,-1]

// Output: -4

// Explanation:

// Pick l = 1, p = 2, q = 3, r = 5:

// nums[l...p] = nums[1...2] = [-2, -1] is strictly increasing (-2 < -1).
// nums[p...q] = nums[2...3] = [-1, -3] is strictly decreasing (-1 > -3)
// nums[q...r] = nums[3...5] = [-3, 0, 2] is strictly increasing (-3 < 0 < 2).
// Sum = (-2) + (-1) + (-3) + 0 + 2 = -4.
// Example 2:

// Input: nums = [1,4,2,7]

// Output: 14

// Explanation:

// Pick l = 0, p = 1, q = 2, r = 3:

// nums[l...p] = nums[0...1] = [1, 4] is strictly increasing (1 < 4).
// nums[p...q] = nums[1...2] = [4, 2] is strictly decreasing (4 > 2).
// nums[q...r] = nums[2...3] = [2, 7] is strictly increasing (2 < 7).
// Sum = 1 + 4 + 2 + 7 = 14.
 

// Constraints:

// 4 <= n = nums.length <= 105
// -109 <= nums[i] <= 109
// It is guaranteed that at least one trionic subarray exists.


class Solution {
    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        long maxResult = Long.MIN_VALUE;
        
        for (int start = 0; start < n; start++) {
            int idx = start + 1;
            
            while (idx < n && nums[idx - 1] < nums[idx]) {
                idx++;
            }
            int peakEnd = idx - 1;
            
            if (peakEnd == start) {
                continue;
            }
            
            long currentSum = nums[peakEnd] + nums[peakEnd - 1];
            
            while (idx < n && nums[idx - 1] > nums[idx]) {
                currentSum += nums[idx];
                idx++;
            }
            int valleyEnd = idx - 1;
            
            if (valleyEnd == peakEnd || valleyEnd == n - 1 || 
                (idx < n && nums[idx] <= nums[valleyEnd])) {
                start = valleyEnd;
                continue;
            }
            
            currentSum += nums[valleyEnd + 1];
            
            long bestThirdSegment = 0;
            long thirdSum = 0;
            for (int k = valleyEnd + 2; k < n && nums[k] > nums[k - 1]; k++) {
                thirdSum += nums[k];
                bestThirdSegment = Math.max(bestThirdSegment, thirdSum);
            }
            currentSum += bestThirdSegment;
            
            long bestFirstSegment = 0;
            long firstSum = 0;
            for (int k = peakEnd - 2; k >= start; k--) {
                firstSum += nums[k];
                bestFirstSegment = Math.max(bestFirstSegment, firstSum);
            }
            currentSum += bestFirstSegment;
            
            maxResult = Math.max(maxResult, currentSum);
            start = valleyEnd - 1;
        }
        
        return maxResult;
    }
}