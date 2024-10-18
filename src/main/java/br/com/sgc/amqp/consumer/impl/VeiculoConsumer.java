package br.com.sgc.amqp.consumer.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.VeiculoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VeiculoConsumer{
	
	@Autowired
	private ConsumerService<VeiculoDto> consumerService;
	
	@KafkaListener(topics = "${veiculo.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(@Payload VeiculoDto message) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
			
		try {
			this.consumerService.action(message);
		} catch (Exception ex) {
			throw new AmqpRejectAndDontRequeueException(ex);
		}
		
	}

}
