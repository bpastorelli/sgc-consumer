package br.com.sgc.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.ResidenciaAvro;
import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.errorheadling.RegistroException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResidenciaConsumerKafkaImpl implements AmqpConsumer<ResidenciaAvro> {
	
	@Autowired
	private ConsumerService<ResidenciaDto> consumerService;
	
	@Autowired
	private ConvertAvroToObject<ResidenciaDto, ResidenciaAvro> converter;
	
	@KafkaListener(topics = "${residencia.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, ResidenciaAvro> message, Acknowledgment ack) throws RegistroException {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
		
		this.consumerService.action(this.converter.convert(message.value()));
		
		ack.acknowledge();
		
	}

}
