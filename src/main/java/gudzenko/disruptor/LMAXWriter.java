package gudzenko.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import gudzenko.taskset.ITaskSet;
import gudzenko.taskset.TaskSet;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LMAXWriter {

    private ITaskSet tasks;

    public LMAXWriter(ITaskSet tasks){
        this.tasks = tasks;
    }

    private Disruptor<WriteEvent> disruptor;
    private WriteEventProducer writeEventProducer;

    private int ringBufferSize;

    public void setRingBufferSize(int ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
    }

    /**
     * Initialize the disruptor engine.
     * */
    public void init() {
        // create a thread pool executor to be used by disruptor
        Executor executor = Executors.newCachedThreadPool();

        // initialize our event factory
        WriteEventFactory factory = new WriteEventFactory();

        if (ringBufferSize == 0) {
            ringBufferSize = 1024;
        }

        // ring buffer size always has to be the power of 2.
        // so if it is not, make it equal to the nearest integer.
        double power = Math.log(ringBufferSize) / Math.log(2);
        if (power % 1 != 0) {
            power = Math.ceil(power);
            ringBufferSize = (int) Math.pow(2, power);
        }

        // initialize our event handler.
        WriteEventHandler handler = new WriteEventHandler(tasks);

        // initialize the disruptor
        disruptor = new Disruptor<WriteEvent>(factory, ringBufferSize, executor);
        disruptor.handleEventsWith(handler);

        // set our custom exception handler
        ExceptionHandler exceptionHandler = new ExceptionHandler() {
            public void handleEventException(Throwable throwable, long l, Object o) {

            }

            public void handleOnStartException(Throwable throwable) {

            }

            public void handleOnShutdownException(Throwable throwable) {

            }
        };
        disruptor.handleExceptionsFor(handler).with(exceptionHandler);

        // start the disruptor and get the generated ring buffer instance
        disruptor.start();

        // initialize the event producer to submit messages
        writeEventProducer = new WriteEventProducer(disruptor);

    }

    public void close() {
        if (disruptor != null) {
            disruptor.halt();
            disruptor.shutdown();
        }
    }

    public void submitMessage(AtomicInteger message) {
        if (writeEventProducer != null ) {
            // publish the messages via event producer
            writeEventProducer.onData(message);
        }
    }
}