public class LinkedList<T extends Comparable<T>> {
    private class Node {
        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            next = null;
        }
    }

    private Node head;
    private Node current;

    public String display() {
        StringBuffer sb = new StringBuffer();
        Node temp = this.head;
        while (temp != null) {
            sb.append(temp.data + ", ");
            temp = temp.next;
        }
        return sb.toString();
    }

    public int length() {
        Node temp = this.head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public LinkedList() {
        head = null;
        current = null;
    }

    public void reset() {
        current = head;
    }

    public boolean hasNext() {
        return current != null;
    }

    public void next() {
        current = current.next;
    }

    public T getCurrentData() {
        return current.data;
    }

    /* Adding at the front of the list */
    public LinkedList<T> addFirst(T data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;

        return this;
    }

    public LinkedList<T> addLast(T data) {
        Node newNode = new Node(data);
        Node temp = this.head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
        return this;
    }

    public String toString() {
        String result = "\" ";
        Node curr = head;

        while (curr != null) {
            result += curr.data + " ";

            curr = curr.next;
        }

        return result + "\"";
    }

    public T findNode(T target) {
        Node curr = head;

        while (curr != null) {
            if (curr.data.compareTo(target) == 0) {
                return curr.data;
            }

            curr = curr.next;
        }

        return null;
    }
}