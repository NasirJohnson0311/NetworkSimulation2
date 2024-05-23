/**
 * Timer event.
 * <br>
 * Timers are events which are handled after a specific period of time has passed (the duration).  When a timer's
 * duration is up, the timer expires (is handled).
 */
public class Timer extends Event {

    private final Host host;
    private final int duration;

    public Timer(int duration, Host host) {
        super();

        this.host = host;
        this.duration = duration;
    }

    @Override
    public void setInsertionTime(int currentTime) {
        this.insertionTime = currentTime;
        this.arrivalTime = currentTime + this.duration;
    }

    @Override
    public void cancel() {
        this.host.onTimerCancellation(this);
    }

    @Override
    public void handle() {
        this.host.onTimerExpiry(this);
    }
}
