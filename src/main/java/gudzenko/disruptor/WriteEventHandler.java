package gudzenko.disruptor;

import com.lmax.disruptor.EventHandler;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gudzenko.rest.TrancieverResults;
import gudzenko.taskset.ITaskSet;

import java.util.concurrent.atomic.AtomicInteger;

public class WriteEventHandler implements EventHandler<WriteEvent> {

    private ITaskSet tasks;

    public WriteEventHandler(ITaskSet tasks){
        this.tasks = tasks;
    }

    public void onEvent(WriteEvent writeEvent, long sequence, boolean endOfBatch) throws Exception {
        if (writeEvent != null && writeEvent.get() != null) {
            AtomicInteger message = writeEvent.get();

            // Put you business logic here.
            // here it will print only the submitted message.
            TrancieverResults result = TrancieverResults.ERROR;
            try
            {
                Client client = Client.create();
                String resourceUrl = "http://api.mathjs.org/v1/?expr=isPrime(" + message + ")";
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
            switch ( result ){
                case PRIME: tasks.addPrime(message);
                    break;
                case NOTPRIME: tasks.addNotprime(message);
                    break;
                default: tasks.addError(message);
            }
        }
    }
}
