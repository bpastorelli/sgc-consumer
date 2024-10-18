package br.com.sgc.amqp.consumer.impl;

import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.entities.Lancamento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContribuicaoConsumer {
	
	@Autowired
	private ConsumerService<List<Lancamento>> consumerService;
	
	@KafkaListener(topics = "${contribuicao.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(@Payload List<Lancamento> message) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
		
		try {
			this.consumerService.action(message);
		} catch (Exception ex) {
			throw new AmqpRejectAndDontRequeueException(ex);
		}
		
	}

}
