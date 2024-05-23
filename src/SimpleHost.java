public class SimpleHost extends Host{
    private int destAddr;
    private int interval;

    private int duration;

    public SimpleHost(){

    }
    @Override
    protected void receive(Message msg) {

        /*
            Upon message .handle() method being called
            Will determine whether message object contains request string

            If request print out message that host has sent ping request to host
            Create new message for response
            Add new response message to fel

            If not request print out message that host has sent ping response
         */

        if (msg.message == "Request"){

            System.out.println(msg.getMessage("Request"));

            Message newMsg = new Message(msg.getDestAddress(), msg.getSrcAddress(), "Response");
            newMsg.setInsertionTime(getCurrentTime());
            sendToNeighbor(newMsg);
        }

        else{
            System.out.println(msg.getMessage("Response"));
        }

    }

    @Override
    protected void timerExpired(int eventId) {

        /*
            If current sim time is greater than the duration then cancel future timers

            If current sim time is less than the duration start timer expiration process
            Print current time followed by which host is sending ping to other host

            Create new timer object for next timer interval (gets added to fel)

            Create new message object that will be used to send messages across interval
            Message object will contain src host, dest host, and string to distinguish ping request
            Send message to neighbor (gets added to fel)

            If current sim time is equal to duration stop sending pings
         */

        if (getCurrentTime() > duration){
            cancelTimer(eventId);
        }
        else if (getCurrentTime() < duration ){

            System.out.println("[" + getCurrentTime()  + "ts] Host " + getHostAddress() + ": Sent ping to host " + destAddr);

            newTimer(interval);

            Message newMessage = new Message(getHostAddress(), this.destAddr, "Request");
            newMessage.setInsertionTime(getCurrentTime());
            sendToNeighbor(newMessage);
        }

        else{
            System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + " Stopped sending pings");
        }

    }

    @Override
    protected void timerCancelled(int eventId) {

    }

    public void sendPings(int destAddr, int interval, int duration){

            this.destAddr = destAddr;
            this.interval = interval;
            this.duration = duration;

            /*
                Adds interval object to fel to advance sim time when removed
                Adds duration object to fel
             */
            newTimer(interval);
            newTimer(duration);
    }
}
