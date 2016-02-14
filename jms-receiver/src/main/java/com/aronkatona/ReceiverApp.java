package com.aronkatona;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReceiverApp {
	
	private static final String RECEIVER_QUEUE_NAME = "receiver_queue";
	private static final String SENDER_QUEUE_NAME = "sender_queue";
	
	@Autowired
	private ConfigurableApplicationContext context;

    @JmsListener(destination = RECEIVER_QUEUE_NAME, containerFactory = "myJmsContainerFactory")
    public void receiveStringDestinationA(String message) {
        System.out.println("message from " + RECEIVER_QUEUE_NAME +  ": <" + message + ">");
        UUID uuid = UUID.randomUUID();
        sendBackMessage(uuid.toString());
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

	private void sendBackMessage(String message) {
		 JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		 System.out.println("Sending back message to: " + SENDER_QUEUE_NAME);
		 jmsTemplate.convertAndSend(SENDER_QUEUE_NAME, message);
	}
      

}
