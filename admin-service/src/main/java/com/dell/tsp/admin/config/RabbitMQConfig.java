package com.dell.tsp.admin.config;

import java.net.URI;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

	private static final String SIMPLE_MESSAGE_QUEUE = "TSP.EmailNotification.Payload.Queue";
	@Value("${spring.rabbitmq.uri}")
	private String RABBITMQ_URI;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		/*
		 * connectionFactory.setUsername("guest");
		 * connectionFactory.setPassword("guest");
		 */
        connectionFactory.setUri(RABBITMQ_URI);
        return connectionFactory;
    }

    @Bean
    public Queue simpleQueue() {
        return new Queue(SIMPLE_MESSAGE_QUEUE);
    }
    
    @Bean
    Exchange testExchange() {
    	return ExchangeBuilder.directExchange("testExchange").
    			autoDelete().
    			build();
    }
    
    @Bean
	  public Binding binding1a(DirectExchange testExchange, 
	      Queue simpleQueue) {
	      return BindingBuilder.bind(simpleQueue)
	          .to(testExchange)
	          .with("messages.key");
	  } 

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(SIMPLE_MESSAGE_QUEUE);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
	
}
