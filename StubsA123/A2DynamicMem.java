// Chirag Aggarwal (2019EE30564)
// NOTE-->  I have overridden the Allocate and Free function in A2DynamicMem. I had to do this because in A1DynamicMem,
//          I had to type-cast the object into DLL data-structure, which won't work with BSTree.
//          It should not be a problem as far as A1 is considered, however, I need to change the functions to handle
//          BSTree and AVLTree classes. I am sorry I forgot to modify that A1DynamicMem from (A1list) type cast to generic Dictionary class.
//          I did not know if it is allowed to resubmit the modified A1DynamicMem, so to do away with that,
//          I decided to override the Allocate and Free function definitions

// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {
        super();
    }

    public A2DynamicMem(int size) {
        super(size);
    }

    public A2DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public int Allocate(int blockSize) {
        if (blockSize <= 0) { // Check invalid allocate argument. No sense for -ve size OR ZERO size allocation
            return -1;
        }

        Dictionary emptyBlock = this.freeBlk.Find(blockSize, false);
        if (emptyBlock == null) {
            return -1;
        }
        else if (emptyBlock.size > blockSize) {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            this.freeBlk.Delete(emptyBlock);
            this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);

            return emptyBlock.address;
        } else {
            this.allocBlk.Insert(emptyBlock.address, blockSize, emptyBlock.address);
            //this.freeBlk.Insert(emptyBlock.address + blockSize, emptyBlock.size - blockSize, emptyBlock.size - blockSize);
            this.freeBlk.Delete(emptyBlock);
            return emptyBlock.address;
        }
    }

    public int Free(int startAddr) {
        if (startAddr < 0) { // Check invalid argument. No sense for -ve starting address
            return -1;
        }
        Dictionary freeBlock = this.allocBlk.Find(startAddr, true);
        if (freeBlock == null) {
            return -1;
        }
        this.freeBlk.Insert(freeBlock.address, freeBlock.size, freeBlock.size);
        this.allocBlk.Delete(freeBlock);
        //return freeBlock.address;
        return 0;
    }


    public void Defragment() {
        if (type == 2) {
            BSTree defrag = new BSTree(), temp, aux_temp;
            temp = (BSTree) this.freeBlk.getFirst();
            if (temp == null) {
                return;
            }

            while (temp != null) {
                defrag.Insert(temp.address, temp.size, temp.address);
                temp = temp.getNext();
            }

            this.freeBlk = new BSTree();

            temp = defrag.getFirst();
            aux_temp = temp.getNext();
            if (aux_temp == null) {
                this.freeBlk.Insert(temp.address, temp.size, temp.size);
                return;
            }

            while (aux_temp != null) {
                if ((temp.address + temp.size) != aux_temp.address) {
                    this.freeBlk.Insert(temp.address, temp.size, temp.size);
                    temp = aux_temp;
                    aux_temp = aux_temp.getNext();
                } else {
                    temp.size += aux_temp.size;
                    defrag.Delete(aux_temp);
                    aux_temp = temp.getNext();
                }
            }

            this.freeBlk.Insert(temp.address, temp.size, temp.size);
        } else if (type == 3) {

            AVLTree defrag = new AVLTree(), temp, aux_temp;
            temp = (AVLTree) this.freeBlk.getFirst();
            if (temp == null) {
                return;
            }

            while (temp != null) {
                defrag.Insert(temp.address, temp.size, temp.address);
                temp = temp.getNext();
            }

            this.freeBlk = new AVLTree();

            temp = defrag.getFirst();
            aux_temp = temp.getNext();
            if (aux_temp == null) {
                this.freeBlk.Insert(temp.address, temp.size, temp.size);
                return;
            }

            while (aux_temp != null) {
                if ((temp.address + temp.size) != aux_temp.address) {
                    this.freeBlk.Insert(temp.address, temp.size, temp.size);
                    temp = aux_temp;
                    aux_temp = aux_temp.getNext();
                } else {
                    temp.size += aux_temp.size;
                    defrag.Delete(aux_temp);
                    aux_temp = temp.getNext();
                }
            }

            this.freeBlk.Insert(temp.address, temp.size, temp.size);
        }

    }

}