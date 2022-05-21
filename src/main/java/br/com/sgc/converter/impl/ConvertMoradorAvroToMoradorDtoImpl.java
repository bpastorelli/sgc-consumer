package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.MoradorAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.PerfilEnum;

@Component
public class ConvertMoradorAvroToMoradorDtoImpl implements ConvertAvroToObject<MoradorDto, MoradorAvro> {

	@Override
	public MoradorDto convert(MoradorAvro avro) {
		
		MoradorDto moradorDto = MoradorDto
				.builder()
				.id(avro.getId())
				.nome(avro.getNome().toString().toUpperCase())
				.cpf(avro.getCpf().toString())
				.rg(avro.getRg().toString())
				.email(avro.getEmail().toString().toLowerCase())
				.perfil(avro.getPerfil() != null ? avro.getPerfil() : PerfilEnum.ROLE_USUARIO)
				.associado(avro.getAssociado())
				.telefone(avro.getTelefone().toString())
				.celular(avro.getCelular().toString())
				.residenciaId(avro.getResidenciaId())
				.guide(avro.getGuide().toString())
				.build();
		
		return moradorDto;
	}

}
