package gudzenko.processors;

import gudzenko.taskset.ITaskSet;
import gudzenko.taskset.TaskSet;

import javax.sound.midi.Soundbank;

public class Statistics extends Thread {

    private ITaskSet tasks;
    private long startTime;
    private long reportPeriod;
    private long previousReported = 0;

    public Statistics(ITaskSet tasks, long reportPeriod){
        this.tasks = tasks;
        this.reportPeriod = reportPeriod;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void run(){ // This thread will run continuously and make big load on CPU. I really don`t like implementation
                    // like this. Should use wait-notify. But this way is faster to develop and I`m still not familiar
                        //with that. Part to improove.
        long taskCount, diff, timeDiff, curTime, previousTime = startTime;
        while (!isInterrupted()){
            taskCount = tasks.getTaskCount();
            diff = taskCount - previousReported;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Statistics sleep interrupted.");
                e.printStackTrace();
            }
            if (diff >= reportPeriod){
                previousReported = taskCount;
                curTime = System.currentTimeMillis();
                timeDiff = curTime - previousTime;
                System.out.print(diff + " tasks were sent for processing in " + timeDiff + " millis. ");
                System.out.println("Performance is " + ((double)diff/timeDiff) + " tasks/milisec.");
            }
        }
    }
}
