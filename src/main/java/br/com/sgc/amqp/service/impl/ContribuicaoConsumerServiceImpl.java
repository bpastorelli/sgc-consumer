package br.com.sgc.amqp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ConsumerPageDto;
import br.com.sgc.entities.HistoricoImportacao;
import br.com.sgc.entities.Lancamento;
import br.com.sgc.enums.SituacaoEnum;
import br.com.sgc.repositories.HistoricoImportacaoRepository;
import br.com.sgc.repositories.LancamentoRepository;
import br.com.sgc.variable.ProcessConsumer;
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
		
		
		SituacaoEnum situacao = null;
		
		Integer page = 0;
		Integer totalPages = 0;
		String idRequisicao = null;
		
		try {
			
			page = dto.get(0).getPage();
			totalPages = dto.get(0).getTotalPages();
			idRequisicao = dto.get(0).getRequisicaoId();
			log.info("Persistindo registros requisição {}...", idRequisicao);
			ProcessConsumer.PAGES.add(new ConsumerPageDto(idRequisicao, page));
			
			this.lancamentoRepository.saveAll(dto);
			situacao = SituacaoEnum.CONCLUIDO;
			
		} catch (Exception e) {
			
			situacao = SituacaoEnum.FALHA;
			log.info("Processamento finalizado com falha página {}.", page);
			
		}finally {
		
			this.salvarHistorico(idRequisicao, situacao, page, totalPages);
			
		}
		
	}
	
	private void salvarHistorico(String idRequisicao, SituacaoEnum situacao, Integer page, Integer totalPages) {
		
		List<ConsumerPageDto> pagesProcessed = new ArrayList<>(ProcessConsumer.PAGES);
		
		long totalPagesProcessed = pagesProcessed.stream().filter(p -> p.idRequisicao.equals(idRequisicao)).count();
		
		if(totalPagesProcessed == totalPages) {
			Optional<HistoricoImportacao> historico = this.historicoRepository.findByIdRequisicao(idRequisicao);
			historico.ifPresent(p -> { 
				p.setSituacao(situacao); 
				this.historicoRepository.save(historico.get());
				log.info("Processamento requisição {} finalizado com o status {}", idRequisicao, situacao);
			});	
		}
		
	}
	
}
