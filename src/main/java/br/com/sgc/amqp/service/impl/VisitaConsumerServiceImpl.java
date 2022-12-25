package br.com.sgc.amqp.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.VisitaDto;
import br.com.sgc.entities.Veiculo;
import br.com.sgc.entities.VinculoVeiculo;
import br.com.sgc.entities.Visita;
import br.com.sgc.entities.Visitante;
import br.com.sgc.mapper.VeiculoMapper;
import br.com.sgc.mapper.VisitaMapper;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.repositories.VeiculoRepository;
import br.com.sgc.repositories.VinculoVeiculoRepository;
import br.com.sgc.repositories.VisitaRepository;
import br.com.sgc.repositories.VisitanteRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VisitaConsumerServiceImpl implements ConsumerService<VisitaDto> {

	@Autowired
	private VisitaMapper visitaMapper;
	
	@Autowired
	private VeiculoMapper veiculoMapper;
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@Autowired
	private VisitanteRepository visitanteRepository;
	
	@Autowired
	private ResidenciaRepository residenciaRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private VinculoVeiculoRepository vinculoVeiculoRepository;
	
	@Autowired
	private Validators<VisitaDto> validator;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(VisitaDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		this.validator.validar(dto);
		
		Visita visita = this.visitaMapper.visitaDtoToVisita(dto);
		visita.setVisitante(visitanteRepository.findByRg(dto.getRg()).get());
		visita.setResidencia(this.residenciaRepository.findById(dto.getResidenciaId()).get());
		
		this.visitaRepository.save(visita);
			
		if(dto.getPlaca() != "") {
			Optional<Veiculo> veiculo = veiculoRepository.findByPlaca(dto.getPlaca().replace("-", "")); 
			Optional<Visitante> visitante = visitanteRepository.findByRg(dto.getRg());
				
			if(!veiculo.isPresent() && dto.getVeiculoVisita() != null) {
				veiculo = Optional.of(this.veiculoMapper.visitaDtoToVeiculo(dto));
				veiculo.get().setGuide(UUID.randomUUID().toString());
				VinculoVeiculo vinculo = VinculoVeiculo
					.builder()
				    .guide(dto.getGuide())
				    .veiculo(this.veiculoRepository.save(veiculo.get()))
				    .visitante(visitante.get())
				    .build();
				this.vinculoVeiculoRepository.save(vinculo);
			}else {
				if(!this.vinculoVeiculoRepository.findByVisitanteIdAndVeiculoId(visitante.get().getId(), veiculo.get().getId()).isPresent() && veiculo.isPresent()) {					
					VinculoVeiculo vinculo = VinculoVeiculo
						.builder()
						.guide(dto.getGuide())
						.veiculo(veiculo.get())
						.visitante(visitante.get())
						.build();
					this.vinculoVeiculoRepository.save(vinculo);				
				}
			}
		}	
		
	}
	
}
