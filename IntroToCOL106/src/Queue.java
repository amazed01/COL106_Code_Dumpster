public class Queue {
    private Object[] queue;
    private int front, rear, size;

    public Queue(int size) {
        this.size = size;
        this.queue = new Object[size];
        this.front = 0;
        this.rear = 0;
    }

    public Queue() {
        this(4);
    }

    public int getQueueCapacity() {
        return size;
    }

    public int getSize() {
        return (size + rear - front) % size;
    }

    public boolean isEmptyQueue() {
        return front == rear;
    }

    public void printQueueArray() {
        for (int i = front; i != rear; i = (i+1) % size) {
            System.out.print(queue[i] + " ");
        }
        System.out.println();
    }

    public void enqueue(Object obj) {
        // If size of queue is about to be filled (size-1 elements before insertion), then regrow the size of queue
        if (this.getSize() == size - 1) {
            Object[] auxiliary = new Object[2*size];
            int i = 0;

            // while current queue is not empty, keep on reassigning the elements to auxiliary array
            while (front != rear) {
                auxiliary[i] = queue[front];
                ++i;
                queue[front] = null;
                front = (front + 1) % size;
            }

            // Update queue fields after regrowth of size and reassigning of objects
            size = size*2; front = 0; rear = i;
            queue = auxiliary;
        }

        queue[rear] = obj;
        rear = (rear + 1) % size;
    }

    public void dequeue() {
        if (this.isEmptyQueue()) {
            System.out.println("Empty Queue");
        }
        else {
        queue[front] = null;
        front = (front + 1) % size;

        // Write a function to shrink size of array in case no of elements are less than half of  queue capacity
        }
    }

    public Object getFront() {
        if (this.isEmptyQueue()) {
            System.out.println("Empty Queue");
            return null;
        }
        return queue[front];
    }
}

/*
Test Code for Queue Class
        Queue test = new Queue();
        System.out.println(test);
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.printQueueArray();
        System.out.println(test.getSize());
        test.enqueue(4);
        test.printQueueArray();
        System.out.println(test.getQueueCapacity());
        test.dequeue();
        test.dequeue();
        test.printQueueArray();
        System.out.println(test.getQueueCapacity() + "hhhh" + test.getSize());
        System.out.println(test.getFront());
        test.dequeue();
        test.dequeue();
        test.printQueueArray();
        System.out.println(test.getQueueCapacity() + "hhhh" + test.getSize());
        System.out.println(test.getFront());
        test.dequeue();
*/
