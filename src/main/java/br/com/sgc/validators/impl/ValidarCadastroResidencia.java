package br.com.sgc.validators.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.validators.Validators;

@Component
public class ValidarCadastroResidencia implements Validators<ResidenciaDto> {
	
	@Autowired
	private ResidenciaRepository residenciaRepository;
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	private static final String TITULO = "Cadastro de residência recusado!";
	
	@Override
	public List<ErroRegistro> validar(ResidenciaDto t) throws RegistroException {
		
		List<ResidenciaDto> list = new ArrayList<ResidenciaDto>();
		list.add(t);
		
		return validar(list);
		
	}
	
	public List<ErroRegistro> validar(List<ResidenciaDto> t) throws RegistroException {
		
		RegistroException errors = new RegistroException();

		t.forEach(r -> {
			
			this.residenciaRepository.findByCepAndNumero(r.getCep(), r.getNumero())
				.ifPresent(res -> errors.getErros().add(new ErroRegistro("", TITULO, " Endereço já existente")));

			if(r.getTicketMorador() != null) {			
				if(!this.moradorRepository.findByGuide(r.getTicketMorador()).isPresent())
					errors.getErros().add(new ErroRegistro("", TITULO, " Morador a ser vinculado não encontrado"));
			}
			
		});
		
		return errors.getErros();
		
	}

}
