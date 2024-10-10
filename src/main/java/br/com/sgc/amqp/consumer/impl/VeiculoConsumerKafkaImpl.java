package br.com.sgc.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.VeiculoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VeiculoConsumerKafkaImpl implements AmqpConsumer<VeiculoDto> {
	
	@Autowired
	private ConsumerService<VeiculoDto> consumerService;
	
	@KafkaListener(topics = "${veiculo.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, VeiculoDto> message, Acknowledgment ack) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
			
		try {
			this.consumerService.action(message.value());
		} catch (Exception ex) {
			ack.acknowledge();
			throw new AmqpRejectAndDontRequeueException(ex);
		}
		
		ack.acknowledge();
		
	}

}
