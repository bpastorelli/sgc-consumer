package br.com.sgc.amqp.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

import br.com.sgc.errorheadling.RegistroException;

public interface AmqpConsumer<T> {
	
	void consumer(ConsumerRecord<String, T> record, Acknowledgment ack) throws RegistroException;
}
