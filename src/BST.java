import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size = 0;

    public static class Entry<K, V> {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        if (root == null) {
            root = new Node(key, val);
            size++;
            return;
        }
        Node parent = null, current = root;
        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else {
                current.val = val;
                return;
            }
        }
        int cmp = key.compareTo(parent.key);
        if (cmp < 0) parent.left = new Node(key, val);
        else parent.right = new Node(key, val);
        size++;
    }

    public V get(K key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return current.val;
        }
        return null;
    }

    public void delete(K key) {
        Node parent = null, current = root;
        while (current != null && !current.key.equals(key)) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) current = current.left;
            else current = current.right;
        }
        if (current == null) return; // not found

        // Case 1: no child
        if (current.left == null && current.right == null) {
            if (parent == null) root = null;
            else if (parent.left == current) parent.left = null;
            else parent.right = null;
        }
        // Case 2: one child
        else if (current.left == null || current.right == null) {
            Node child = (current.left != null) ? current.left : current.right;
            if (parent == null) root = child;
            else if (parent.left == current) parent.left = child;
            else parent.right = child;
        }
        // Case 3: two children (find successor)
        else {
            Node successorParent = current;
            Node successor = current.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            current.key = successor.key;
            current.val = successor.val;
            if (successorParent.left == successor)
                successorParent.left = successor.right;
            else successorParent.right = successor.right;
        }
        size--;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final LinkedList<Node> stack = new LinkedList<>();
            private Node current = root;

            {
                pushLeft(current);
            }

            private void pushLeft(Node node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) throw new NoSuchElementException();
                Node node = stack.pop();
                pushLeft(node.right);
                return new Entry<>(node.key, node.val);
            }
        };
    }
}
