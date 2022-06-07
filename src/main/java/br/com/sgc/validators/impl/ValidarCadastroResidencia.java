package br.com.sgc.validators.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.entities.Residencia;
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
	
	@Override
	public List<ErroRegistro> validar(ResidenciaDto t) throws RegistroException {
		
		List<ResidenciaDto> list = new ArrayList<ResidenciaDto>();
		list.add(t);
		
		return validar(list);
		
	}
	
	public List<ErroRegistro> validar(List<ResidenciaDto> t) throws RegistroException {
		
		RegistroException errors = new RegistroException();

		for(ResidenciaDto residencia : t) {
			
			if(residencia.getId() != null) {
				
				Optional<Residencia> residenciaSource = this.residenciaRepository.findById(residencia.getId());
				
				residencia.setId(residenciaSource.get().getId());
				residencia.setGuide(residenciaSource.get().getGuide());
				residencia.setDataCriacao(residenciaSource.get().getDataCriacao());
			}else {
				
				if(residencia.getTicketMorador() != null)
					residencia.setMorador(moradorRepository.findByGuide(residencia.getTicketMorador()).get());
			}
			
		};
		
		return errors.getErros();
		
	}

}
