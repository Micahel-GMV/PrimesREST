package gudzenko.processors;

import gudzenko.taskset.TaskSet;

public class NotprimesProcessor extends Thread {
    private TaskSet tasks;
    public NotprimesProcessor(TaskSet tasks){
        this.tasks = tasks;
    }
    @Override
    public void run(){
        while (!isInterrupted()){
            tasks.getNotprime();
        }
    }
}