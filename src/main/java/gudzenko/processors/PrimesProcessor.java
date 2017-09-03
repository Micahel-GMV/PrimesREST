package gudzenko.processors;

import gudzenko.taskset.TaskSet;

public class PrimesProcessor extends Thread {
    private TaskSet tasks;
    public PrimesProcessor(TaskSet tasks){
        this.tasks = tasks;
    }
    @Override
    public void run(){
        while (!isInterrupted()){
            tasks.getPrime();
        }
    }
}
