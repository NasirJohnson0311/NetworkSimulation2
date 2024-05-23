public class Message extends Event{

    private int srcAddress;
    private int destAddress;
    Host destination;
    int distBetweenSourceAndDest;
    String message;

    @Override
    public void setInsertionTime(int currentTime) {
        insertionTime = currentTime;
    }

    @Override
    public void cancel() {
        /*
            Leave empty, Messages cannot be cancelled
         */
    }

    @Override
    public void handle() {
        destination.receive(this);
    }

    public Message(int srcAddress, int destAddress, String message){

        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
        this.message = message;
    }

    public String getMessage(String message){

        /*
            Upon method being called will determine what string has been passed

            Uses switch statement to distinguish response from request
            If request return request message in retrievedMessage
            If response return response message in retrievedMessage
         */

        String retrievedMessage = null;

        switch(message){

            case "Response":
                retrievedMessage = "[" + (arrivalTime) + "ts] Host " + getDestAddress() + ": Ping response from host " + getSrcAddress()  + " (RTT = " + (distBetweenSourceAndDest * 2) + "ts)";
                break;

            case "Request":
                retrievedMessage = "[" + (arrivalTime) + "ts] Host " + getDestAddress() + ": Ping request from host " + getSrcAddress();
                break;
        }

        return retrievedMessage;
    }

    public int getSrcAddress(){
        return this.srcAddress;
    }

    public int getDestAddress(){
        return this.destAddress;
    }

    public void setNextHop(Host destination, int distBetweenSourceAndDest){
        this.destination = destination;
        this.distBetweenSourceAndDest = distBetweenSourceAndDest;
        arrivalTime = getInsertionTime() + distBetweenSourceAndDest;
    }

}
