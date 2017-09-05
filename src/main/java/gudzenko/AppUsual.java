package gudzenko;

import gudzenko.processors.*;
import gudzenko.rest.TaskTranciever;
import gudzenko.taskset.ArrayBlockingResultSet;
import gudzenko.taskset.ArrayBlockingTaskSet;

import gudzenko.taskset.ResultSet;
import gudzenko.taskset.TaskSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppUsual {

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AppUsual.class);

    public static void main(String[] args) {
        final int MAX_TRANCIEVER_COUNT = 4;
        final int QUEUE_CAPACITY = 100;
        final long REPORT_PERIOD = 10000;

        TaskSet tasks = new ArrayBlockingTaskSet(QUEUE_CAPACITY);
        ResultSet results = new ArrayBlockingResultSet(QUEUE_CAPACITY);

        ExecutorService localExecutor = Executors.newCachedThreadPool();
        ExecutorService restExecutor = Executors.newFixedThreadPool(MAX_TRANCIEVER_COUNT);

        localExecutor.submit(new TaskGenerator(tasks));
        for (int i = 0; i < MAX_TRANCIEVER_COUNT; i++)
            restExecutor.submit(new TaskTranciever(tasks,results));
        localExecutor.submit(new ResultsConsumer(tasks,results,REPORT_PERIOD));
    }
}
