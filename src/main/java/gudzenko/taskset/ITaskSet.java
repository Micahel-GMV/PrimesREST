package gudzenko.taskset;

import java.util.concurrent.atomic.AtomicInteger;

public interface ITaskSet {
    public void addTask(AtomicInteger value);
    public AtomicInteger getTask();
    public void addPrime(AtomicInteger value);
    public AtomicInteger getPrime();
    public void addNotprime(AtomicInteger value);
    public AtomicInteger getNotprime();
    public void addError(AtomicInteger value);
    public AtomicInteger getError();
    public long getTaskCount();
}
