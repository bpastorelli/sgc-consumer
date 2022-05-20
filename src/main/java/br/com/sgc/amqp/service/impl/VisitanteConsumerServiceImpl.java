package br.com.sgc.amqp.service.impl;

import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.dto.VisitanteDto;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.mapper.VisitanteMapper;
import br.com.sgc.repositories.VisitanteRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VisitanteConsumerServiceImpl implements ConsumerService<VisitanteDto> {

	@Autowired
	private VisitanteMapper visitanteMapper;
	
	@Autowired
	private VisitanteRepository visitanteRepository;
	
	@Autowired
	private Validators<VisitanteDto> validator;
	
	@Override
	public void action(VisitanteDto dto) throws RegistroException {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			this.visitanteRepository.save(this.visitanteMapper.visitanteDtoToVisitante(dto));
		}	
		
	}
	
}
