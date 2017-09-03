package gudzenko.taskset;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskSet implements ITaskSet {

    private int taskCapacity;
    private long taskCount = 0;
    private long primesCount = 0;
    private long notPrimesCount = 0;
    private long errorsCount = 0;

    private LinkedBlockingQueue<Integer> tasks;
    private LinkedBlockingQueue<Integer> primes;
    private LinkedBlockingQueue<Integer> notPrimes;
    private LinkedBlockingQueue<Integer> errors;


    public TaskSet(int capacity){
        this.taskCapacity = capacity;
        tasks = new LinkedBlockingQueue<Integer>(taskCapacity);
        primes = new LinkedBlockingQueue<Integer>(taskCapacity);
        notPrimes = new LinkedBlockingQueue<Integer>(taskCapacity);
        errors = new LinkedBlockingQueue<Integer>(taskCapacity);
    }

    public void addTask(int value) {
        try {
            tasks.put(value);
            taskCount++;
            //System.out.println(value + " was put to tasks queue. " + taskCount + " tasks were put." );
        } catch (InterruptedException e) {
            System.out.println("Add task interrupted.");
            e.printStackTrace();
        }
    }

    public int getTask() {
        try {
            int i = tasks.take();
            //System.out.println(i + " was taken from task list.");
            return i;
        } catch (InterruptedException e) {
            System.out.println("Get task interrupted.");
            e.printStackTrace();
            return -1;
        }
    }

    public void addPrime(int value) {
        try {
            primes.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add prime interrupted.");
            e.printStackTrace();
        }
    }

    public int getPrime() {
        try {
            primesCount++;
            return primes.take();
        } catch (InterruptedException e) {
            System.out.println("Get prime interrupted.");
            e.printStackTrace();
            return -1;
        }
    }

    public void addNotprime(int value) {
        try {
            notPrimes.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add notprime interrupted.");
            e.printStackTrace();
        }
    }

    public int getNotprime() {
        try {
            notPrimesCount++;
            return notPrimes.take();
        } catch (InterruptedException e) {
            System.out.println("Get notprime interrupted.");
            e.printStackTrace();
            return -1;
        }
    }

    public void addError(int value) {
        try {
            errors.put(value);
        } catch (InterruptedException e) {
            System.out.println("Add error interrupted.");
            e.printStackTrace();
        }
    }

    public int getError() {
        try {
            errorsCount++;
            return errors.take();
        } catch (InterruptedException e) {
            System.out.println("Get error interrupted.");
            e.printStackTrace();
            return -1;
        }
    }

    public long getTaskCount() {
        return taskCount;
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
