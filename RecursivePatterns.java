package com.karat.cheatsheet;

public class RecursivePatterns {

    // ✅ 1. Max Depth of Binary Tree
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    // ✅ 2. Factorial (Classic)
    public static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // ✅ 3. Fibonacci (Naive)
    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // ✅ 4. Sum of Array
    public static int sumArray(int[] arr, int index) {
        if (index == arr.length) return 0;
        return arr[index] + sumArray(arr, index + 1);
    }

    // ✅ 5. Reverse String
    public static String reverse(String s) {
        if (s.isEmpty()) return s;
        return reverse(s.substring(1)) + s.charAt(0);
    }

    // ✅ 6. Palindrome Check
    public static boolean isPalindrome(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalindrome(s, left + 1, right - 1);
    }

    // ✅ 7. DFS on Binary Tree (Preorder)
    public static void dfs(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        dfs(root.left);
        dfs(root.right);
    }

    // ✅ TreeNode class for tree-based recursion
    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }
}