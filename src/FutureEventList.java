public interface FutureEventList {

    /**
     * Remove and return the Event at the front of the list.
     * <br>
     * The FutureEventList is sorted by arrival time, so the Event at the front of the list will be the one with the
     * smallest arrival time.
     *
     * @return the Event at the front of the list
     */
    public Event removeFirst();

    /**
     * Remove the Event e from the list, if it exists.
     *
     * @param e an Event to remove from the list
     * @return true if Event present in the list, false otherwise
     */
    public boolean remove(Event e);

    /**
     * Insert an Event into the list.
     * <br>
     * The FutureEventList maintains an ordering of Events based on arrival time.
     *
     * @param e an Event to insert into the list
     */
    public void insert(Event e);

    /**
     * Return the list size (number of Events in the list).
     *
     * @return the number of Events in the list
     */
    public int size();

    /**
     * Return the list capacity (total number of Events the list can store before having to resize it).
     *
     * @return total number of Events the list can store before having to resize it
     */
    public int capacity();

    /**
     * Return the current simulation time (arrival time of last Event)
     *
     * @return the current simulation time
     */
    public int getSimulationTime();
}
