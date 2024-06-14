package br.com.sgc.amqp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(List<Lancamento> dto) throws Exception {
		
		log.info("Persistindo registros...");
		
		SituacaoEnum situacao = null;
		
		try {
			
			this.lancamentoRepository.saveAll(dto);
			situacao = SituacaoEnum.CONCLUIDO;
			log.info("Processamento finalizado com sucesso.");
			
		} catch (Exception e) {
			
			situacao = SituacaoEnum.FALHA;
			log.info("Processamento finalizando com falha.");
			
		}finally {
		
			this.salvarHistorico(dto.get(0).getRequisicaoId(), situacao);
			
		}
		
	}
	
	private void salvarHistorico(String idRequisicao, SituacaoEnum situacao) {
		
		Optional<HistoricoImportacao> historico = this.historicoRepository.findByIdRequisicao(idRequisicao);
		historico.ifPresent(p -> { 
			p.setSituacao(situacao); 
			this.historicoRepository.save(historico.get());
		});
		
	}
	
}
