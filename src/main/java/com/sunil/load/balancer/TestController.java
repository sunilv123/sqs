package com.sunil.load.balancer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.load.balancer.sqs.SqsService;

@RestController
public class TestController {
	
	@Autowired
	private SqsService sqsService;
	
	@PostMapping("/test")
	public TestDto testMethod(@RequestBody TestDto dto) throws Exception {
		
		System.out.println("test is called... "+LocalDateTime.now());
		
		sqsService.sendTestMessage(dto);
		
		return dto;
	}
	
}
