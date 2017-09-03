package gudzenko.rest;

import gudzenko.taskset.ITaskSet;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskTranciever extends Thread {//Gets number from tasks.tasks and sends it for processing to
                                            //http://api.mathjs.org/v1/?expr=isPrime(41), recievs answer and decides
                                            //which result set in tasks to be replenished with this number.

    ITaskSet tasks;

    public TaskTranciever(ITaskSet tasks){
        this.tasks = tasks;
    }

    public TrancieverResults process(int i){
        TrancieverResults result = TrancieverResults.ERROR;
        try
        {
            Client client = Client.create();
            String resourceUrl = "http://api.mathjs.org/v1/?expr=isPrime(" + i + ")";
            WebResource webResource = client
                    .resource(resourceUrl);
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() == 200) {
                String output = response.getEntity(String.class);
                if (output.equals("true")) result = TrancieverResults.PRIME;
                else if (output.equals("false")) result = TrancieverResults.NOTPRIME;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void run(){
        int i;
        while (!isInterrupted()){
            i = tasks.getTask().get();
            //System.out.println(i + " got from task list. Sent it to processing.");
            switch ( process(i) ){
                case PRIME: tasks.addPrime(new AtomicInteger(i));
                break;
                case NOTPRIME: tasks.addNotprime(new AtomicInteger(i));
                break;
                default: tasks.addError(new AtomicInteger(i));
            }
        }
    }
}
