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
import br.com.sgc.entities.Morador;
import br.com.sgc.entities.Residencia;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.MoradorMapper;
import br.com.sgc.mapper.ResidenciaMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessoCadastroConsumerServiceImpl implements ConsumerService<ProcessoCadastroDto> {

	@Autowired
	private MoradorMapper moradorMapper;
	
	@Autowired
	private ResidenciaMapper residenciaMapper;
	
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
			this.tratarGuide(dto);
			Optional<Residencia> residencia = this.residenciaRepository.findByCepAndNumeroAndComplemento(dto.getMorador().getResidencia().getCep(), dto.getMorador().getResidencia().getNumero(), dto.getMorador().getResidencia().getComplemento());
			Optional<Morador> morador = this.moradorRepository.findByCpf(dto.getMorador().getCpf());
			
			if (!residencia.isPresent() && !morador.isPresent()) {
				this.moradorRepository.save(this.moradorMapper.moradorDtoToMorador(dto.getMorador()));
				this.residenciaRepository.save(this.residenciaMapper.residenciaDtoToResidencia(dto.getMorador().getResidencia()));
			} else if (residencia.isPresent() && !morador.isPresent()) {
				this.moradorRepository.save(this.moradorMapper.moradorDtoToMorador(dto.getMorador()));	
			} else if (!residencia.isPresent() && morador.isPresent()) {
				this.residenciaRepository.save(this.residenciaMapper.residenciaDtoToResidencia(dto.getMorador().getResidencia()));
			} else {
				log.info("Nada para gravar de Morador e Residencia.");
			}
		}
		
	}
	
	private void tratarGuide(ProcessoCadastroDto dto) {
		
		dto.getMorador().setGuide(dto.getGuide());
		dto.getMorador().getResidencia().setGuide(dto.getGuide());
		
	}
	
}
