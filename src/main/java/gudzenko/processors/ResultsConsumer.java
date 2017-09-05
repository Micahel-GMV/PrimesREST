package gudzenko.processors;

import gudzenko.taskset.ResultSet;
import gudzenko.taskset.TaskSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultsConsumer extends Thread {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ResultsConsumer.class);

    private ResultSet results;
    private TaskSet tasks;
    private long primesCount;
    private long notPrimesCount;
    private long errorsCount;
    private long startTime;
    private long reportPeriod;

    public ResultsConsumer(TaskSet tasks, ResultSet results, long reportPeriod){
        this.tasks = tasks;
        this.results = results;
        this.reportPeriod = reportPeriod;
        startTime = System.currentTimeMillis();
    }

    public long getResultsCount(){
        return primesCount+notPrimesCount+errorsCount;
    }

    @Override
    public void run(){
        long prevTime = startTime, prevCount = 0, curCount, curTime, difTime, difCount;
        while (!interrupted()){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error("Sleep in consumer interrupted.");
            }
            switch (results.get()){
                case PRIME: primesCount++;
                    break;
                case NOTPRIME: notPrimesCount++;
                default: errorsCount++;
            }
            curCount = getResultsCount();
            difCount = curCount - prevCount;
            if (difCount >= reportPeriod) {
                curTime = System.currentTimeMillis();
                difTime = curTime - prevTime;
                System.out.println(difCount + " results obtained at average speed of " +
                        1000*(double)difCount/difTime + " ops/sec.");
                prevTime = curTime;
                prevCount = curCount;
            }
        }
    }
}
