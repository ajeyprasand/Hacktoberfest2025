// BinaryTreesDemo.java
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinaryTreesDemo {

    static class Node {
        int val;
        Node left, right;

        Node(int val) { this.val = val; }
    }

    static class BinaryTree {
        Node root;

        // Level-order insert (keeps tree as complete as possible)
        public void insert(int val) {
            Node newNode = new Node(val);
            if (root == null) {
                root = newNode;
                return;
            }
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                Node cur = q.poll();
                if (cur.left == null) {
                    cur.left = newNode;
                    return;
                } else {
                    q.add(cur.left);
                }
                if (cur.right == null) {
                    cur.right = newNode;
                    return;
                } else {
                    q.add(cur.right);
                }
            }
        }

        // Traversals
        public void preorder(Node node) {
            if (node == null) return;
            System.out.print(node.val + " ");
            preorder(node.left);
            preorder(node.right);
        }

        public void inorder(Node node) {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.val + " ");
            inorder(node.right);
        }

        public void postorder(Node node) {
            if (node == null) return;
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.val + " ");
        }

        // Level-order print
        public void levelOrder() {
            if (root == null) return;
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                Node cur = q.poll();
                System.out.print(cur.val + " ");
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
        }

        // Size (number of nodes)
        public int size(Node node) {
            if (node == null) return 0;
            return 1 + size(node.left) + size(node.right);
        }

        // Height (edges count). If you prefer nodes count, add +1 conventionally.
        public int height(Node node) {
            if (node == null) return -1; // -1 for edges, 0 if you want nodes count
            int lh = height(node.left);
            int rh = height(node.right);
            return 1 + Math.max(lh, rh);
        }

        // Find node with a value (returns first found in traversal)
        public Node find(int val) {
            if (root == null) return null;
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                Node cur = q.poll();
                if (cur.val == val) return cur;
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
            return null;
        }

        // Delete a node by value: replaces the node's value with deepest node value then removes deepest node.
        public boolean delete(int val) {
            if (root == null) return false;
            if (root.left == null && root.right == null) {
                if (root.val == val) {
                    root = null;
                    return true;
                }
                return false;
            }

            Queue<Node> q = new LinkedList<>();
            q.add(root);
            Node target = null;
            Node last = null;

            while (!q.isEmpty()) {
                last = q.poll();
                if (last.val == val) target = last;
                if (last.left != null) q.add(last.left);
                if (last.right != null) q.add(last.right);
            }

            if (target == null) return false;

            // last now points to deepest rightmost node
            int deepestVal = last.val;
            // Delete deepest node
            deleteDeepest(last);
            // Replace target's value
            target.val = deepestVal;
            return true;
        }

        // Helper to delete deepest node (given reference to deepest)
        private void deleteDeepest(Node deepest) {
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                Node cur = q.poll();
                if (cur.left != null) {
                    if (cur.left == deepest) {
                        cur.left = null;
                        return;
                    } else {
                        q.add(cur.left);
                    }
                }
                if (cur.right != null) {
                    if (cur.right == deepest) {
                        cur.right = null;
                        return;
                    } else {
                        q.add(cur.right);
                    }
                }
            }
        }
    }

    static class BinarySearchTree {
        Node root;

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private Node insertRec(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insertRec(node.left, val);
            else if (val > node.val) node.right = insertRec(node.right, val);
            // if equal, ignore or handle duplicates as you prefer; here we ignore duplicates
            return node;
        }

        public boolean search(int val) {
            return searchRec(root, val);
        }

        private boolean searchRec(Node node, int val) {
            if (node == null) return false;
            if (node.val == val) return true;
            return val < node.val ? searchRec(node.left, val) : searchRec(node.right, val);
        }

        public void inorder() {
            inorderRec(root);
        }

        private void inorderRec(Node node) {
            if (node == null) return;
            inorderRec(node.left);
            System.out.print(node.val + " ");
            inorderRec(node.right);
        }

        // Delete node
        public void delete(int val) {
            root = deleteRec(root, val);
        }

        private Node deleteRec(Node node, int val) {
            if (node == null) return null;
            if (val < node.val) node.left = deleteRec(node.left, val);
            else if (val > node.val) node.right = deleteRec(node.right, val);
            else {
                // node to delete
                // case 1: no child
                if (node.left == null && node.right == null) return null;
                // case 2: one child
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                // case 3: two children -> replace with inorder successor (smallest in right subtree)
                Node successor = minNode(node.right);
                node.val = successor.val;
                node.right = deleteRec(node.right, successor.val);
            }
            return node;
        }

        private Node minNode(Node node) {
            Node cur = node;
            while (cur.left != null) cur = cur.left;
            return cur;
        }
    }

    // ---------------------------
    // Demo main
    // ---------------------------
    public static void main(String[] args) {
        System.out.println("=== Binary Tree Demo ===");
        BinaryTree bt = new BinaryTree();
        int[] values = {10, 20, 30, 40, 50, 60, 70};
        for (int v : values) bt.insert(v);

        System.out.print("Level-order: ");
        bt.levelOrder();
        System.out.println();

        System.out.print("Inorder: ");
        bt.inorder(bt.root);
        System.out.println();

        System.out.print("Preorder: ");
        bt.preorder(bt.root);
        System.out.println();

        System.out.print("Postorder: ");
        bt.postorder(bt.root);
        System.out.println();

        System.out.println("Size: " + bt.size(bt.root));
        System.out.println("Height (edges): " + bt.height(bt.root));
        System.out.println("Find 50: " + (bt.find(50) != null));
        System.out.println("Delete 30: " + bt.delete(30));
        System.out.print("Level-order after delete: ");
        bt.levelOrder();
        System.out.println();

        System.out.println("\n=== Binary Search Tree Demo ===");
        BinarySearchTree bst = new BinarySearchTree();
        int[] bstVals = {50, 30, 70, 20, 40, 60, 80};
        for (int v : bstVals) bst.insert(v);

        System.out.print("BST inorder (sorted): ");
        bst.inorder();
        System.out.println();

        System.out.println("Search 60: " + bst.search(60));
        System.out.println("Search 25: " + bst.search(25));

        System.out.println(
