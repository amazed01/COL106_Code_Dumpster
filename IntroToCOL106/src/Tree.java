public class Tree<T> {
    static class TreeNode<T> {
        public T data;
        public int key;
        public TreeNode<T> parent, left, right; // Binary tree

        public TreeNode(int key) {
            this.data = null;
            this.key = key;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
    }

    public TreeNode<T> root;

    private TreeNode<T> search(TreeNode<T> t, int search_element) {
        TreeNode<T> current = t;
        while (current != null && current.key != search_element) {
            if (search_element < current.key) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current;
    }
    public TreeNode<T> search(int key) {
        return search(root, key);
    }

    public void add(int element) {
        TreeNode<T> newVertex = new TreeNode<>(element);

        if (root == null) {
            root = newVertex;
            return;
        }

        TreeNode<T> parent = null, current = root;

        while (current != null) {
            if (current.key > element) {
                parent = current;
                current = current.left;
            } else {
                parent = current;
                current = current.right;
            }
        }

        if (parent.key > element) {
            parent.left = newVertex;
            newVertex.parent = parent;
        } else {
            parent.right = newVertex;
            newVertex.parent = parent;
        }
    }

    private void preOrderTraversal(TreeNode node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }
    public void preOrderTraversal() {
        this.preOrderTraversal(this.root);
    }

    private void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.key + " ");
            inOrderTraversal(node.right);
        }
    }
    public void inOrderTraversal() {
        this.inOrderTraversal(this.root);
    }

    private void postOrderTraversal(TreeNode node) {
        if (node != null) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            System.out.print(node.key + " ");
        }
    }
    public void postOrderTraversal() {
        this.postOrderTraversal(this.root);
    }

}

//      TEST CODE
//        Tree<Integer> example = new Tree<>();
//        example.add(2);
//        example.add(1);
//        example.add(3);
//        example.add(5);
//        System.out.println(example.search(2).key);
//        example.preOrderTraversal();
//        System.out.println();
//        example.inOrderTraversal();
//        System.out.println();
//        example.postOrderTraversal();

