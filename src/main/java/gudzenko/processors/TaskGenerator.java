package gudzenko.processors;

import gudzenko.taskset.ArrayBlockingTaskSet;
import gudzenko.taskset.TaskSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskGenerator extends Thread {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TaskGenerator.class);

    TaskSet tasks;
    final Random random = new Random();

    public TaskGenerator(TaskSet tasks){
        this.tasks = tasks;
    }

    @Override
    public void run() {
        System.out.println("Task generator started.");
        while (!isInterrupted()) {
            int i = Math.abs(random.nextInt());
            tasks.add(new Integer(i));
            try {
                sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error("Sleep in taskgen interrupted.");
            }
            //System.out.println(i + " added to tasks.");
        }
        LOGGER.error("Task generator interrupted.");
    }
}
