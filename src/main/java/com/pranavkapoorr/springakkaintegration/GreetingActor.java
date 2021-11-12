package com.pranavkapoorr.springakkaintegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.japi.pf.ReceiveBuilder;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingActor extends AbstractActor {
	//@Autowired
    private GreetingService greetingService;
    
    public GreetingActor(GreetingService service) {
		this.greetingService = service;
	}
    
    @Override
    public void preStart() throws Exception {
    	System.out.println("Starting Greeter");
    	super.preStart();
    }
    
    
    @Override
    public Receive createReceive() {
    	return ReceiveBuilder.create()
    			.match(Greet.class, msg ->getSender().tell(greetingService.greet(msg.name), getSelf()))
    			.build();
    }
    
    @Override
    public void postStop() throws Exception {
    	System.out.println("Stopping Greeter");
    	super.postStop();
    }
    
    public static class Greet {
        private String name;
        public Greet(String name) {
			this.name = name;
		}
        
        public String getName() {
        	return this.name;
        }
        public void setName(String name) {
        	this.name = name;
        }
    }
}