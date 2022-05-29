package br.com.sgc.amqp.service.impl;

import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.entities.VinculoResidencia;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.MoradorMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MoradorConsumerServiceImpl implements ConsumerService<MoradorDto> {

	@Autowired
	private MoradorMapper moradorMapper;
	
	@Autowired
	private MoradorRepository moradorRepository; 
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Autowired
	private Validators<MoradorDto> validator;
	
	@Override
	public void action(MoradorDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			if(dto.getResidenciaId() != 0L && dto.getId() == null) {
				log.info("Registrando com vinculo de residência...");
				VinculoResidencia res = this.moradorMapper.moradorDtoToVinculoResidencia(dto);
				this.vinculoResidenciaRepository.save(res);				
			}else {
				log.info("Registrando sem vinculo de residência...");
				this.moradorRepository.save(this.moradorMapper.moradorDtoToMorador(dto));
			}
		}	
		
	}
	
}
