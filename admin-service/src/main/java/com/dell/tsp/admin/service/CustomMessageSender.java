package com.dell.tsp.admin.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@Component
public class CustomMessageSender {
	
	private static final Logger log = LoggerFactory.getLogger(CustomMessageSender.class);
	 
	@Autowired
    private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${spring.rabbitmq.exchange}")
	private String EXCHANGE_NAME;
	
	@Value("${spring.rabbitmq.routingkey}")
	private String ROUTING_KEY;
 

	@Value("${notifiationBaseUrl}")
	private String NOTIFICATION_BASE_URL;
	
    public CustomMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
 
    @Scheduled(fixedDelay = 3000L)
    @HystrixCommand(fallbackMethod = "sendNotificationFallback")
    public String sendMessage(CustomMessage message) {
    	log.info("Sending message to queue");
        log.debug("made connection with: " + rabbitTemplate.getConnectionFactory());
        
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        return "sent Successfully";
   
    }

    public String sendNotificationFallback(CustomMessage message) {
    	log.info("Inside fallback method - sendNotificationFallback");
    	ResponseEntity<String> response = null;
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<CustomMessage> request = new HttpEntity<CustomMessage>(message, headers);
		try {
			response= restTemplate.postForEntity(NOTIFICATION_BASE_URL + "/v1/notification" , request, String.class);
		}catch (RestClientException e) {
			log.error(e.getMessage());
		}
		return response.getBody();
    }

}
