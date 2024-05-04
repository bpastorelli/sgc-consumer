package br.com.sgc.validators.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.PerfilEnum;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.entities.Morador;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.utils.PasswordUtils;
import br.com.sgc.validators.Validators;

@Component
public class ValidarCadastroMorador implements Validators<MoradorDto> {
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@Autowired
	private ResidenciaRepository residenciaRepository;
	
	private static final String TITULO = "Cadastro de morador recusado!";
	
	@Override
	public List<ErroRegistro> validar(MoradorDto t) throws RegistroException {
		
		List<MoradorDto> list = new ArrayList<MoradorDto>();
		list.add(t);
		
		return validar(list);
		
	}
	
	public List<ErroRegistro> validar(List<MoradorDto> t) throws RegistroException {
		
		RegistroException errors = new RegistroException();
		
		if(t.size() == 0) 
			errors.getErros().add(new ErroRegistro("", TITULO, " Você deve informar ao menos um morador"));	
		
		for(MoradorDto morador : t) {
			
			if(morador.getId() == null || morador.getId() == 0) {
				
				morador.setSenha(PasswordUtils.gerarBCrypt(morador.getCpf().substring(0, 6)));
				morador.setPerfil(morador.getPerfil() == null ? PerfilEnum.ROLE_USUARIO : morador.getPerfil());
				morador.setAssociado(morador.getAssociado() == null ? 0 : morador.getAssociado());
				morador.setResidenciaId(morador.getResidenciaId() == null ? 0 : morador.getResidenciaId());
				
				if(morador.getResidenciaId() != null && morador.getResidenciaId() != 0)
					morador.setResidencia(residenciaRepository.findById(morador.getResidenciaId()).get());
			
			}else {
				
				Optional<Morador> moradorSource = this.moradorRepository.findById(morador.getId());
				
				morador.setCpf(moradorSource.get().getCpf());
				morador.setPerfil(moradorSource.get().getPerfil());
				morador.setGuide(moradorSource.get().getGuide());
				morador.setSenha(moradorSource.get().getSenha());
				morador.setResidenciaId(morador.getResidenciaId() == null ? 0 : morador.getResidenciaId());
				morador.setDataCriacao(moradorSource.get().getDataCriacao());
				
			}
		}

		return errors.getErros();
		
	}

}
