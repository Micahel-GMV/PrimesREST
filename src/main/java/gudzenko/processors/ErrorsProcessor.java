package gudzenko.processors;

import gudzenko.taskset.TaskSet;

public class ErrorsProcessor extends Thread {
    private TaskSet tasks;
    public ErrorsProcessor(TaskSet tasks){
        this.tasks = tasks;
    }
    @Override
    public void run(){
        while (!isInterrupted()){
            tasks.getError();//Here I just throw out error value `cause dummy tranciever will return error each time
                                // we send this number on processing. Otherwise we`ll make tassks.addTask on this number.
        }
    }
}