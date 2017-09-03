package gudzenko.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.atomic.AtomicInteger;

public class WriteEventProducer {

    private final Disruptor<WriteEvent> disruptor;

    public WriteEventProducer(Disruptor<WriteEvent> disruptor) {
        this.disruptor = disruptor;
    }

    private static final EventTranslatorOneArg<WriteEvent, AtomicInteger> TRANSLATOR_ONE_ARG =
            new EventTranslatorOneArg<WriteEvent, AtomicInteger>() {
                public void translateTo(WriteEvent writeEvent, long sequence, AtomicInteger message) {
                    writeEvent.set(message);
                }
            };

    public void onData(AtomicInteger message) {
        // publish the message to disruptor
        disruptor.publishEvent(TRANSLATOR_ONE_ARG, message);
    }
}