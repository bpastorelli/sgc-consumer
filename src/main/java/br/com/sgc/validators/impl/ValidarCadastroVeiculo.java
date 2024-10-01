package br.com.sgc.validators.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.dto.VeiculoDto;
import br.com.sgc.entities.Veiculo;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.repositories.VeiculoRepository;
import br.com.sgc.repositories.VisitanteRepository;
import br.com.sgc.validators.Validators;

@Component
public class ValidarCadastroVeiculo implements Validators<VeiculoDto> {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private VisitanteRepository visitanteRepository;
	
	private static final String TITULO = "Cadastro de veículo recusado!";
	
	@Override
	public List<ErroRegistro> validar(VeiculoDto t) throws RegistroException {
		
		RegistroException errors = new RegistroException();
		
		if(t.getId() == null || t.getId() == 0) {
			
			if(t.getPlaca() == null) 
				errors.getErros().add(new ErroRegistro("", TITULO, " Não existem veículos para cadastro!"));
			
			this.veiculoRepository.findByPlaca(t.getPlaca().replace("-", "")).
				ifPresent(res -> errors.getErros().add(new ErroRegistro("", TITULO, "A placa informada (" + t.getPlaca() + ") já existe para o veiculo id " + res.getId() + "!") ));			
			
		}else {
			
			Optional<Veiculo> veiculo = this.veiculoRepository.findById(t.getId());
			
			if(!veiculo.isPresent())
				errors.getErros().add(new ErroRegistro("", TITULO, " Veículo inexistente!"));
			else {
				t.setDataCriacao(veiculo.get().getDataCriacao());
			}
			
			
		}
		
		return errors.getErros();
		
	}

}
