package br.com.sgc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sgc.entities.HistoricoImportacao;
import br.com.sgc.enums.SituacaoEnum;

@Repository	
public interface HistoricoImportacaoRepository extends JpaRepository<HistoricoImportacao, Long> {
	
	List<HistoricoImportacao> findBySituacaoIn(List<SituacaoEnum> situacao);
	
	Optional<HistoricoImportacao> findByIdRequisicao(String idRequisicao);

}
