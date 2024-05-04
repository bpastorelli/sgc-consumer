package br.com.sgc.amqp.service.impl;

import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.dto.VeiculoDto;
import br.com.sgc.entities.Veiculo;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.VeiculoMapper;
import br.com.sgc.repositories.VeiculoRepository;
import br.com.sgc.repositories.VinculoVeiculoRepository;
import br.com.sgc.validators.Validators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VeiculoConsumerServiceImpl implements ConsumerService<VeiculoDto> {

	@Autowired
	private VeiculoMapper veiculoMapper;
	
	@Autowired
	private VeiculoRepository veiculoRepository; 
	
	@Autowired
	private VinculoVeiculoRepository vinculoVeiculoRepository;
	
	@Autowired
	private Validators<VeiculoDto> validator;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void action(VeiculoDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		ResponsePublisherDto response = new ResponsePublisherDto();
		
		Veiculo veiculo = this.veiculoMapper.veiculoDtoToVeiculo(dto);
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			if(dto.getVisitanteId() != 0L) {
				vinculoVeiculoRepository.save(this.veiculoMapper.veiculoToVinculoVeiculo(this.veiculoRepository.save(veiculo)));				
			}else {
				this.veiculoRepository.save(veiculo);
			}
		}	
		
	}
	
}
