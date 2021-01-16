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
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    // Time Complexity :: O(n)
    public int Allocate(int blockSize) {
        if (blockSize <= 0) { // Check invalid allocate argument. No sense for -ve size OR ZERO size allocation
            return -1;
        }

        BSTree emptyBlock = (BSTree) this.freeBlk.Find(blockSize, false);
        if (emptyBlock == null) {
            return -1;
        }
        else if (emptyBlock.size > blockSize) {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);
            this.freeBlk.Delete(emptyBlock);
            return emptyBlock.address;
        } else {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            //this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);
            this.freeBlk.Delete(emptyBlock);
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
        this.freeBlk.Insert(freeBlock.address, freeBlock.size, freeBlock.size);
        this.allocBlk.Delete(freeBlock);
        //return freeBlock.address;
        return 0;
    }
}