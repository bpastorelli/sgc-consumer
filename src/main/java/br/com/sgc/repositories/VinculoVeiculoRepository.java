package br.com.sgc.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.entities.VinculoVeiculo;

@Repository
@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "VinculoVeiculoRepository.findByPlacaAndVisitanteId", 
				query = "SELECT a FROM Veiculo a "
						+ "WHERE a.veiculo.placa = :placa "
						+ "AND a.visitanteId = :visitanteId ")})

public interface VinculoVeiculoRepository extends JpaRepository<VinculoVeiculo, Long> {
	
	@Transactional(readOnly = true)
	Optional<VinculoVeiculo> findById(Long id);
	
	VinculoVeiculo findByVeiculoPlacaAndVisitanteId(@Param("placa") String placa, @Param("visitanteId") Long visitanteId);
	
	Optional<VinculoVeiculo> findByVisitanteIdAndVeiculoId(Long visitanteId, Long veiculoId);
	
	List<VinculoVeiculo> findByVisitanteId(Long visitanteId);
	
	List<VinculoVeiculo> findByVeiculoId(Long veiculoId);
	
	List<VinculoVeiculo> findByVisitanteRg(String rg);

}
