package gudzenko;

import gudzenko.processors.*;
import gudzenko.rest.TaskTranciever;
import gudzenko.taskset.TaskSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TaskSet tasks = new TaskSet(10);
        final int MAX_TRANCIEVER_COUNT = 20000;

        ExecutorService localExecutor = Executors.newCachedThreadPool();
        localExecutor.submit(new TaskGenerator(tasks));
        localExecutor.submit(new PrimesProcessor(tasks));
        localExecutor.submit(new NotprimesProcessor(tasks));
        localExecutor.submit(new ErrorsProcessor(tasks));
        localExecutor.submit(new Statistics(tasks, 100000));

        ExecutorService restExecutor = Executors.newFixedThreadPool(MAX_TRANCIEVER_COUNT);
        for (int i = 0; i < MAX_TRANCIEVER_COUNT; i++) restExecutor.submit(new TaskTranciever(tasks));

    }
}
