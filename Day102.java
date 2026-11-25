// 3411. Maximum Subarray With Equal Products
// Easy
// Topics
// premium lock icon
// Companies
// Hint
// You are given an array of positive integers nums.

// An array arr is called product equivalent if prod(arr) == lcm(arr) * gcd(arr), where:

// prod(arr) is the product of all elements of arr.
// gcd(arr) is the GCD of all elements of arr.
// lcm(arr) is the LCM of all elements of arr.
// Return the length of the longest product equivalent subarray of nums.

 

// Example 1:

// Input: nums = [1,2,1,2,1,1,1]

// Output: 5

// Explanation: 

// The longest product equivalent subarray is [1, 2, 1, 1, 1], where prod([1, 2, 1, 1, 1]) = 2, gcd([1, 2, 1, 1, 1]) = 1, and lcm([1, 2, 1, 1, 1]) = 2.

// Example 2:

// Input: nums = [2,3,4,5,6]

// Output: 3

// Explanation: 

// The longest product equivalent subarray is [3, 4, 5].

// Example 3:

// Input: nums = [1,2,3,1,4,5,1]

// Output: 5

 

// Constraints:

// 2 <= nums.length <= 100
// 1 <= nums[i] <= 10




class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 2;
        long maxLcm = 1;
        for (int num : nums) {
            maxLcm = lcm(maxLcm, num);
        }
        for (int i = 0; i < n; i++) {
            long prod = 1;
            long g = 0;
            long l = 1;
            for (int j = i; j < n; j++) {
                prod *= nums[j];
                if (prod > maxLcm * nums[j]) {
                    break;
                }
                g = gcd(g, nums[j]);
                l = lcm(l, nums[j]);
                if (prod == l * g) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}