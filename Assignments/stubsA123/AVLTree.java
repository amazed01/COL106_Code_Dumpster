// CHIRAG AGGARWAL 2019EE30564

// Class: Height balanced AVL Tree
// Binary Search Tree


import java.util.Random;

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();

        // this.height = -1;

        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    public static void main(String[] args) {

        Random rand = new Random();
        AVLTree a = new AVLTree();
        for (int i = 1; i <= 5000; i++) {
            int x = rand.nextInt(1000000);
            a.Insert(i, i, x);
        }
        //levelOrder(a);
        System.out.println(a.getRoot().right.height);
        System.out.println("Root's key\t:\t" + a.getRoot().right.key);
        System.out.println("Sanity\t\t:\t" + a.sanity());
        int count = 0;
        int san = 0;
        for (AVLTree temp = a.getFirst(); temp != null; temp = temp.getNext()) {
            //System.out.print(temp.key + " ");
            count++;
        }
        System.out.println();
        System.out.println("No of nodes\t:\t" + count);
        //Deletion
        System.out.println("Deletion");
        boolean del;

        for(AVLTree temp = a.getRoot().right;temp!=null;){
            temp = a.getRoot().right;
            Dictionary d = a.getRoot().right;
            del = a.Delete(d);
            //levelOrder(a);
            //levelOrder(a);
//            if (a.getRoot().right!=null)
//            System.out.println("Root's key\t:\t" + a.getRoot().right.key);
            //System.out.println("Sanity\t\t:\t" + a.sanity());
//            for (AVLTree temp1 = a.getFirst(); temp1 != null; temp1 = temp1.getNext()) {
//                 System.out.print(temp1.key + " ");
//            }
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            if(!a.sanity()){
                //System.out.println("false");
                temp.print();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //levelOrder(a);
                san++;
                break;
            }
        }
        System.out.println(san);
    }




    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    public AVLTree Insert(int address, int size, int key) {
        AVLTree root = this.getRoot(), parent = root.parent;  // Sentinel Root and Parent Of Sentinel

        if (parent == null && root.right == null) {          // Check for empty tree
            root.right = new AVLTree(address, size, key);     // Inserted Actual Root (Non-empty Tree)
            root.right.parent = root;
            root.right.height = 1;
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
                root.left = new AVLTree(address, size,key);
                root.left.parent = root;
                root =  root.left;
            } else if (key > root.key){
                root.right = new AVLTree(address, size,key);
                root.right.parent = root;
                root = root.right;
            } else if (address < root.address) {
                root.left = new AVLTree(address, size,key);
                root.left.parent = root;
                root = root.left;
            } else {
                root.right = new AVLTree(address, size,key);
                root.right.parent = root;
                root = root.right;
            }

            AVLTree x, y, z; int initialHeight;

            x = root; y = x.parent; z = y.parent;
            x.height += 1;
            initialHeight = y.height;
            y.height = checkHeightBalance(y, true); //(y.left == null || y.right == null) ? x.height + 1 : Math.max(y.left.height, y.right.height) + 1;
            if (y.height == initialHeight) return root;

            while (z.parent != null) {
                initialHeight = z.height;
                z.height = checkHeightBalance(z, true); //(z.left == null || z.right == null) ? y.height + 1 : Math.max(z.left.height, z.right.height) + 1;
                if (z.height == -1) {
//                    y = (getSafeHeight(z.left) > getSafeHeight(z.right)) ? z.left : z.right;
//                    x = (getSafeHeight(y.left) >= getSafeHeight(y.right)) ? y.left : y.right;
                    AVLTree top = rotation(x, y, z);

                    break; // Rotation Required
                } else if (z.height == initialHeight) break;
                x = x.parent;
                y = y.parent;
                z = z.parent;
            }

            return root;
        }
    }

    public boolean Delete(Dictionary e) {
        AVLTree root = this.getRoot().right;
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
        AVLTree temp;
        if (root.left == null && root.right == null) {
            temp = root.parent;                                        //System.out.println("\n 1 \n");
            temp.right = (temp.right == root) ? null : temp.right;
            temp.left = (temp.left == root) ? null : temp.left;
            traverseRotation(temp);

        } else if (root.left == null) {
            temp = root.parent;                                        //System.out.println("\n 2 \n");
            temp.right = (temp.right == root) ? root.right : temp.right;
            temp.left = (temp.left == root) ? root.right : temp.left;
            root.right.parent = temp;
            traverseRotation(temp);

        } else if (root.right == null) {
            temp = root.parent;                                       //System.out.println("\n 3 \n");
            temp.right = (temp.right == root) ? root.left : temp.right;
            temp.left = (temp.left == root) ? root.left : temp.left;
            root.left.parent = temp;
            traverseRotation(temp);

        } else {
            //System.out.println("\n 4 \n");
            AVLTree right = root.right;                                        // We are guaranteed about existence of right child now.
            while (right.left != null) {
                right = right.left;
            }

            temp = right.parent;
            if (temp == root) temp = right;
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
            traverseRotation(temp);
        }
        return true;
    }

    public AVLTree Find(int key, boolean exact) {
        AVLTree root = this.getRoot().right;
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
            AVLTree parent = root.parent;
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


    public AVLTree getFirst() {
        //return (AVLTree) super.getFirst();
        AVLTree root = (this.parent == null) ? this.right : this;
        if (root == null) { // Check For empty tree
            return null;
        }

        while (root.left != null) {
            root = root.left;
        }
        return root;

    }

    public AVLTree getNext() {
        //return (AVLTree) super.getNext();
        if (this.right != null) {
            return this.right.getFirst();
        } else {
            AVLTree child = this, parent = child.parent;
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
        AVLTree root = this.getRoot().right;
        if (root == null) return true;

        return bstOrder(root) && isBalanced(root);
    }

    private static boolean bstOrder(AVLTree node) {        // Checks Connectedness AND BST-ORDERING of the tree
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


    private int calcHeight(AVLTree root) {
        if (root == null) {              // Height of 'null' is 0 in my implementation, for 'leaf' height would be 1
            return 0;
        }
        else if (root.parent == null && root.right == null) {  // Height of sentinel root returns -2 in case of empty tree
            return -2;
        }
        else if (root.parent == null) {  // Call from sentinel for non-empty tree calculates Height of complete tree
            return calcHeight(root.right);
        }
        else {
            int leftHeight = calcHeight(root.left), rightHeight = calcHeight(root.right);
            root.height = Math.max(leftHeight, rightHeight) + 1;
            return root.height;
        }
    }

    private AVLTree getRoot() {
        AVLTree current = this;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    private boolean isBalanced(AVLTree root) {
        if (root == null) {
            return true;
        }
        else if (root.parent == null && root.right == null) {
            return true;
        }
        else if (root.parent == null) {  // Call from sentinel for non-empty tree checks Height of non-empty tree
            return isBalanced(root.right);
        }
        return checkHeightBalance(root) != -1;
    }

    private int checkHeightBalance(AVLTree root) {  // Exponential in Time
        if (root == null) {
            return 0;
        } else {
            int leftTree = checkHeightBalance(root.left);
            int rightTree = checkHeightBalance(root.right);

            if (leftTree == -1 || rightTree == -1 || Math.abs(leftTree - rightTree) > 1) {
                return -1;
            }

            int rootHeight = Math.max(leftTree,rightTree) + 1;

            return rootHeight;//(rootHeight == root.height) ? rootHeight : -1;
        }
    }

    private int checkHeightBalance(AVLTree root, boolean non_recursive) { // Constant in time O(1)
        if (root == null) {
            return 0;
        } else {
            int leftTree = (root.left == null) ? 0 : root.left.height;
            int rightTree = (root.right == null) ? 0 : root.right.height;

            if (leftTree == -1 || rightTree == -1 || Math.abs(leftTree - rightTree) > 1) {
                return -1;
            }

            return Math.max(leftTree,rightTree) + 1;
        }
    }

    private AVLTree rotation(AVLTree x, AVLTree y, AVLTree z) {
        AVLTree top = z.parent;
        if (x == y.left && y == z.left) {
            if (top.left == z) {
                top.left = y;
            } else {
                top.right = y;
            }
            y.parent = top;

            z.left = y.right;
            if (y.right != null) y.right.parent = z;
            y.right = z;
            z.parent = y;

//            if (z.left != null) z.left.height = checkHeightBalance(z.left, true);
//            if (z.right != null) z.right.height = checkHeightBalance(z.right, true);
//            if (x.left != null) x.left.height = checkHeightBalance(y.left, true);
//            if (x.right != null) x.left.height = checkHeightBalance(y.left, true);

            z.height = checkHeightBalance(z, true);
            if (x != null) x.height = checkHeightBalance(x, true);
            y.height = checkHeightBalance(y, true);
            return (x!= null) ? x : y;
        }
        else if (x == y.right && y == z.right) {
            if (top.left == z) {
                top.left = y;
            } else {
                top.right = y;
            }
            y.parent = top;

            z.right = y.left;
            if (y.left != null) y.left.parent = z;
            y.left = z;
            z.parent = y;

//            if (z.left != null) z.left.height = checkHeightBalance(z.left, true);
//            if (z.right != null) z.right.height = checkHeightBalance(z.right, true);
//            if (x.left != null) x.left.height = checkHeightBalance(y.left, true);
//            if (x.right != null) x.left.height = checkHeightBalance(y.left, true);

            z.height = checkHeightBalance(z, true);
            if (x != null) x.height = checkHeightBalance(x, true);
            y.height = checkHeightBalance(y, true);
            return (x!= null) ? x : y;
        }
        else if (x == y.right && y == z.left) {
            if (top.left == z) {
                top.left = x;
            } else {
                top.right = x;
            }
            x.parent = top;

            y.right = x.left;
            if (x.left != null) x.left.parent = y;
            x.left = y;
            y.parent = x;

            z.left = x.right;
            if (x.right != null) x.right.parent = z;
            x.right = z;
            z.parent = x;

//            if (z.left != null) z.left.height = checkHeightBalance(z.left, true);
//            if (z.right != null) z.right.height = checkHeightBalance(z.right, true);
//            if (y.left != null) y.left.height = checkHeightBalance(y.left, true);
//            if (y.right != null) y.left.height = checkHeightBalance(y.left, true);

            z.height = checkHeightBalance(z, true);
            y.height = checkHeightBalance(y, true);
            x.height = checkHeightBalance(x, true);
            return y;
        }
        else if (x == y.left && y == z.right) {
            if (top.left == z) {
                top.left = x;
            } else {
                top.right = x;
            }
            x.parent = top;

            z.right = x.left;
            if (x.left != null) x.left.parent = z;
            x.left = z;
            z.parent = x;

            y.left = x.right;
            if (x.right != null) x.right.parent = y;
            x.right = y;
            y.parent = x;

//            if (z.left != null) z.left.height = checkHeightBalance(z.left, true);
//            if (z.right != null) z.right.height = checkHeightBalance(z.right, true);
//            if (y.left != null) y.left.height = checkHeightBalance(y.left, true);
//            if (y.right != null) y.left.height = checkHeightBalance(y.left, true);

            z.height = checkHeightBalance(z, true);
            y.height = checkHeightBalance(y, true);
            x.height = checkHeightBalance(x, true);
            return y;
        }
        return null;
    }

    private void traverseRotation(AVLTree node) {
        AVLTree x,y,z; int initialHeight;
        z = node;
        while (z.parent != null) {
            initialHeight = z.height;
            z.height = checkHeightBalance(z, true);
            if (z.height == -1) {
                y = (getSafeHeight(z.left) > getSafeHeight(z.right)) ? z.left : z.right;
                if (y == null) {
                    z.height = 1;
                    continue;
                }
                x = (getSafeHeight(y.left) >= getSafeHeight(y.right)) ? y.left : y.right;
                if (x == null) {
                    y.height = 1;
                    z.height = checkHeightBalance(z,true);continue;
                }
                AVLTree top = rotation(x, y, z);
                z = top;
                //if (top.height == -1) System.out.println("Wrong something in traverse Delete");
            }
            else z = z.parent;
        }
    }

    private static int getSafeHeight(AVLTree node) {
        if (node == null) return 0;
        return node.height;
    }

    private void print() {
        System.out.print("(" + this.height + ", " + this.key + ")");
    }

    private static void inorder(AVLTree node) {
        if (node != null) {
            inorder(node.left);
            node.print();
            inorder(node.right);
        }

    }
}

