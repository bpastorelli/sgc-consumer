package br.com.sgc.amqp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.entities.Lancamento;
import br.com.sgc.repositories.LancamentoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContribuicaoConsumerServiceImpl implements ConsumerService<List<Lancamento>> {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(List<Lancamento> dto) throws Exception {
		
		log.info("Persistindo registros...");
		
		try {
			
			this.lancamentoRepository.saveAll(dto);
			log.info("Processamento finalizado com sucesso.");
			
		} catch (Exception e) {
			
			log.error("Processamento finalizado com falha: " + e.getMessage() + ")");
		
		}
		
	}
	
}
