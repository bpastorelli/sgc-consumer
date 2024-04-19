package br.com.sgc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.entities.HistoricoImportacao;
import br.com.sgc.enums.SituacaoEnum;
import br.com.sgc.repositories.HistoricoImportacaoRepository;

@Service
public class HistoricoImportacaoService {
	
	@Autowired
	private HistoricoImportacaoRepository historicoRepository;
	
	private HistoricoImportacao historico = new HistoricoImportacao();
	
	public void salvarHistorico(String idRequisicao, SituacaoEnum situacao) {
		
		this.historico = this.historicoRepository.findByIdRequisicao(idRequisicao).get();
		this.historico.setSituacao(situacao);
		
		this.historico = this.historicoRepository.save(historico);
		
	}

}
