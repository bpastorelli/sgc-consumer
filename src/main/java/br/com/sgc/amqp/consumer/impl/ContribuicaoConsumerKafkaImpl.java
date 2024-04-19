package br.com.sgc.amqp.consumer.impl;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import br.com.sgc.ContribuicaoAvro;
import br.com.sgc.amqp.consumer.AmqpConsumer;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.entities.Lancamento;
import br.com.sgc.enums.SituacaoEnum;
import br.com.sgc.service.HistoricoImportacaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContribuicaoConsumerKafkaImpl implements AmqpConsumer<ContribuicaoAvro> {
	
	@Autowired
	private HistoricoImportacaoService historicoService;
	
	@Autowired
	private ConsumerService<List<Lancamento>> consumerService;
	
	@Autowired
	private ConvertAvroToObject<List<Lancamento>, ContribuicaoAvro> converter;
	
	@KafkaListener(topics = "${contribuicao.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(ConsumerRecord<String, ContribuicaoAvro> message, Acknowledgment ack) {
		
		log.info("Recebida a mensagem, enviando para o serviço...");
		
		SituacaoEnum situacao = null;
		
		try {
			this.consumerService.action(this.converter.convert(message.value()));
			situacao = SituacaoEnum.CONCLUIDO;
			
		} catch (Exception ex) {
			situacao = SituacaoEnum.FALHA;
			throw new AmqpRejectAndDontRequeueException(ex);
		}finally {
			
			this.historicoService.salvarHistorico(this.converter.convert(message.value()).get(0).getRequisicaoId(), situacao);
			
		}
		
		ack.acknowledge();
		
	}

}
