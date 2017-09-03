package gudzenko.disruptor;

import com.lmax.disruptor.EventFactory;

public class WriteEventFactory implements EventFactory<WriteEvent> {

    public WriteEvent newInstance() {
        return new WriteEvent();
    }
}