public class Main {
    public static void main(String[] args) {
        System.out.println("===== Testing MyHashTable =====");
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();

        for (int i = 0; i < 10000; i++) {
            MyTestingClass key = new MyTestingClass(i % 100, i);
            Student value = new Student("Student" + i);
            table.put(key, value);
        }

        table.printBucketSizes();
        System.out.println("Total size: " + table.size());

        System.out.println("\n===== Testing BST =====");
        BST<Integer, String> bst = new BST<>();
        bst.put(5, "Apple");
        bst.put(2, "Banana");
        bst.put(8, "Cherry");
        bst.put(1, "Date");
        bst.put(3, "Elderberry");

        System.out.println("BST Size: " + bst.size());

        for (BST.Entry<Integer, String> entry : bst) {
            System.out.println("key is " + entry.getKey() + " and value is " + entry.getValue());
        }
    }
}
