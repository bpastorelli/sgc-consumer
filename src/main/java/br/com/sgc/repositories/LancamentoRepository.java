package br.com.sgc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.entities.Lancamento;

@Repository
@Transactional(readOnly = true)
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	Optional<Lancamento> findByPeriodoAndId(String periodo, Long id);
	
	List<Lancamento> findByMoradorIdIn(List<Long> codigos);
	
}
