package gudzenko.taskset;

import gudzenko.rest.TrancieverResults;

public interface ResultSet {
    public void add(TrancieverResults value);
    public TrancieverResults get();
}