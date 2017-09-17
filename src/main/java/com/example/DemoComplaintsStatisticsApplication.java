package com.example;


import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.Channel;

@SpringBootApplication
public class DemoComplaintsStatisticsApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoComplaintsStatisticsApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DemoComplaintsStatisticsApplication.class, args);
	}

	
	
	
	
	@Bean
	public SpringAMQPMessageSource statisticsSource(Serializer serializer){
		
		logger.info("statisticsSource()");
		
		return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)){
			
			
			@RabbitListener(queues="ComplaintEvents")
			@Override
			public void onMessage(Message message, Channel channel) throws Exception{
				
				logger.info("onMessage {}", message.toString());
				super.onMessage(message, channel);
				
			}
			
		};
	}

}


