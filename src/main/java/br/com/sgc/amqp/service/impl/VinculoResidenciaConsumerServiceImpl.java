package br.com.sgc.amqp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ProcessoCadastroDto;
import br.com.sgc.dto.VinculoResidenciaDto;
import br.com.sgc.entities.Morador;
import br.com.sgc.entities.Residencia;
import br.com.sgc.mapper.MoradorMapper;
import br.com.sgc.mapper.ResidenciaMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VinculoResidenciaConsumerServiceImpl implements ConsumerService<VinculoResidenciaDto> {
	
	@Autowired
	private MoradorMapper moradorMapper; 
	
	@Autowired
	private ResidenciaMapper residenciaMapper;
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@Autowired
	private ResidenciaRepository residenciaRepository;
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(VinculoResidenciaDto dto) throws Exception {

		log.info("Registrando vinculo...");
		
		ProcessoCadastroDto processoDto = new ProcessoCadastroDto();
		Residencia residencia = residenciaRepository.findById(dto.getResidenciaId()).get();
		Morador morador = moradorRepository.findById(dto.getMoradorId()).get();
		
		processoDto.setGuide(dto.getGuide());
		processoDto.setResidencia(this.residenciaMapper.residenciaToResidenciaDto(residencia));
		processoDto.setMorador(this.moradorMapper.moradorToMoradorDto(morador));
		
		this.vinculoResidenciaRepository.save(this.moradorMapper.processoCadastroDtoToVinculoResidencia(processoDto));	
		
	}
	
}
