package gudzenko.rest;

import gudzenko.taskset.ArrayBlockingTaskSet;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gudzenko.taskset.ResultSet;
import gudzenko.taskset.TaskSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskTranciever extends Thread {//Gets number from tasks.tasks and sends it for processing to
                                            //http://api.mathjs.org/v1/?expr=isPrime(41), recievs answer and decides
                                            //which result set in tasks to be replenished with this number.

    private static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TaskTranciever.class);
    private TaskSet tasks;
    private ResultSet results;

    public TaskTranciever(TaskSet tasks, ResultSet results) {
        this.results = results;
        this.tasks = tasks;
    }

    private TrancieverResults process(Integer i){
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
            LOGGER.error("Tranciever error:\n" + e.getMessage());
        }
        if (result == TrancieverResults.ERROR) tasks.add(i);
        return result;
    }

    @Override
    public void run(){
        while (!isInterrupted()){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                LOGGER.error("Sleep in tranciever interrupted.");
            }
            results.add(process(tasks.get()));
        }
    }
}
