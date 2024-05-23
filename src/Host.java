/**
 * Base class for network hosts.
 * <br>
 * Contains functionality to add neighbor hosts, send Message events to neighbor hosts, and create and manage Timer
 * events.
 */
import org.w3c.dom.events.EventException;

import java.util.HashMap;
import java.util.Map;

public abstract class Host {

    private FutureEventList fel;
    private final Map<Integer, Timer> activeTimers;

    private final Map<Integer, Host> neighbor;
    private final Map<Integer, Integer> neighborDistance;

    private int address;

    protected Host() {
        this.activeTimers = new HashMap<>();
        this.neighbor = new HashMap<>();
        this.neighborDistance = new HashMap<>();
    }

    /**
     * This is called when a host receives a Message event from a neighboring host.
     *
     * @param msg the Message event received
     */
    protected abstract void receive(Message msg);

    /**
     * This is called after a Timer event expires, and enables you to write code to do something upon timer
     * expiry.  A timer expires when the duration set for the timer is reached.
     *
     * @param eventId the event id of the Timer event which expired
     */
    protected abstract void timerExpired(int eventId);

    /**
     * This is called after a Timer event is cancelled, and enables you to write code to do something upon timer
     * cancellation.
     *
     * @param eventId the event id of the Timer event which was cancelled
     */
    protected abstract void timerCancelled(int eventId);

    /**
     * Assign this host's address and provide a reference to the FutureEventList object used in the simulation.
     *
     * @param address the address of this host
     * @param fel the FutureEventList object
     */
    public void setHostParameters(int address, FutureEventList fel) {
        this.address = address;
        this.fel = fel;
    }

    /**
     * Return the address of this Host object.  This is the address assigned using setHostParameters.
     *
     * @return this host's address
     */
    public int getHostAddress() {
        return this.address;
    }

    /**
     * Return the current simulation time, directly from the future event list.
     *
     * @return the current simulation time
     */
    public int getCurrentTime() {
        return this.fel.getSimulationTime();
    }

    /**
     * Adds a new neighboring host to this host.  A neighbor is a host directly connected to this host, with the
     * specified distance between them.  The further apart two hosts are, the longer it takes for a message to traverse
     * from one to the other.
     *
     * @param neighbor the neighboring host
     * @param distance the distance to the neighboring host
     */
    public void addNeighbor(Host neighbor, int distance) {
        // for simplicity, assume that 1 distance = 1 simtime
        this.neighborDistance.put(neighbor.address, distance);
        this.neighbor.put(neighbor.address, neighbor);
    }

    /**
     * Sends a Message object to the neighbor specified by the Message object's destination address.
     *
     * @param msg the Message object to send
     */
    protected void sendToNeighbor(Message msg) {
        Host neighbor = this.neighbor.get(msg.getDestAddress());
        if (neighbor == null) {
            //throw new EventException("sendToNeighbor: destination address of message is unknown: " + msg.getDestAddress());
        }

        int distance = this.neighborDistance.get(msg.getDestAddress());
        msg.setNextHop(neighbor, distance);  // next hop is neighbor host we will send to

        this.fel.insert(msg);
    }

    /**
     * Create a new Timer event, managed by this class.  What you receive from this method is the event id of the timer,
     * not the Timer object.  When a timer expires or is cancelled, a callback method will be invoked (see below) with
     * the timer's event id passed to it, so you'll know which timer expired or was cancelled.
     *
     * @param duration length of time before the timer expires
     * @return event id of the timer
     */
    protected int newTimer(int duration) {
        Timer timer = new Timer(duration, this);
        this.fel.insert(timer);

        this.activeTimers.put(timer.getId(), timer);

        return timer.getId();
    }

    /**
     * Cancels the timer given its event id.
     *
     * @param timerId the event id of the timer
     */
    protected void cancelTimer(int timerId)  {
        Timer timer = this.activeTimers.get(timerId);
        if (timer == null) {
            //throw new EventException("cancelTimer: attempted to cancel Timer event which does not exist: " + timerId);
        }

        this.fel.remove(timer);
    }

    /**
     * Called when a Timer event's cancel method is invoked.  Performs clean-up, then calls the timerCancelled method.
     *
     * @param timer the Timer object that was cancelled
     */
    protected void onTimerCancellation(Timer timer) {
        Timer tObj = this.activeTimers.remove(timer.getId());
        if (tObj == null) {
            //throw new EventException("onTimerCancellation: attempted to remove Timer event which does not exist: " + timer.getId());
        }
        this.timerCancelled(timer.getId());
    }

    /**
     * Called when a Timer event's handle method is invoked.  Performs clean-up, then calls the timerExpired method.
     * <br>
     * Note that when a Timer event expires, it is removed.  In other words, timers are not recurring (they only expire
     * once).  If you want a recurring timer, you'll need to create a new timer each time the current one expires.
     *
     * @param timer the Timer object that expired
     */
    protected void onTimerExpiry(Timer timer) {
        Timer tObj = this.activeTimers.remove(timer.getId());
        if (tObj == null) {
            //throw new EventException("onTimerExpiry: attempted to remove Timer event which does not exist: " + timer.getId());
        }
        this.timerExpired(timer.getId());
    }

}
