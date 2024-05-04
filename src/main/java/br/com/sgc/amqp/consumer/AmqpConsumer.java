package br.com.sgc.amqp.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface AmqpConsumer<T> {
	
	void consumer(ConsumerRecord<String, T> record, Acknowledgment ack);
}
