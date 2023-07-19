package br.com.sgc.amqp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.entities.HistoricoImportacao;
import br.com.sgc.entities.Lancamento;
import br.com.sgc.enums.SituacaoEnum;
import br.com.sgc.repositories.HistoricoImportacaoRepository;
import br.com.sgc.repositories.LancamentoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContribuicaoConsumerServiceImpl implements ConsumerService<List<Lancamento>> {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private HistoricoImportacaoRepository historicoRepository;
	
	private HistoricoImportacao historico = new HistoricoImportacao();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(List<Lancamento> dto) throws Exception {
		
		log.info("Persistindo registros...");
		
		try {
			
			this.lancamentoRepository.saveAll(dto);
			
		} catch (Exception e) {
			
			this.salvarHistorico(dto.get(0).getRequisicaoId(), SituacaoEnum.FALHA);
			log.info("Processamento finalizando com falha.");
			
		}finally {
		
			this.salvarHistorico(dto.get(0).getRequisicaoId(), SituacaoEnum.CONCLUIDO);
			
		}
		
		log.info("Processamento finalizando com sucesso.");
		
	}
	
	@Async
	private void salvarHistorico(String file, SituacaoEnum situacao) {
		
		if(!Optional.ofNullable(this.historico.getId()).isPresent()) {
			this.historico.setNomeArquivo(file);
			this.historico.setIdRequisicao(UUID.randomUUID().toString());
			this.historico.setSituacao(situacao);			
		}else
			this.historico.setSituacao(situacao);
		
		this.historico = this.historicoRepository.save(historico);
		
	}
	
}
