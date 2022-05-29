package br.com.sgc.amqp.service.impl;

import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.entities.VinculoResidencia;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.ResidenciaMapper;
import br.com.sgc.repositories.MoradorRepository;
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
	private MoradorRepository moradorRepository;
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Autowired
	private Validators<ResidenciaDto> validator;
	
	@Override
	public void action(ResidenciaDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			if(dto.getGuide() != null) {
				VinculoResidencia vinculo = VinculoResidencia
						.builder()
						.residenciaId(dto.getId())
						.morador(moradorRepository.findByGuide(dto.getTicketMorador()).get())
						.guide(dto.getGuide())
						.build();
				vinculoResidenciaRepository.save(vinculo);				
			}else {
				this.residenciaRepository.save(this.residenciaMapper.residenciaDtoToResidencia(dto));
			}
		}	
		
	}
	
}
