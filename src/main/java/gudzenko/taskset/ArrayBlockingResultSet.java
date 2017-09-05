package gudzenko.taskset;

import gudzenko.rest.TrancieverResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayBlockingResultSet implements ResultSet {

    private static Logger LOGGER = LoggerFactory.getLogger(ArrayBlockingResultSet.class);

    private int taskCapacity;

    private ArrayBlockingQueue<TrancieverResults> results;

    public ArrayBlockingResultSet(int capacity){
        this.taskCapacity = capacity;
        results = new ArrayBlockingQueue<TrancieverResults>(taskCapacity);
    }

    public void add(TrancieverResults value) {
        try {
            results.put(value);
            //System.out.println(value + " was put to tasks queue. " + taskCount + " tasks were put." );
        } catch (InterruptedException e) {
            LOGGER.error("Add task interrupted.");
        }
    }

    public TrancieverResults get() {
        TrancieverResults result;
        try {
            result = results.poll(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("Get task interrupted.");
            result = TrancieverResults.ERROR;
        }
        return result;
    }
}
