package gudzenko;

import gudzenko.disruptor.LMAXWriter;
import gudzenko.disruptor.TaskGeneratorDisruptor;
import gudzenko.processors.*;
import gudzenko.taskset.ArrayBlockingTaskSet;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class AppDisruptor
{/*
    public static void main( String[] args )
    {
        ArrayBlockingTaskSet tasks = new ArrayBlockingTaskSet(1000);

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
    }*/
}
