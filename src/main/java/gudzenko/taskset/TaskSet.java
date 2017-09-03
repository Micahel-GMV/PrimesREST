package gudzenko.taskset;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskSet implements ITaskSet {

    private int taskCapacity;
    private long taskCount = 0;
    private long primesCount = 0;
    private long notPrimesCount = 0;
    private long errorsCount = 0;

    private LinkedBlockingQueue<AtomicInteger> tasks;
    private LinkedBlockingQueue<AtomicInteger> primes;
    private LinkedBlockingQueue<AtomicInteger> notPrimes;
    private LinkedBlockingQueue<AtomicInteger> errors;


    public TaskSet(int capacity){
        this.taskCapacity = capacity;
        tasks = new LinkedBlockingQueue<AtomicInteger>(taskCapacity);
        primes = new LinkedBlockingQueue<AtomicInteger>(taskCapacity);
        notPrimes = new LinkedBlockingQueue<AtomicInteger>(taskCapacity);
        errors = new LinkedBlockingQueue<AtomicInteger>(taskCapacity);
    }

    public void addTask(AtomicInteger value) {
        try {
            tasks.put(value);
            taskCount++;
            //System.out.println(value + " was put to tasks queue. " + taskCount + " tasks were put." );
        } catch (InterruptedException e) {
            System.out.println("Add task interrupted.");
            e.printStackTrace();
        }
    }

    public AtomicInteger getTask() {
        try {
            return tasks.take();
        } catch (InterruptedException e) {
            System.out.println("Get task interrupted.");
            e.printStackTrace();
            return new AtomicInteger(-1);
        }
    }

    public void addPrime(AtomicInteger value) {
        try {
            primes.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add prime interrupted.");
            e.printStackTrace();
        }
    }

    public AtomicInteger getPrime() {
        try {
            primesCount++;
            return primes.take();
        } catch (InterruptedException e) {
            System.out.println("Get prime interrupted.");
            e.printStackTrace();
            return new AtomicInteger(-1);
        }
    }

    public void addNotprime(AtomicInteger value) {
        try {
            notPrimes.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add notprime interrupted.");
            e.printStackTrace();
        }
    }

    public AtomicInteger getNotprime() {
        try {
            notPrimesCount++;
            return notPrimes.take();
        } catch (InterruptedException e) {
            System.out.println("Get notprime interrupted.");
            e.printStackTrace();
            return new AtomicInteger(-1);
        }
    }

    public void addError(AtomicInteger value) {
        try {
            errors.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add error interrupted.");
            e.printStackTrace();
        }
    }

    public AtomicInteger getError() {
        try {
            errorsCount++;
            return errors.take();
        } catch (InterruptedException e) {
            System.out.println("Get error interrupted.");
            e.printStackTrace();
            return new AtomicInteger(-1);
        }
    }

    public long getTaskCount() {
        return primesCount + notPrimesCount + errorsCount;
    }

    public long getPrimesCount() {
        return primesCount;
    }
    public long getNotPrimesCount() {
        return notPrimesCount;
    }
    public long getErrorsCount() {
        return errorsCount;
    }
}
