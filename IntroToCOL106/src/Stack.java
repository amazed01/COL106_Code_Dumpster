public class Stack {
    private Object[] stack;
    private int top, size;

    public Stack() {
        this.size = 2;
        this.stack = new Object[size];
        this.top = -1;
    }

    public void push(Object obj) {
        if (top + 1 == size) {
            // Grow size of Q array
            Object[] auxiliary = new Object[size*2];

            // Copy currently stored elements to bigger stack array
            for (int i = 0; i < size; i++) {
                auxiliary[i] = stack[i];
                stack[i] = null;
            }

            // Reassign bigger stack array to object's stack array field pile
            stack = auxiliary;
            size = size * 2;
        }

        top += 1;
        stack[top] = obj;
    }

    public void pop() {
        if (top < 0) {
            System.out.println("No element to pop. It is an empty Stack!!!");
        }
        else {
            stack[top] = null;
            top -= 1;
        }
    }

    public void printStackArray() {
        for (int i = 0; i <= top; i++) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }

    public Object getTop() {
        if (top < 0) {
            System.out.println("Empty Stack!!!");
            return null;
        }
        return stack[top];
    }

    public Object[] getStack() {
        return stack;
    }

    public int getSize() {
        return top + 1;
    }

    public int getStackCapacity() {
        return size;
    }
}


/*
Test Statements used for testing Stack.java
        Stack test = new Stack();
        test.push(1);
        test.push(2);
        test.printStackArray();
        System.out.println(test.getStackCapacity() + "  " + test.getTop());
        test.pop();
        test.printStackArray();
        System.out.println(test.getStackCapacity() + "  " + test.getTop());
        test.push(2);
        test.push(3);
        test.push(4);
        System.out.println(test.getStackCapacity() + "  " + test.getTop());
        test.pop();
        test.printStackArray();
        System.out.println(test.getStackCapacity() + "  " + test.getTop());

        test.push(5);
        test.printStackArray();
        System.out.println(test.getStackCapacity() + "  " + test.getTop());

        test.pop();        test.pop();        test.pop();        test.pop();
        test.printStackArray();
        System.out.println(test.getStackCapacity());

        test.pop();//test.printStackArray();
*/
