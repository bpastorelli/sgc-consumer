package br.com.sgc.consumer.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.MoradorAvro;
import br.com.sgc.consumer.amqp.consumer.AmqpConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MoradorConsumerKafkaImpl implements AmqpConsumer<MoradorAvro> {
	
	@KafkaListener(topics = "${morador.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, MoradorAvro> message, Acknowledgment ack) {
		
		log.info("Consumindo mensagem...");
		
		log.info("Dados" + message.value());
		
		ack.acknowledge();
		
	}

}
