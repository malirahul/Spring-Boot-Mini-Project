package com.spring.rabbitmq.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.rabbitmq.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMqJsonProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.json.key}")
	private String rountingJsonKey;

	@Autowired
	private RabbitTemplate rabbitTemplate;


	public void sendJsonMessage(User user) {
		log.info(String.format("Json Message Sent -> %s", user.toString()));
		rabbitTemplate.convertAndSend(exchange, rountingJsonKey, user);
	}
	
}
