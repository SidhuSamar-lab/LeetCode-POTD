// 2911. Minimum Changes to Make K Semi-palindromes
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// Given a string s and an integer k, partition s into k substrings such that the letter changes needed to make each substring a semi-palindrome are minimized.

// Return the minimum number of letter changes required.

// A semi-palindrome is a special type of string that can be divided into palindromes based on a repeating pattern. To check if a string is a semi-palindrome:​

// Choose a positive divisor d of the string's length. d can range from 1 up to, but not including, the string's length. For a string of length 1, it does not have a valid divisor as per this definition, since the only divisor is its length, which is not allowed.
// For a given divisor d, divide the string into groups where each group contains characters from the string that follow a repeating pattern of length d. Specifically, the first group consists of characters at positions 1, 1 + d, 1 + 2d, and so on; the second group includes characters at positions 2, 2 + d, 2 + 2d, etc.
// The string is considered a semi-palindrome if each of these groups forms a palindrome.
// Consider the string "abcabc":

// The length of "abcabc" is 6. Valid divisors are 1, 2, and 3.
// For d = 1: The entire string "abcabc" forms one group. Not a palindrome.
// For d = 2:
// Group 1 (positions 1, 3, 5): "acb"
// Group 2 (positions 2, 4, 6): "bac"
// Neither group forms a palindrome.
// For d = 3:
// Group 1 (positions 1, 4): "aa"
// Group 2 (positions 2, 5): "bb"
// Group 3 (positions 3, 6): "cc"
// All groups form palindromes. Therefore, "abcabc" is a semi-palindrome.
 

// Example 1:

// Input: s = "abcac", k = 2

// Output: 1

// Explanation: Divide s into "ab" and "cac". "cac" is already semi-palindrome. Change "ab" to "aa", it becomes semi-palindrome with d = 1.

// Example 2:

// Input: s = "abcdef", k = 2

// Output: 2

// Explanation: Divide s into substrings "abc" and "def". Each needs one change to become semi-palindrome.

// Example 3:

// Input: s = "aabbaa", k = 3

// Output: 0

// Explanation: Divide s into substrings "aa", "bb" and "aa". All are already semi-palindromes.

 

// Constraints:

// 2 <= s.length <= 200
// 1 <= k <= s.length / 2
// s contains only lowercase English letters.



import java.util.Arrays;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        char[] arr = s.toCharArray();
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                cost[i][j] = getSemiPalindromeCost(arr, i, j);
            }
        }

        int[][] dp = new int[k + 1][n + 1];
        int INF = 1000000;
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        dp[0][0] = 0;

        for (int p = 1; p <= k; p++) {
            for (int j = 2 * p; j <= n; j++) {
                for (int i = 2 * (p - 1); i <= j - 2; i++) {
                    if (dp[p - 1][i] != INF) {
                        dp[p][j] = Math.min(dp[p][j], dp[p - 1][i] + cost[i][j - 1]);
                    }
                }
            }
        }

        return dp[k][n];
    }

    private int getSemiPalindromeCost(char[] arr, int start, int end) {
        int len = end - start + 1;
        int minChanges = len;

        for (int d = 1; d < len; d++) {
            if (len % d == 0) {
                int changes = 0;
                int groupLen = len / d;
                for (int rem = 0; rem < d; rem++) {
                    int left = 0;
                    int right = groupLen - 1;
                    while (left < right) {
                        if (arr[start + rem + left * d] != arr[start + rem + right * d]) {
                            changes++;
                        }
                        left++;
                        right--;
                    }
                }
                minChanges = Math.min(minChanges, changes);
            }
        }

        return minChanges;
    }
}