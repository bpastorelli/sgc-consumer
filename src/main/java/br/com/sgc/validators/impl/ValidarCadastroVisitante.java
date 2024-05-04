package br.com.sgc.validators.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.dto.VisitanteDto;
import br.com.sgc.entities.Visitante;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.repositories.VisitanteRepository;
import br.com.sgc.validators.Validators;

@Component
public class ValidarCadastroVisitante implements Validators<VisitanteDto> {

	@Autowired
	private VisitanteRepository visitanteRepository;
	
	@Override
	public List<ErroRegistro> validar(VisitanteDto t) throws RegistroException {
		
		RegistroException errors = new RegistroException();
		
		if(t.getId() != null) {
			
			Optional<Visitante> visitanteSource = visitanteRepository.findById(t.getId());
			
			if(!visitanteSource.get().getCpf().isEmpty())
				if(t.getCpf() != visitanteSource.get().getCpf())
					t.setCpf(visitanteSource.get().getCpf());
			
			t.setId(visitanteSource.get().getId());
			t.setRg(visitanteSource.get().getRg());
			t.setDataCriacao(visitanteSource.get().getDataCriacao());
			t.setGuide(visitanteSource.get().getGuide());
			
		}
		
		
		return errors.getErros();
		
	}

}
