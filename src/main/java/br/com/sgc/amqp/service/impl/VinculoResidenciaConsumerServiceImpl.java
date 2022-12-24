package br.com.sgc.amqp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.VinculoResidenciaDto;
import br.com.sgc.mapper.VinculoResidenciaMapper;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VinculoResidenciaConsumerServiceImpl implements ConsumerService<VinculoResidenciaDto> {

	@Autowired
	private VinculoResidenciaMapper vinculoMapper; 
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(VinculoResidenciaDto dto) throws Exception {
		
		log.info("Persistindo registro...");

		log.info("Registrando vinculo...");
		this.vinculoResidenciaRepository.save(this.vinculoMapper.visitanteDtoToVisitante(dto));	
		
	}
	
}
