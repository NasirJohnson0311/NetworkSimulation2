public class Node {

    Event e;
    Node prev;
    Node next;

    public Node(Event e){
        this.e = e;
        this.next = null;
        this.prev = null;
    }

    public int getInsertionTime(){
        return e.getInsertionTime();
    }
    public int getArrivalTime(){
        return e.getArrivalTime();
    }
    public int getNodeId(){
        return e.getId();
    }
    public void setNext(Node nextNode){
        this.next = nextNode;
    }
    public void setPrev(Node prevNode){
        this.prev = prevNode;
    }
    public Node getPrev(){
        return this.prev;
    }

}

