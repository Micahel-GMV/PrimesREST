package gudzenko.rest;

import gudzenko.taskset.ITaskSet;

public class TaskTranciever extends Thread {//Gets number from tasks.tasks and sends it for processing to
                                            //http://api.mathjs.org/v1/?expr=isPrime(41), recievs answer and decides
                                            //which result set in tasks to be replenished with this number.

    ITaskSet tasks;

    public TaskTranciever(ITaskSet tasks){
        this.tasks = tasks;
    }

    private TrancieverResults process(int i){// It`s dummy for send-recieve operations.
                                //
        int modulo = i%3;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            modulo = -1;
            System.out.println("Task processor is interrupted.");
            e.printStackTrace();
        }
        switch (modulo){
            case 0: return TrancieverResults.EVEN;
            case 1: return TrancieverResults.ODD;
            default: return TrancieverResults.ERROR;
        }
    }

    @Override
    public void run(){
        int i;
        while (!isInterrupted()){
            i = tasks.getTask();
            //System.out.println(i + " got from task list. Sent it to processing.");
            switch ( process(i) ){
                case EVEN: tasks.addPrime(i);
                break;
                case ODD: tasks.addNotprime(i);
                break;
                default: tasks.addError(i);
            }
        }
    }
}
