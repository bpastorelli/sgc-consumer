package br.com.sgc.amqp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.PerfilEnum;
import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.dto.ResponsePublisherDto;
import br.com.sgc.entities.Morador;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.mapper.MoradorMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.VinculoResidenciaRepository;
import br.com.sgc.utils.PasswordUtils;
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
		
		List<Morador> listMorador = new ArrayList<Morador>();
		
		Morador morador = this.moradorMapper.moradorDtoToMorador(dto);
		
		morador.setSenha(PasswordUtils.gerarBCrypt(morador.getCpf().substring(0, 6)));
		morador.setPerfil(morador.getPerfil() == null ? PerfilEnum.ROLE_USUARIO : morador.getPerfil());
		
		listMorador.add(morador);
		
		List<ErroRegistro> errors = this.validator.validar(dto);
		response.setErrors(errors);
		
		if(response.getErrors().size() > 0) {			
			response.getErrors().forEach(erro -> {
				throw new AmqpRejectAndDontRequeueException(erro.getDetalhe()); 
			});			
		}else {
			if(dto.getResidenciaId() != 0L) {
				vinculoResidenciaRepository.save(this.moradorMapper.moradorDtoToVinculoResidencia(this.moradorRepository.save(morador)));				
			}else {
				this.moradorRepository.saveAll(listMorador);
			}
		}	
		
	}
	
}
