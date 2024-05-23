public class LinkedEventList implements FutureEventList {

    public Node head;
    int currentSimulationTime = 0;
    public LinkedEventList(){
        this.head = null;
    }

    @Override
    public Event removeFirst() {

        /*
            Removes first node from fel
            Return null if null

            Else store removed event (head)
            Advance current sim time to events arrival time
            Next node will be head
         */

        if (head == null){
            return null;
        }

        Event removedEvent = head.e;
        currentSimulationTime = removedEvent.arrivalTime;
        head = head.next;

        if (head != null){
            head.prev = null;
        }

        return removedEvent;
    }

    public boolean remove(Event e){
        return false;
    }

    @Override
    public void insert(Event e) {

        e.setInsertionTime(currentSimulationTime);

        Node newNode = new Node(e);

        if (head == null || head.getArrivalTime() >= newNode.getArrivalTime()){

            newNode.next = head;

            if (head != null){

                head.prev = newNode;
            }

            head = newNode;
        }

        else{

            Node current = head;

            while (current.next != null && current.next.getArrivalTime() < newNode.getArrivalTime()){

                current = current.next;
            }

            newNode.next = current.next;

            if (current.next != null){

                current.next.prev = newNode;
            }

            current.next = newNode;
            newNode.prev = current;
        }
    }
    public int size(){

        int size = 0;
        Node newNode = head;

        if (newNode == null){
            return size;
        }

        else{
            size++;
            while (newNode.next != null){
                newNode = newNode.next;
                size++;
            }
        }

        return size;
    }

    public int capacity(){

        int capcity = 0;
        Node newNode = head;

        if (newNode == null){

            return capcity;
        }

        else{

            capcity++;
            while (newNode.next != null){

                newNode = newNode.next;
                capcity++;
            }
        }

        return capcity;
    }

    public int getSimulationTime(){
        return currentSimulationTime;
    }
}
