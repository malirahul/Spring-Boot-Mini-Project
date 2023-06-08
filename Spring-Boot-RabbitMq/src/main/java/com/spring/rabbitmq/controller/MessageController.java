package com.spring.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rabbitmq.dto.User;
import com.spring.rabbitmq.publisher.RabbitMqJsonProducer;
import com.spring.rabbitmq.publisher.RabbitMqProducer;

@RestController
@RequestMapping("/rabbitmq")
public class MessageController {
	
	@Autowired
	private RabbitMqProducer producer;
	
	@Autowired
	private RabbitMqJsonProducer jsonProducer;

	
	
	@GetMapping("/publish")
	public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
		producer.sendMessage(message);
		return ResponseEntity.ok("Message sent to the RabittMq....");
	}
	
	@PostMapping("/publish")
	public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
		jsonProducer.sendJsonMessage(user);
		return ResponseEntity.ok("Json Message sent to the RabittMq....");
	}

}
