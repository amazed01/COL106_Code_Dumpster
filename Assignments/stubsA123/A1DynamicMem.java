// CHIRAG AGGARWAL 2019EE30564


// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).


public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return;
//        BSTree defrag = new BSTree(), temp, aux_temp;
//        temp = (BSTree) this.freeBlk.getFirst();
//        if (temp == null) {
//            return;
//        }
//
//        while (temp != null) {
//            defrag.Insert(temp.address, temp.size, temp.address);
//            temp = temp.getNext();
//        }
//
//        this.freeBlk = new BSTree();
//
//        temp = defrag.getFirst();
//        aux_temp = temp.getNext();
//        if (aux_temp == null) {
//            this.freeBlk.Insert(temp.address, temp.size, temp.size);
//            return;
//        }
//
//        while (aux_temp != null) {
//            if ((temp.address + temp.size) != aux_temp.address) {
//                this.freeBlk.Insert(temp.address, temp.size, temp.size);
//                //defrag.Delete(temp);
//                temp = aux_temp;
//                aux_temp = aux_temp.getNext();
//            } else {
//                temp.size += aux_temp.size;
//                defrag.Delete(aux_temp);
//                aux_temp = temp.getNext();
//            }
//        }
//
//        this.freeBlk.Insert(temp.address, temp.size, temp.size);
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    // Time Complexity :: O(n)
    public int Allocate(int blockSize) {
        if (blockSize <= 0) { // Check invalid allocate argument. No sense for -ve size OR ZERO size allocation
            return -1;
        }
        //BSTree.inorder((BSTree) freeBlk);
        //System.out.println(blockSize);

        BSTree emptyBlock = (BSTree) this.freeBlk.Find(blockSize, false);
        //System.out.println("reached ---> Allocate");
        if (emptyBlock == null) {
            //System.out.println("here@@@@@");
            return -1;
        }
        else if (emptyBlock.size > blockSize) {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            //BSTree.inorder((BSTree) allocBlk);
            this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);
            //BSTree.inorder((BSTree) freeBlk);
            boolean t = this.freeBlk.Delete(emptyBlock);
            //System.out.println("Allocate ka drama" + t);
            //sc.next();
            //BSTree.inorder((BSTree) freeBlk);
            return emptyBlock.address;
        } else {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            //BSTree.inorder((BSTree) allocBlk);
            //this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);
            //BSTree.inorder((BSTree) freeBlk);
            this.freeBlk.Delete(emptyBlock);
            //BSTree.inorder((BSTree) freeBlk);
            return emptyBlock.address;
        }
    }

    // Time Complexity :: O(n)
    public int Free(int startAddr) {
        if (startAddr < 0) { // Check invalid argument. No sense for -ve starting address
            return -1;
        }
        BSTree freeBlock = (BSTree) this.allocBlk.Find(startAddr, true);
        if (freeBlock == null) {
            return -1;
        }
        //BSTree.inorder((BSTree) freeBlk);
//        this.freeBlk.Insert(freeBlock.address, freeBlock.size, freeBlock.size);
//        //this.Defragment();
//        //BSTree.inorder((BSTree) freeBlk);
//        //BSTree.inorder((BSTree) allocBlk);
//        this.allocBlk.Delete(freeBlock);
//        //BSTree.inorder((BSTree) allocBlk);
//        //return freeBlock.address;
        return 0;
    }
}