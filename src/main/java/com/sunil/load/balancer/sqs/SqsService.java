package com.sunil.load.balancer.sqs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunil.load.balancer.TestDto;

@Service
public class SqsService {

	@Value("${sqs.url}")
	private String sqsURL;

	private static AmazonSQS sqs = null;

	public void sendTestMessage(TestDto dto) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		final AmazonSQS sqs = getSqsConnection(); // to create AmazonSQS
		sqs.sendMessage(new SendMessageRequest(sqsURL, mapper.writeValueAsString(dto)));

		SendMessageRequest sendMessageStandardQueue = new SendMessageRequest().withQueueUrl(sqsURL)
				.withMessageBody(mapper.writeValueAsString(dto)).withDelaySeconds(0);

		sqs.sendMessage(sendMessageStandardQueue);

	}

	@SqsListener(value = { "${sqs.url}" }, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void readMessage(String message) {
		System.out.println("Reading Message... " + message);
	}

	public AmazonSQS getSqsConnection() {

		if (sqs == null) {

			BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("XXXXXXXXXXXXXX",
					"XXXXXXXXXXXXXXXXX");

			sqs = AmazonSQSClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
					.withRegion(Regions.US_EAST_1).build();

		}
		return sqs;
	}
}
