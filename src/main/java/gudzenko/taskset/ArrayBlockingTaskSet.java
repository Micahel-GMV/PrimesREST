package gudzenko.taskset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayBlockingTaskSet implements TaskSet {

    private static Logger LOGGER = LoggerFactory.getLogger(ArrayBlockingTaskSet.class);

    private int taskCapacity;

    private ArrayBlockingQueue<Integer> tasks;

    public ArrayBlockingTaskSet(int capacity){
        this.taskCapacity = capacity;
        tasks = new ArrayBlockingQueue<Integer>(taskCapacity);
    }

    public void add(Integer value) {
        try {
            tasks.put(value);
            //System.out.println(value + " was put to tasks queue. " + taskCount + " tasks were put." );
        } catch (InterruptedException e) {
            LOGGER.error("Add task interrupted.");
        }
    }

    public Integer get() {
        Integer result;
        try {
            result = tasks.poll(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("Get task interrupted.");
            result = new Integer(-1);
        }
        return result;
    }
}
