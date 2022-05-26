package br.com.sgc.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.MoradorAvro;
import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.MoradorDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MoradorConsumerKafkaImpl implements AmqpConsumer<MoradorAvro> {
	
	@Autowired
	private ConsumerService<MoradorDto> consumerService;
	
	@Autowired
	private ConvertAvroToObject<MoradorDto, MoradorAvro> converter;
	
	@KafkaListener(topics = "${morador.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, MoradorAvro> message, Acknowledgment ack) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");

		MoradorDto moradorDto = this.converter.convert(message.value());
		
		try {
			this.consumerService.action(moradorDto);
		} catch (Exception ex) {
			ack.acknowledge();
			throw new AmqpRejectAndDontRequeueException(ex);
		}
		
		ack.acknowledge();
		
	}

}
