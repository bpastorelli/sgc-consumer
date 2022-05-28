package br.com.sgc.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.VisitaAvro;
import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.VisitaDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VisitaConsumerKafkaImpl implements AmqpConsumer<VisitaAvro> {
	
	@Autowired
	private ConsumerService<VisitaDto> consumerService;
	
	@Autowired
	private ConvertAvroToObject<VisitaDto, VisitaAvro> converter;
	
	@KafkaListener(topics = "${visita.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, VisitaAvro> message, Acknowledgment ack) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
		
		try {
			this.consumerService.action(this.converter.convert(message.value()));
		} catch (Exception ex) {
			ack.acknowledge();
			throw new AmqpRejectAndDontRequeueException(ex);
		}
		
		ack.acknowledge();
		
	}

}
