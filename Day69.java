// 3863. Minimum Operations to Sort a String
// Medium
// Topics
// premium lock icon
// Companies
// Hint
// You are given a string s consisting of lowercase English letters.

// In one operation, you can select any substring of s that is not the entire string and sort it in non-descending alphabetical order.

// Return the minimum number of operations required to make s sorted in non-descending order. If it is not possible, return -1.

 

// Example 1:

// Input: s = "dog"

// Output: 1

// Explanation:​​​​​​​

// Sort substring "og" to "go".
// Now, s = "dgo", which is sorted in ascending order. Thus, the answer is 1.
// Example 2:

// Input: s = "card"

// Output: 2

// Explanation:

// Sort substring "car" to "acr", so s = "acrd".
// Sort substring "rd" to "dr", making s = "acdr", which is sorted in ascending order. Thus, the answer is 2.
// Example 3:

// Input: s = "gf"

// Output: -1

// Explanation:

// It is impossible to sort s under the given constraints. Thus, the answer is -1.
 

// Constraints:

// 1 <= s.length <= 105
// s consists of only lowercase English letters.



import java.util.Arrays;

class Solution {
    public int minOperations(String s) {
        int n = s.length();
        if (isSorted(s)) return 0;
        if (n == 2) return -1;
        
        String target = sortStr(s);
        
        String pre = sortSub(s, 0, n - 2);
        String suf = sortSub(s, 1, n - 1);
        
        if (pre.equals(target) || suf.equals(target)) {
            return 1;
        }
        
        if (sortSub(pre, 1, n - 1).equals(target) || sortSub(suf, 0, n - 2).equals(target)) {
            return 2;
        }
        
        return 3;
    }
    
    private boolean isSorted(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
    
    private String sortStr(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    
    private String sortSub(String s, int i, int j) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr, i, j + 1);
        return new String(arr);
    }
}