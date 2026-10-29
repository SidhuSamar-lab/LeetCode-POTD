// 3841. Palindromic Path Queries in a Tree
// Hard
// Topics
// premium lock icon
// Companies
// Hint
// You are given an undirected tree with n nodes labeled 0 to n - 1. This is represented by a 2D array edges of length n - 1, where edges[i] = [ui, vi] indicates an undirected edge between nodes ui and vi.

// You are also given a string s of length n consisting of lowercase English letters, where s[i] represents the character assigned to node i.

// You are also given a string array queries, where each queries[i] is either:

// "update ui c": Change the character at node ui to c. Formally, update s[ui] = c.
// "query ui vi": Determine whether the string formed by the characters on the unique path from ui to vi (inclusive) can be rearranged into a palindrome.
// Return a boolean array answer, where answer[j] is true if the jth query of type "query ui vi"​​​​​​​ can be rearranged into a palindrome, and false otherwise.

 

// Example 1:

// Input: n = 3, edges = [[0,1],[1,2]], s = "aac", queries = ["query 0 2","update 1 b","query 0 2"]

// Output: [true,false]

// Explanation:

// "query 0 2": Path 0 → 1 → 2 gives "aac", which can be rearranged to form "aca", a palindrome. Thus, answer[0] = true.
// "update 1 b": Update node 1 to 'b', now s = "abc".
// "query 0 2": Path characters are "abc", which cannot be rearranged to form a palindrome. Thus, answer[1] = false.
// Thus, answer = [true, false].

// Example 2:

// Input: n = 4, edges = [[0,1],[0,2],[0,3]], s = "abca", queries = ["query 1 2","update 0 b","query 2 3","update 3 a","query 1 3"]

// Output: [false,false,true]

// Explanation:

// "query 1 2": Path 1 → 0 → 2 gives "bac", which cannot be rearranged to form a palindrome. Thus, answer[0] = false.
// "update 0 b": Update node 0 to 'b', now s = "bbca".
// "query 2 3": Path 2 → 0 → 3 gives "cba", which cannot be rearranged to form a palindrome. Thus, answer[1] = false.
// "update 3 a": Update node 3 to 'a', s = "bbca".
// "query 1 3": Path 1 → 0 → 3 gives "bba", which can be rearranged to form "bab", a palindrome. Thus, answer[2] = true.
// Thus, answer = [false, false, true].

 

// Constraints:

// 1 <= n == s.length <= 5 * 104
// edges.length == n - 1
// edges[i] = [ui, vi]
// 0 <= ui, vi <= n - 1
// s consists of lowercase English letters.
// The input is generated such that edges represents a valid tree.
// 1 <= queries.length <= 5 * 104​​​​​​​
// queries[i] = "update ui c" or
// queries[i] = "query ui vi"
// 0 <= ui, vi <= n - 1
// c is a lowercase English letter.



import java.util.ArrayList;
import java.util.List;

class Solution {
    int[] in, out, depth;
    int[][] up;
    int timer;
    int[] bit;
    char[] chars;
    List<Integer>[] adj;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        in = new int[n];
        out = new int[n];
        depth = new int[n];
        up = new int[n][16];
        bit = new int[n + 2];
        chars = s.toCharArray();
        adj = new ArrayList[n];
        
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        timer = 0;
        dfs(0, 0, 0);

        List<Boolean> ans = new ArrayList<>();
        for (String q : queries) {
            if (q.charAt(0) == 'u') {
                int space1 = q.indexOf(' ');
                int space2 = q.indexOf(' ', space1 + 1);
                int u = Integer.parseInt(q.substring(space1 + 1, space2));
                char c = q.charAt(space2 + 1);
                
                int oldVal = 1 << (chars[u] - 'a');
                int newVal = 1 << (c - 'a');
                int change = oldVal ^ newVal;
                chars[u] = c;
                
                updateBIT(in[u], change);
                updateBIT(out[u] + 1, change);
            } else {
                int space1 = q.indexOf(' ');
                int space2 = q.indexOf(' ', space1 + 1);
                int u = Integer.parseInt(q.substring(space1 + 1, space2));
                int v = Integer.parseInt(q.substring(space2 + 1));
                
                int lca = getLCA(u, v);
                int maskU = queryBIT(in[u]);
                int maskV = queryBIT(in[v]);
                int charLCA = 1 << (chars[lca] - 'a');
                
                int pathMask = maskU ^ maskV ^ charLCA;
                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }
        return ans;
    }

    private void dfs(int u, int p, int d) {
        in[u] = ++timer;
        up[u][0] = p;
        for (int i = 1; i < 16; i++) {
            up[u][i] = up[up[u][i - 1]][i - 1];
        }
        depth[u] = d;
        
        int val = 1 << (chars[u] - 'a');
        updateBIT(in[u], val);

        for (int v : adj[u]) {
            if (v != p) {
                dfs(v, u, d + 1);
            }
        }
        
        out[u] = timer;
        updateBIT(out[u] + 1, val);
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        for (int i = 15; i >= 0; i--) {
            if (depth[u] - (1 << i) >= depth[v]) {
                u = up[u][i];
            }
        }
        if (u == v) return u;
        
        for (int i = 15; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }
        return up[u][0];
    }

    private void updateBIT(int i, int delta) {
        for (; i < bit.length; i += i & -i) {
            bit[i] ^= delta;
        }
    }

    private int queryBIT(int i) {
        int sum = 0;
        for (; i > 0; i -= i & -i) {
            sum ^= bit[i];
        }
        return sum;
    }
}