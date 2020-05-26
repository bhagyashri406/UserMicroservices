package com.socialmedia.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.userservice.model.NotificationDTO;

@RestController
public class HelloWorldController {

	@Autowired
	private QueueProducer queueProducer;

	@RequestMapping({ "/hello" })
	public String firstPage() {

		NotificationDTO notificationDTO = new NotificationDTO("UserDeleted", 7);
		try {
			queueProducer.produce(notificationDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Hello World";
	}
}