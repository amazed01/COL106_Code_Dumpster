// CHIRAG AGGARWAL 2019EE30564
// NOTE:: I had completed A1 quite early but made some modifications after recent clarifications on Piazza dated 18 Nov
// Mainly I modified Find and Delete functions, earlier i was searching/deleting in forward and backward nodes from the node by which
// respective function is called. I have modified them as in to first get head of DLL no matter from which node the function is called.
// There is no real logic behind this modification, I just did this as i see fit, reading some "clarifications" on piazza. It was working quite fine
// earlier as well. So for now, I have gone with the modified approach, but I am not deleting what i did earlier, I have commented them out.



// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List


public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Initiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    // Time Complexity :: O(1)
    public A1List Insert(int address, int size, int key) {
        A1List insertingElement = new A1List(address, size, key);
        insertingElement.next = this.next;
        insertingElement.next.prev = insertingElement;
        insertingElement.prev = this;
        this.next = insertingElement;
        return insertingElement;
    }

    // Time Complexity :: O(n)
    public boolean Delete(Dictionary d) {
        A1List head = this.getFirst();
        if (head == null || d == null) return false;

        boolean status = false;
        A1List forward = this.next, backward = this.prev, deletingNode = null;

//        if (this.key == d.key && this == d) {
//            status =  true;
//            deletingNode = this;
//        }

        while (head.next != null && !status) {
            if (head.key == d.key && head == d) {
                status =  true;
                deletingNode = head;
                //break;
            }
            head = head.next;
        }

//        while (forward != null && forward.next != null && !status) {
//            if (forward.key == d.key && forward == d) {
//                status =  true;
//                deletingNode = forward;
//                //break;
//            }
//            forward = forward.next;
//        }
//
//        while (backward != null && backward.prev != null && !status) {
//            if (backward.key == d.key && backward == d) {
//                status =  true;
//                deletingNode = backward;
//                //break;
//            }
//            backward = backward.prev;
//        }

        if (status) {
            deletingNode.prev.next = deletingNode.next;
            deletingNode.next.prev = deletingNode.prev;
            //deletingNode = null;
        }

        return status;
    }

    // Time Complexity :: O(n)
    public A1List Find(int k, boolean exact) {
        A1List head = this.getFirst();
        if (head == null) return null; // Empty DLL returning null

//        if (this.key == k) return this;

//        A1List forward = this.next, backward = this.prev;

        if (exact) {
            while (head.next != null) {
                if (head.key == k) {
                    return head;
                }
                head = head.next;
            }

//            while (forward != null && forward.next != null) {
//                if (forward.key == k) {
//                    return forward;
//                }
//                forward = forward.next;
//            }
//
//            while (backward != null && backward.prev != null) {
//                if (backward.key == k) {
//                    return backward;
//                }
//                backward = backward.prev;
//            }

        }
        else {
            while (head.next != null) {
                if (head.key >= k) {
                    return head;
                }
                head = head.next;
            }


//            while (forward != null && forward.next != null) {
//                if (forward.key >= k) {
//                    return forward;
//                }
//                forward = forward.next;
//            }
//
//            while (backward != null && backward.prev != null) {
//                if (backward.key >= k) {
//                    return backward;
//                }
//                else {
//                    backward = backward.prev;
//                }
//            }

        }
        return null;
    }

    // Time Complexity :: O(n)
    public A1List getFirst() { // Empty DLL returns null as there is no First element in real sense
        A1List currentNode = this;

        while (currentNode.prev != null) {
            currentNode = currentNode.prev;
        }

        // currentNode has become header sentinel now
        if (currentNode.next.next == null) { // Check for empty DLL by checking for tail sentinel
            return null;
        }
        return currentNode.next;
    }

    // Time Complexity :: O(1)
    public A1List getNext() { // returns next element or null if no next element exists (Not include head,tail sentinel)
        if (this.next == null || this.next.next == null) {
            return null; // this.next checks if someone deliberately called this for tail sentinel
        }
        return this.next;
    }

    // Time Complexity :: O(n)
    public boolean sanity() {

        // Circular DLL Checking in forward part
        A1List slow = this, fast = this;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return false;
            }
        }
        // Circular DLL Checking in backward part
        A1List slowb = this;
        A1List fastb = this;
        while (fastb != null && fastb.prev != null) {
            fastb = fastb.prev.prev;
            slowb = slowb.prev;
            if (fastb == slowb) {
                return false;
            }
        }

        // If at this point CODE reaches, that means fast and fastb both are either nulls or header/tail respectively
        // Thus existence of head and tail nodes are guaranteed by this point in code.

        A1List node = this.getFirst(); // get Head of DLL (Non-Empty)
        while (node != null && node.next != null) {
            if (node.next.prev != node) { // Connectivity check invariant for DLL
                return false;
            }
            node = node.next;
        }
        return true;
    }

}


