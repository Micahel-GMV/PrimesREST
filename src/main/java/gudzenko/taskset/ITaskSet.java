package gudzenko.taskset;

public interface ITaskSet {
    public void addTask(int value);
    public int getTask();
    public void addPrime(int value);
    public int getPrime();
    public void addNotprime(int value);
    public int getNotprime();
    public void addError(int value);
    public int getError();
    public long getTaskCount();
}
