// 3629. Minimum Jumps to Reach End via Prime Teleportation

// You are given an integer array nums of length n.

// You start at index 0, and your goal is to reach index n - 1.

// From any index i, you may perform one of the following operations:

// Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
// Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
// Return the minimum number of jumps required to reach index n - 1.

 

// Example 1:

// Input: nums = [1,2,4,6]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index 1.
// At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
// Thus, the answer is 2.

// Example 2:

// Input: nums = [2,3,4,7,9]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index i = 1.
// At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
// Thus, the answer is 2.

// Example 3:

// Input: nums = [4,6,5,8]

// Output: 3

// Explanation:

// Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
 

// Constraints:

// 1 <= n == nums.length <= 105
// 1 <= nums[i] <= 106


class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        java.util.Map<Integer, java.util.List<Integer>> map = new java.util.HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int p = 2; p * p <= x; p++) {
                if (x % p == 0) {
                    map.computeIfAbsent(p, k -> new java.util.ArrayList<>()).add(i);
                    while (x % p == 0) x /= p;
                }
            }
            if (x > 1) map.computeIfAbsent(x, k -> new java.util.ArrayList<>()).add(i);
        }
        java.util.Queue<Integer> q = new java.util.LinkedList<>();
        boolean[] vis = new boolean[n];
        java.util.Set<Integer> used = new java.util.HashSet<>();
        q.add(0);
        vis[0] = true;
        int steps = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int i = q.poll();
                if (i == n - 1) return steps;
                if (i - 1 >= 0 && !vis[i - 1]) {
                    vis[i - 1] = true;
                    q.add(i - 1);
                }
                if (i + 1 < n && !vis[i + 1]) {
                    vis[i + 1] = true;
                    q.add(i + 1);
                }
                int x = nums[i];
                if (isPrime(x) && !used.contains(x)) {
                    used.add(x);
                    java.util.List<Integer> list = map.get(x);
                    if (list != null) {
                        for (int j : list) {
                            if (!vis[j]) {
                                vis[j] = true;
                                q.add(j);
                            }
                        }
                    }
                }
            }
            steps++;
        }
        return -1;
    }
    boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}

