package br.com.sgc.amqp.consumer.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.MoradorAvro;
import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.enums.PerfilEnum;
import br.com.sgc.errorheadling.RegistroException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MoradorConsumerKafkaImpl implements AmqpConsumer<MoradorAvro> {
	
	@Autowired
	private ConsumerService<MoradorDto> consumerService;
	
	@KafkaListener(topics = "${morador.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, MoradorAvro> message, Acknowledgment ack) throws RegistroException {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
		
		MoradorDto moradorDto = MoradorDto
				.builder()
				.id(message.value().getId())
				.nome(message.value().getNome().toString())
				.rg(message.value().getRg().toString())
				.cpf(message.value().getCpf().toString())
				.email(message.value().getEmail().toString())
				.associado(message.value().getAssociado())
				.celular(message.value().getCelular().toString())
				.telefone(message.value().getTelefone().toString())
				.residenciaId(message.value().getResidenciaId())
				.guide(message.value().getGuide().toString())
				.perfil(message.value().getPerfil().ordinal() == 0 ? PerfilEnum.ROLE_ADMIN : PerfilEnum.ROLE_USUARIO)
				.build();
		
		this.consumerService.action(moradorDto);
		
		ack.acknowledge();
		
	}

}
