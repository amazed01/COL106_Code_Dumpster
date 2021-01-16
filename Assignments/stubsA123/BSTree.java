// Chirag Aggarwal (2019EE30564)

// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java


public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree root = this.getRoot(), parent = root.parent;  // Sentinel Root and Parent Of Sentinel

        if (parent == null && root.right == null) {          // Check for empty tree
            root.right = new BSTree(address, size, key);     // Inserted Actual Root (Non-empty Tree)
            root.right.parent = root;
            return root.right;
        }
        else {                                               // Non-empty Tree
            parent = root;                                   // Assign root as actual root of non-empty Tree
            root = root.right;

            while (root != null) {
                if (key < root.key) {
                    parent = root;
                    root = root.left;
                } else if (key > root.key){
                    parent = root;
                    root = root.right;
                } else if (address < root.address) {
                    parent = root;
                    root = root.left;
                } else {
                    parent = root;
                    root = root.right;
                }
            }

            root = parent;
            if (key < root.key) {
                root.left = new BSTree(address, size,key);
                root.left.parent = root;
                return root.left;
            } else if (key > root.key){
                root.right = new BSTree(address, size,key);
                root.right.parent = root;
                return root.right;
            } else {
                if (address < root.address) {
                    root.left = new BSTree(address, size,key);
                    root.left.parent = root;
                    return root.left;
                } else {
                    root.right = new BSTree(address, size,key);
                    root.right.parent = root;
                    return root.right;
                }
            }
        }
    }

    public boolean Delete(Dictionary e) {
        BSTree root = this.getRoot().right;
        if (root == null) {
            return false;
        }

        while (root != null && root != e) {
            if (e.key < root.key) {
                root = root.left;
            } else if (e.key > root.key) {
                root = root.right;
            } else if (e.address < root.address) {
                root = root.left;
            } else if (e.address > root.address) {
                root = root.right;
            } else {
                return false;
            }
        }

        if (root == null) {
            return false;
        }

        // root == e is true now.
        if (root.left == null && root.right == null) {
            BSTree temp = root.parent;                                        //System.out.println("\n 1 \n");
            temp.right = (temp.right == root) ? null : temp.right;
            temp.left = (temp.left == root) ? null : temp.left;

        } else if (root.left == null) {
            BSTree temp = root.parent;                                        //System.out.println("\n 2 \n");
            temp.right = (temp.right == root) ? root.right : temp.right;
            temp.left = (temp.left == root) ? root.right : temp.left;
            root.right.parent = temp;

        } else if (root.right == null) {
            BSTree temp = root.parent;                                       //System.out.println("\n 3 \n");
            temp.right = (temp.right == root) ? root.left : temp.right;
            temp.left = (temp.left == root) ? root.left : temp.left;
            root.left.parent = temp;

        } else {
                                                                              //System.out.println("\n 4 \n");
            BSTree right = root.right;                                        // We are guaranteed about existence of right child now.
            while (right.left != null) {
                right = right.left;
            }

            if (right == right.parent.left) {
                right.parent.left = right.right;
            }
            else if (right == right.parent.right) {
                right.parent.right = right.right;
            }
            else {
                System.out.println("Something Wrong Probably");
                System.out.println(right + "  " + right.parent + "  " + right.parent.left + " " + right.parent.right);
            }

            if (right.right != null) {
                right.right.parent = right.parent;
            }

            // Re-referencing deleted node pointers

            right.parent = root.parent;
            right.right = root.right;
            right.left = root.left;

            if (root == root.parent.right) {
                root.parent.right = right;
            } else if (root == root.parent.left) {
                root.parent.left = right;
            }

            if (root.right != null) {
                root.right.parent = right;
            }
            if (root.left != null) {
                root.left.parent = right;
            }
            root = null;
        }
        return true;
    }

    public BSTree Find(int key, boolean exact) {
        BSTree root = this.getRoot().right;
        if (root == null) {
            return null;
        }
        if (exact) {
            while (root != null) {
                if (key < root.key) {
                    root = root.left;
                } else if (key > root.key) {
                    root = root.right;
                } else {
                    return root;
                }
            }
            return null;
        } else {
            BSTree parent = root.parent;
            while (root != null) {
                if (key < root.key) {
                    parent = root;
                    root = root.left;
                } else if (key > root.key) {
                    parent = root;
                    root = root.right;
                } else {
                    return root;
                }
            }

            if (parent.key >= key) {
                return parent;
            }

            root = parent.getNext();

            while (root != null) {
                if (root.key >= key) {
                    return root;
                }
                root = root.getNext();
            }
            return null;
        }
    }

    public BSTree getFirst() {
        BSTree root = (this.parent == null) ? this.right : this;
        if (root == null) { // Check For empty tree
            return null;
        }

        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public BSTree getNext() {
        if (this.right != null) {
            return this.right.getFirst();
        } else {
            BSTree child = this, parent = child.parent;
            while (parent != null && parent.parent != null) {  // Do until parent becomes root sentinel
                if (child == parent.right) {
                    child = parent;
                    parent = parent.parent;
                } else if (child == parent.left){
                    return parent;
                }
            }
            return null;
        }
    }

    public boolean sanity() {
        BSTree root = this.getRoot().right;
        if (root == null) return true;

        return bstOrder(root);
    }

    private BSTree getRoot() {
        BSTree current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    private static boolean bstOrder(BSTree node) {        // Checks Connectedness AND BST-ORDERING of the tree
        if (node!= null) {
            boolean left = bstOrder(node.left);
            boolean right = bstOrder(node.right);
            if (!left || !right) {
                return false;
            }
            if (node.left == null && node.right == null) return true;     // A single note is BST-Ordered by default

            boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true;  // flag1, flag2 ---> BST-ordering,, flag3, flag4 ---> Connectedness
            if (node.left != null) {
                flag3 = (node.left != node && node.left.parent == node);
                if (node.left.key < node.key) flag1 = true;
                else if (node.left.key > node.key) flag1 = false;
                else flag1 = node.left.address < node.address;
            }
            if (node.right != null) {
                flag4 = (node.right != node && node.right.parent == node);
                if (node.right.key > node.key) flag2 = true;
                else if (node.right.key < node.key) flag2 = false;
                else flag2 = node.right.address > node.address;
            }

            return (flag1 && flag2 && flag3 && flag4);
        } else {
            return true;
        }
    }

}
