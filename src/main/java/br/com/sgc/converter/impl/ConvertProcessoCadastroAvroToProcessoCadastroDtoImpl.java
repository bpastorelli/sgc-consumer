package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.PerfilEnum;
import br.com.sgc.ProcessoCadastroAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.dto.ProcessoCadastroDto;
import br.com.sgc.dto.ResidenciaDto;

@Component
public class ConvertProcessoCadastroAvroToProcessoCadastroDtoImpl implements ConvertAvroToObject<ProcessoCadastroDto, ProcessoCadastroAvro> {
	
	@Override
	public ProcessoCadastroDto convert(ProcessoCadastroAvro avro) {
		
		MoradorDto morador = MoradorDto.builder()
				.id(avro.getMorador().getId())
				.nome(avro.getMorador().getNome().toString().toUpperCase())
				.rg(avro.getMorador().getRg().toString())
				.cpf(avro.getMorador().getCpf().toString())
				.email(avro.getMorador().getEmail().toString().toLowerCase())
				.perfil(avro.getMorador().getPerfil() != null ? avro.getMorador().getPerfil() : PerfilEnum.ROLE_USUARIO)
				.associado(avro.getMorador().getAssociado())
				.telefone(avro.getMorador().getTelefone().toString())
				.celular(avro.getMorador().getCelular().toString())
				.guide(avro.getGuide().toString())
				.posicao(1L)
				.build();
		
		ResidenciaDto residencia = ResidenciaDto.builder()
				.id(avro.getResidencia().getId())
				.endereco(avro.getResidencia().getEndereco().toString().toUpperCase())
				.numero(avro.getResidencia().getNumero())
				.complemento(avro.getResidencia().getComplemento().toString().toUpperCase())
				.bairro(avro.getResidencia().getBairro().toString().toUpperCase())
				.cep(avro.getResidencia().getCep().toString())
				.cidade(avro.getResidencia().getCidade().toString().toUpperCase())
				.uf(avro.getResidencia().getUf().toString().toUpperCase())
				.guide(avro.getGuide().toString())
				.build();				
		
		ProcessoCadastroDto processoDto = ProcessoCadastroDto
				.builder()
				.morador(morador)
				.residencia(residencia)
				.guide(avro.getGuide().toString())
				.build();
		
		return processoDto;
	}

}
