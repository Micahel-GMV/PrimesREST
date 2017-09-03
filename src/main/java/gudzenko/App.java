package gudzenko;

import gudzenko.disruptor.LMAXWriter;
import gudzenko.disruptor.TaskGeneratorDisruptor;
import gudzenko.processors.*;
import gudzenko.rest.TaskTranciever;
import gudzenko.taskset.TaskSet;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*
        TaskSet tasks = new TaskSet(10);
        final int MAX_TRANCIEVER_COUNT = 500;

        ExecutorService localExecutor = Executors.newCachedThreadPool();
        localExecutor.submit(new TaskGenerator(tasks));
        localExecutor.submit(new PrimesProcessor(tasks));
        localExecutor.submit(new NotprimesProcessor(tasks));
        localExecutor.submit(new ErrorsProcessor(tasks));
        localExecutor.submit(new Statistics(tasks, 10));

        ExecutorService restExecutor = Executors.newFixedThreadPool(MAX_TRANCIEVER_COUNT);
        for (int i = 0; i < MAX_TRANCIEVER_COUNT; i++) restExecutor.submit(new TaskTranciever(tasks));
*/
        TaskSet tasks = new TaskSet(1000);

        Random random = new Random();

        LMAXWriter lmaxWriter = new LMAXWriter(tasks);
        lmaxWriter.setRingBufferSize(1000); //deliberately set. Final ring buffer size would be nearest power of 2.
        lmaxWriter.init();

        // submit messages to process concurrently using disruptor
        ExecutorService localExecutor = Executors.newCachedThreadPool();
        localExecutor.submit(new TaskGeneratorDisruptor(tasks,lmaxWriter));
        localExecutor.submit(new PrimesProcessor(tasks));
        localExecutor.submit(new NotprimesProcessor(tasks));
        localExecutor.submit(new ErrorsProcessor(tasks));
        localExecutor.submit(new Statistics(tasks, 10));

        //lmaxWriter.close();
    }
}
