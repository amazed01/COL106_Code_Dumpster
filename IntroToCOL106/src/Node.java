public class Node<T> {
    private Node<T> next, prev;
    private T data;

    // Node Class Interface Methods

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    // Constructors of Node Class
    public Node(T data, Node<T> next, Node<T> prev) {
        this.next = next;
        this.prev = prev;
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this(data, next, null);
    }

    public Node(T data) {
        this(data, null, null);
    }

    public Node() {
        this(null,null,null);
    }
    // Constructors of Node Class END
}
