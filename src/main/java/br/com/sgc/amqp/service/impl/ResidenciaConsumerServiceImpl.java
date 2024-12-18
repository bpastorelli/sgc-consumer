package br.com.sgc.amqp.service.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.entities.VinculoResidencia;
import br.com.sgc.mapper.ResidenciaMapper;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResidenciaConsumerServiceImpl implements ConsumerService<ResidenciaDto> {

	@Autowired
	private ResidenciaMapper residenciaMapper;
	
	@Autowired
	private ResidenciaRepository residenciaRepository; 
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Autowired
	private Validators<ResidenciaDto> validator;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(ResidenciaDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		response.setErrors(this.validator.validar(dto));
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			log.info("Registrando residencia...");
			this.residenciaRepository.save(this.residenciaMapper.residenciaDtoToResidencia(dto));
		}	
		
	}
	
}
