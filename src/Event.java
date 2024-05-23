/**
 * Base class for Events.
 * Manages event IDs, assigning a unique event ID per new event created.  Provides getters for event ID, insertion time,
 * and arrival time.
 */
public abstract class Event {

    private static int nextId = 0;

    private final int id;

    /**
     * Insertion time into the future event list of this Event.  Must be zero or greater.
     */
    protected int insertionTime;

    /**
     * Arrival time of this Event.  This is the simulation time in which the Event is removed from the future event list
     * and handled.  Must be zero or greater, and cannot be less than the insertion time.
     */
    protected int arrivalTime;

    protected Event() {
        this.insertionTime = -1;
        this.arrivalTime = -1;

        this.id = Event.nextId++;
    }

    /**
     * Returns the Event's ID.
     * <br>
     * Each Event has an unique id
     *
     * @return the Event's arrival time
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the insertion time of this Event.
     * <br>
     * Insertion time is the simulation time in which this Event is inserted into the future event list.  This should
     * not change once set.
     *
     * @return the Event's arrival time
     */
    public int getInsertionTime() {
        return this.insertionTime;
    }

    /**
     * Returns the arrival time of this Event.
     * <br>
     * Arrival time represents the time in which an Event is removed from the future event list for it to be
     * executed or handled.  For example, if this Event represents a network packet, then the arrival time
     * represents the time in which the network packet 'arrives' at the destination host.  If the Event is a Timer,
     * then the arrival time is the absolute time in which the timer expires.
     * <br>
     * This arrival time is what the future event list uses for sort order when inserting an Event, therefore it should
     * not change.
     *
     * @return the Event's arrival time
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * Sets the insertion time and arrival time for this Event.
     * <br>
     * It is assumed that any information needed to compute the arrival time from the insertion time is passed into
     * the Event's constructor (for example a duration).  This method should be called from within the FutureEventList's
     * insert method.
     *
     * @param currentTime the current simulation time
     */
    public abstract void setInsertionTime(int currentTime);

    /**
     * Cancel the Event.
     * <br>
     * This occurs after the Event has been removed from the future event list, probably before the arrival time has
     * been reached.
     */
    public abstract void cancel();

    /**
     * Handle (or execute) the Event.
     * <br>
     * This occurs after the Event has been removed from the future event list, due to the arrival time being reached.
     * For example, if this Event represents a network message, then calling handle() will 'process' the message at the
     * destination host.  If the Event is a Timer, then this will execute whatever needs to be done upon timer expiry.
     */
    public abstract void handle();

    @Override
    public String toString() {
        return "[Event " + this.getId() + " (insertion time: " + this.getInsertionTime() + ", arrival time: " +
                this.getArrivalTime() + ")]";
    }

}
