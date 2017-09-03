package gudzenko.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

public class WriteEvent {

    private AtomicInteger message;

    public void set(AtomicInteger message){
        this.message = message;
    }

    public AtomicInteger get() {
        return this.message;
    }
}