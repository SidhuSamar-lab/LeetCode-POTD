// 110. Balanced Binary Tree

// Example 1:

// Input: root = [3,9,20,null,null,15,7]
// Output: true
// Example 2:

// Input: root = [1,2,2,3,3,null,null,4,4]
// Output: false
// Example 3:

// Input: root = []
// Output: true
 

// Constraints:

// The number of nodes in the tree is in the range [0, 5000].
// -104 <= Node.val <= 104







public class Day14 {
    int val;
    Day14 left;
    Day14 right;
    Day14() {}
    Day14(int val) { this.val = val; }
    Day14(int val, Day14 left, Day14 right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
 
class Solution {
    public boolean isBalanced(Day14 root) {
        return checkHeight(root) != -1;
    }
    
    private int checkHeight(Day14 node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1;
        }
        
        int rightHeight = checkHeight(node.right);
        if (rightHeight == -1) {
            return -1;
        }
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}