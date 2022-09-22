package History;

public class CustomLinkedList<E> {
    public Node<E> node;
    public Node<E> head;
    public Node<E> tail;
    private int size = 0;

    public Node<E> linkLast(E task) {
            node = new Node<>(null, task, head);
            head.setPrev(node);
            head = node;
            size++;
            return node;
    }
    public Node<E> linkFirst(E task) {
        node = new Node<>(null, task, null);
        tail = node;
        head = node;
        size++;
        return node;
    }
    public Node<E> getTail() {
        return tail;
    }

    public void setTail(Node<E> tail) {
        this.tail = tail;
    }

    public Node<E> getNode() {
        return node;
    }

    public void setNode(Node<E> node) {
        this.node = node;
    }
}




