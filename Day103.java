// 3934. Smallest Unique Subarray
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// You are given an integer array nums.

// Find the minimum length of a subarray that is not identical to any other subarray in nums.

// Return an integer denoting the minimum possible length of such a subarray.

// Two subarrays are considered identical if they have the same length and the same elements in corresponding positions.

 

// Example 1:

// Input: nums = [3,3,3]

// Output: 3

// Explanation:

// Subarrays of length 1: [3] → appears 3 times
// Subarrays of length 2: [3, 3] → appears 2 times
// Subarrays of length 3: [3, 3, 3] → appears once
// The subarray [3, 3, 3] is unique, so the smallest unique subarray length is 3.

// Example 2:

// Input: nums = [2,1,2,3,3]

// Output: 1

// Explanation:

// Subarrays of length 1:

// [2] → appears 2 times
// [1] → appears once
// [3] → appears 2 times
// The subarray [1] is unique, so the smallest unique subarray length is 1.
// Example 3:

// Input: nums = [1,1,2,2,1]

// Output: 2

// Explanation:

// Subarrays of length 1:

// [1] → appears 3 times
// [2] → appears 2 times
// Subarrays of length 2:

// [1, 1] → appears once
// [1, 2] → appears once
// [2, 2] → appears once
// [2, 1] → appears once
// There is at least one subarray of length 2 that is unique, so the smallest unique subarray length is 2.
 

// Constraints:

// 1 <= nums.length <= 105
// 1 <= nums[i] <= 105



import java.util.Arrays;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] vals = nums.clone();
        Arrays.sort(vals);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || vals[i] != vals[i - 1]) {
                vals[m++] = vals[i];
            }
        }
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i] = Arrays.binarySearch(vals, 0, m, nums[i]) + 1;
        }
        s[n] = 0;
        int len = n + 1;
        int[] sa = buildSuffixArray(s, m + 1);
        int[] rank = new int[len];
        for (int i = 0; i < len; i++) {
            rank[sa[i]] = i;
        }
        int[] lcp = new int[len];
        int k = 0;
        for (int i = 0; i < len; i++) {
            if (rank[i] == len - 1) {
                k = 0;
                continue;
            }
            int j = sa[rank[i] + 1];
            while (i + k < len && j + k < len && s[i + k] == s[j + k]) {
                k++;
            }
            lcp[rank[i]] = k;
            if (k > 0) {
                k--;
            }
        }
        int ans = n;
        for (int r = 1; r < len; r++) {
            int pos = sa[r];
            int prevLcp = (r > 1) ? lcp[r - 1] : 0;
            int nextLcp = (r < len - 1) ? lcp[r] : 0;
            int req = Math.max(prevLcp, nextLcp) + 1;
            if (pos + req <= n) {
                ans = Math.min(ans, req);
            }
        }
        return ans;
    }

    private int[] buildSuffixArray(int[] s, int alphabetSize) {
        int n = s.length;
        int[] sa = new int[n];
        int[] r = new int[n];
        int[] cnt = new int[Math.max(n, alphabetSize)];
        for (int i = 0; i < n; i++) {
            cnt[s[i]]++;
        }
        for (int i = 1; i < alphabetSize; i++) {
            cnt[i] += cnt[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            sa[--cnt[s[i]]] = i;
        }
        r[sa[0]] = 0;
        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (s[sa[i]] != s[sa[i - 1]]) {
                classes++;
            }
            r[sa[i]] = classes - 1;
        }
        int[] sa2 = new int[n];
        int[] r2 = new int[n];
        for (int h = 0; (1 << h) < n; h++) {
            int shift = 1 << h;
            for (int i = 0; i < n; i++) {
                sa2[i] = sa[i] - shift;
                if (sa2[i] < 0) {
                    sa2[i] += n;
                }
            }
            Arrays.fill(cnt, 0, classes, 0);
            for (int i = 0; i < n; i++) {
                cnt[r[sa2[i]]]++;
            }
            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }
            for (int i = n - 1; i >= 0; i--) {
                sa[--cnt[r[sa2[i]]]] = sa2[i];
            }
            r2[sa[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; i++) {
                int cur1 = r[sa[i]];
                int prev1 = r[sa[i - 1]];
                int cur2 = r[(sa[i] + shift) % n];
                int prev2 = r[(sa[i - 1] + shift) % n];
                if (cur1 != prev1 || cur2 != prev2) {
                    classes++;
                }
                r2[sa[i]] = classes - 1;
            }
            System.arraycopy(r2, 0, r, 0, n);
            if (classes == n) {
                break;
            }
        }
        return sa;
    }
}