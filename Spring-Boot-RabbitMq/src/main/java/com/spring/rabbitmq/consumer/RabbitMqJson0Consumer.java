package com.spring.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.spring.rabbitmq.dto.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMqJson0Consumer {
	
	@RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
	public void consume(User user) {
			log.info(String.format("Received Json Message -> %s", user));
		}
}
