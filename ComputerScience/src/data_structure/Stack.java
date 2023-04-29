package data_structure;


public class Stack<T> extends DataStructure<T> {

    private int top = -1;
    private int size;
    private T[] elements;


    public Stack(int size) {
        if(size < 0) {
            return;
        }
        this.size = size;
        elements = (T[]) new Object[size];
    }

    public Stack() {
        this.size = 10;
        elements = (T[]) new Object[size];
    }

    public T pop() {
        T obj = peek(getTop());
        remove();
        return obj;
    }

    private void remove() {
        if(top == -1) {
            System.out.println("삭제할 수 있는 원소가 존재하지 않습니다.");
        } else {
            elements[top] = null;
            top--;
        }
    }

    private T peek(int index) {
        try{
            return elements[index];
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return null;
    }

    public void push(T item) {
        if(size == top+1) {
            System.out.println("원소를 더 이상 추가할 수 없습니다.");
            return;
        }
        elements[top+1] = item;
        top++;

    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int getSize() {
        return this.size;
    }

    public int getTop() {
        return this.top;
    }



}
