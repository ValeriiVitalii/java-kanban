package history;

public class Node<E> {
E item;
Node<E> next;
Node<E> prev;

    public Node(Node<E> prev, E element, Node<E> next) {
        item = element;
        this.prev = prev;
        this.next = next;
    }

    public Node<E> getNext() {
        if (this.next != null) {
            return next;
        }
            return new Node<>(null, null, null);
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        if (this.prev != null) {
            return prev;
        }
        return new Node<>(null, null, null);
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }
}

