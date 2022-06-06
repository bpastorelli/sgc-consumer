package br.com.sgc.amqp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ProcessoCadastroDto;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.entities.Residencia;
import br.com.sgc.entities.VinculoResidencia;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.MoradorMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessoCadastroConsumerServiceImpl implements ConsumerService<ProcessoCadastroDto> {

	@Autowired
	private MoradorMapper moradorMapper; 
	
	@Autowired
	private VinculoResidenciaRepository vinculoResidenciaRepository;
	
	@Autowired
	private ResidenciaRepository residenciaRepository;
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@Autowired
	private Validators<ProcessoCadastroDto> validator;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(ProcessoCadastroDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			Optional<Residencia> residencia = this.residenciaRepository.findByCepAndNumero(dto.getResidencia().getCep(), dto.getResidencia().getNumero());
			if(!residencia.isPresent())
				this.vinculoResidenciaRepository.save(this.moradorMapper.processoCadastroDtoToVinculoResidencia(dto));
			else {
				VinculoResidencia vinculo = VinculoResidencia.builder()
						.morador(this.moradorRepository.save(this.moradorMapper.moradorDtoToMorador(dto.getMorador())))
						.residencia(residencia.get())
						.guide(dto.getGuide())
						.build();	
				vinculoResidenciaRepository.save(vinculo);	
			}
		}
		
	}
	
}
