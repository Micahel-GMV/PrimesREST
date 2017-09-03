package gudzenko.disruptor;

import gudzenko.taskset.ITaskSet;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskGeneratorDisruptor extends Thread {

    ITaskSet tasks;
    LMAXWriter lmaxWriter;

    final Random random = new Random();

    public TaskGeneratorDisruptor(ITaskSet tasks, LMAXWriter lmaxWriter){
        this.lmaxWriter = lmaxWriter;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        System.out.println("Task generator started.");
        while (!isInterrupted()) {
            int i = Math.abs(random.nextInt());
            lmaxWriter.submitMessage(new AtomicInteger(i));
            //System.out.println(i + " added to tasks.");
        }
        System.out.println("Task generator interrupted.");
    }
}
