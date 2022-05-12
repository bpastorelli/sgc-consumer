package br.com.sgc.consumer.amqp.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface AmqpConsumer<T> {
	
	void consumer(ConsumerRecord<String, T> message);
}
