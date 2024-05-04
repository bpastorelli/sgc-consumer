package br.com.sgc.validators.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.PerfilEnum;
import br.com.sgc.dto.ProcessoCadastroDto;
import br.com.sgc.entities.Morador;
import br.com.sgc.entities.Residencia;
import br.com.sgc.errorheadling.ErroRegistro;
import br.com.sgc.errorheadling.RegistroException;
import br.com.sgc.mapper.ResidenciaMapper;
import br.com.sgc.repositories.MoradorRepository;
import br.com.sgc.repositories.ResidenciaRepository;
import br.com.sgc.utils.PasswordUtils;
import br.com.sgc.validators.Validators;

@Component
public class ValidarProcessoCadastro implements Validators<ProcessoCadastroDto> {
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@Autowired
	private ResidenciaRepository residenciaRepsository;
	
	@Autowired
	private ResidenciaMapper residenciaMapper;
	
	@Override	
	public List<ErroRegistro> validar(ProcessoCadastroDto t) throws RegistroException {
		
		RegistroException errors = new RegistroException();
		
			
			if(t.getMorador().getId() == null || t.getMorador().getId() == 0) {
				
				t.getMorador().setSenha(PasswordUtils.gerarBCrypt(t.getMorador().getCpf().substring(0, 6)));
				t.getMorador().setPerfil(t.getMorador().getPerfil() == null ? PerfilEnum.ROLE_USUARIO : t.getMorador().getPerfil());
				t.getMorador().setAssociado(t.getMorador().getAssociado() == null ? 0 : t.getMorador().getAssociado());
				
				Optional<Residencia> residencia = residenciaRepsository.findByCepAndNumeroAndComplemento(
						t.getResidencia().getCep()
						, t.getResidencia().getNumero()
						, t.getResidencia().getComplemento());
				if(residencia.isPresent())
					t.setResidencia(this.residenciaMapper.residenciaToResidenciaDto(residencia.get()));

			}else {
				
				Optional<Morador> moradorSource = this.moradorRepository.findById(t.getMorador().getId());
				
				t.getMorador().setCpf(moradorSource.get().getCpf());
				t.getMorador().setPerfil(moradorSource.get().getPerfil());
				t.getMorador().setGuide(moradorSource.get().getGuide());
				t.getMorador().setSenha(moradorSource.get().getSenha());
				t.getMorador().setResidenciaId(t.getMorador().getResidenciaId() == null ? 0 : t.getMorador().getResidenciaId());
				t.getMorador().setDataCriacao(moradorSource.get().getDataCriacao());
				
			}

		return errors.getErros();
		
	}

}
