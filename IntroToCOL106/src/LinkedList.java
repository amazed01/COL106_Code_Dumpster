public class LinkedList<T> {
    private Node<T> head = new Node<T>();
    private Node<T> tail = new Node<T>(); //Sentinel Nodes,  EMPTY LinkedList Represented as { head <==> tail }

    public LinkedList() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    public void insertAtHead(T data) {
        this.insertAtHead(new Node<T>(data));
    }

    public void insertAtHead(Node<T> obj) {
        obj.setNext(head.getNext());
        obj.getNext().setPrev(obj);

        obj.setPrev(head);
        head.setNext(obj);
    }

    public void insertAtTail(T data) {
        this.insertAtTail(new Node<T>(data));
    }

    public void insertAtTail(Node<T> obj) {
        obj.setPrev(tail.getPrev());
        obj.getPrev().setNext(obj);

        obj.setNext(tail);
        tail.setPrev(obj);
    }

    public Node<T> findNode(T data) {
        return this.findNode(new Node<T>(data));
    }

    public Node<T> findNode(Node<T> obj) {
        Node<T> current = head.getNext();
        boolean flag = false;

        while (current != tail ) {
            if (current.getData() == obj.getData()) {
                flag = true;
                break;
            } else {
                current = current.getNext();
            }
        }

        if (flag) {
            return current;
        } else {
            return null;
        }
    }

    public boolean isNodeInLinkedList(T data) {
        return this.isNodeInLinkedList(new Node<T>(data));
    }

    public boolean isNodeInLinkedList(Node<T> obj) {
        Node<T> current = head.getNext();
        boolean flag = false;

        while (current != tail ) {
            if (current.getData() == obj.getData()) {
                flag = true;
                break;
            } else {
                current = current.getNext();
            }
        }

        return flag;
    }

    private void insertNode(Node<T> obj, Node<T> search, boolean position) {
        Node<T> auxiliary = this.findNode(search);
        if (auxiliary == null) {
            System.out.println("Node where element need to be inserted NOT FOUND");
            return;
        }

        // position ? Ahead <==> true :: Before <==> false
        if (position) {
            obj.setNext(auxiliary.getNext());
            obj.getNext().setPrev(obj);
            obj.setPrev(auxiliary);
            auxiliary.setNext(obj);
        } else {
            obj.setPrev(auxiliary.getPrev());
            obj.getPrev().setNext(obj);
            obj.setNext(auxiliary);
            auxiliary.setPrev(obj);
        }
    }

    public void insertAheadNode(Node<T> obj, Node<T> search) {
        this.insertNode(obj, search, true);
    }

    public void insertAheadNode(T obj, T search) {
        this.insertNode(new Node<T>(obj), new Node<T>(search), true);
    }

    public void insertBeforeNode(Node<T> obj, Node<T> search) {
        this.insertNode(obj, search, false);
    }

    public void insertBeforeNode(T obj, T search) {
        this.insertNode(new Node<T>(obj), new Node<T>(search), false);
    }

    public void deleteNode(Node<T> obj) {
        Node<T> auxiliary = this.findNode(obj);
        if (auxiliary == null) {
            System.out.println("Given Node is not in list !!");
            return;
        }

        auxiliary.getPrev().setNext(auxiliary.getNext());
        auxiliary.getNext().setPrev(auxiliary.getPrev());
        System.out.println("Node Deleted");
        auxiliary.setNext(null);
        auxiliary.setData(null);
        auxiliary.setPrev(null);
    }

    public void deleteNode(T data) {
        this.deleteNode(new Node<T>(data));
    }

    public void deleteFromHead() {
        if (head.getNext() != tail) {
            Node<T> auxiliary = head.getNext();
            head.setNext(auxiliary.getNext());
            head.getNext().setPrev(head);
            auxiliary.setPrev(null);
            auxiliary.setNext(null);
            auxiliary.setData(null);
        }
    }

    public void deleteFromTail() {
        if (tail.getPrev() != head) {
            Node<T> auxiliary = tail.getPrev();
            tail.setPrev(auxiliary.getPrev());
            tail.getPrev().setNext(tail);
            auxiliary.setPrev(null);
            auxiliary.setNext(null);
            auxiliary.setData(null);
        }
    }

    public void printLinkedList() {
        Node<T> current = head.getNext();
        while (current != tail ) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
}

/* TestCode for LinkedList Class

        LinkedList li = new LinkedList();
        li.insertAtHead(100);
        li.insertAtTail(-100);
        li.insertAtHead(50);
        System.out.println(li.findNode(-100).getPrev().getData());
        System.out.println(li.isNodeInLinkedList(50));
        li.printLinkedList();
        li.insertAheadNode(69, 100);
        li.deleteFromTail();
        li.printLinkedList();
        li.deleteNode(-100);
        li.printLinkedList();
        li.deleteNode(50);
        li.printLinkedList();
*/