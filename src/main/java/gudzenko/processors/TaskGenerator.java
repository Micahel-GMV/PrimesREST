package gudzenko.processors;

import gudzenko.taskset.ITaskSet;

import java.util.Random;

public class TaskGenerator extends Thread {

    ITaskSet tasks;
    final Random random = new Random();

    public TaskGenerator(ITaskSet tasks){
        this.tasks = tasks;
    }

    @Override
    public void run() {
        System.out.println("Task generator started.");
        while (!isInterrupted()) {
            int i = Math.abs(random.nextInt());
            tasks.addTask(i);
            //System.out.println(i + " added to tasks.");
        }
        System.out.println("Task generator interrupted.");
    }
}
