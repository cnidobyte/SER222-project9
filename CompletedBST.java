package edu.ser222.m03_02;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A binary search tree based implementation of a symbol table.
 * 
 * Completion time: (your completion time)
 *
 * @author Shota Bennett, Sedgewick, Acuna
 * @version 1.03
 */
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    private Node<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value> iter = root;

        while(iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    private Value get(Node<Key, Value> x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Key min() {
        if(root == null)
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Key, Value> min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        if(root == null)
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Key, Value> max(Node x) {
    if (x.right == null) return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        if(root == null)
            throw new NoSuchElementException();

        Node<Key, Value> x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node<Key, Value> t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k-t-1);
        else return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node<Key, Value> x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    @Override
    public void deleteMin() {
        if(root == null)
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        if (root == null)
            return new LinkedList<>();
        else
            return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi)
    {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi)
    {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public Key ceiling(Key key) {
        //SKIP, UNNEEDED
        return null;
    }
    public Node getRoot() {
        return root;
    }

    public boolean contains(Key key) {
        if(get(key) != null) return true;
        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void deleteMax()  {
        if(root == null) {
            throw new NoSuchElementException();
        }
        root = deleteMax(root);
    }

    private Node<Key, Value> deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    public int size(Key lo, Key hi) {
        Queue<Key> queue = (Queue<Key>) keys(lo, hi);
        return queue.size();
    }

    public void putFast(Key key, Value val) {

        Node<Key, Value> iter = root;
        Node<Key, Value> iterStalker = null;
        Node<Key, Value> euNode = new Node(key, val, 1);
        if(root == null) {
            root = euNode;
            return;
        }

        // Iterates through tree to proper spot. Stalker follows on node behind.
        while(iter != null) {
            iterStalker = iter;
            int cmp = key.compareTo(iter.key);
            if(cmp < 0) iter = iter.left;
            else if(cmp > 0) iter = iter.right;
        }

        // Places new node
        if(key.compareTo(iterStalker.key) < 0) {
            iterStalker.left = euNode;
        }
        else {
            iterStalker.right = euNode;
        }

        // Update size.
        iterStalker.N = size(iterStalker.left) + size(iterStalker.right) + 1;
    }

    // Iterates through tree, returning value.
    public Value getFast(Key key) {
        Node<Key, Value> iter = root;

        while(iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    public void balance() {
        //TODO
        LinkedList<Node> list = new LinkedList<>();
        inorder(root, list);
        root = createTree(0, list.size(), list);
    }

    //Divide and Conquer strategy for building bst, starting from middle out.
    private Node<Key, Value> createTree(int lo, int hi, LinkedList<Node> bstArray) {
        if(lo == hi) return null;
        int mid = lo + (hi - lo) / 2;
        Node euRoot = bstArray.get(mid);
        euRoot.left = createTree(lo, mid, bstArray);
        euRoot.right = createTree(mid + 1, hi, bstArray);
        euRoot.N = size(euRoot.left) + size(euRoot.right) + 1;
        return euRoot;
    }

    // Create sorted array to use to build trees
    private void inorder(Node x, LinkedList<Node> list) {
        if(x == null) return;
        inorder(x.left, list);
        list.add(x);
        inorder(x.right, list);
    }

    public String displayLevel(Key key) {
        //TODO
        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        Node<Key, Value> start = floor(root, key);
        if(start == null) return "empty";

        // Enqueue the start node, aka first level
        queue.add(start);
        while (true) {
            int count = queue.size();
            if(count == 0)
                break;
            while (count > 0) {
                //Dequeue one level, enqueue next level
                Node node = queue.peek();
                sb.append(node.val + " ");
                queue.remove();
                if(node.left != null)
                    queue.add(node.left);
                if(node.right != null)
                    queue.add(node.right);
                count--;
            }
        }
        return sb.toString();
    }

    /**
     * entry point for testing.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BST<Integer, String> bst = new CompletedBST();
        
        bst.put(10, "TEN");
        bst.put(3, "THREE");
        bst.put(1, "ONE");
        bst.put(5, "FIVE");
        bst.put(2, "TWO");
        bst.put(7, "SEVEN");
        
        System.out.println("Before balance:");
        System.out.println(bst.size());
        System.out.println(bst.displayLevel(10)); //root
        
        System.out.println("After balance:");
        bst.balance();
        System.out.println(bst.size());
        System.out.println(bst.displayLevel(5)); //root
    }
}
