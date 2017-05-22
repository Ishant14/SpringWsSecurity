package com.springWsSecurity.SpringWSSecurity.endpoint;

import com.springwssecurity.types.helloworld.Greeting;
import com.springwssecurity.types.helloworld.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by igaurav on 5/22/2017.
 */
@Endpoint
public class HelloWorldEndPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldEndPoint.class);

    private static final String NAMESPACE="http://springWsSecurity.com/types/helloworld";

    @PayloadRoot(namespace = NAMESPACE,localPart = "person")
    @ResponsePayload
    public Greeting sayHello(@RequestPayload Person request){

        LOGGER.info("Endpoint recieved : "+ request.getFirstName()+":  "+request.getLastName());
        String greeting = "Hello "+request.getFirstName()+" "+request.getLastName()+" !";
        Greeting response = new Greeting();
        response.setGreeting(greeting);
        return response;

    }

}
