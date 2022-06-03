package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.ResidenciaAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.ResidenciaDto;

@Component
public class ConvertResidenciaAvroToResidenciaDtoImpl implements ConvertAvroToObject<ResidenciaDto, ResidenciaAvro> {

	@Override
	public ResidenciaDto convert(ResidenciaAvro avro) {
		
		ResidenciaDto moradorDto = ResidenciaDto
				.builder()
				.id(avro.getId())
				.endereco(avro.getEndereco().toString().toUpperCase())
				.numero(avro.getNumero())
				.complemento(avro.getComplemento().toString().toUpperCase())
				.bairro(avro.getBairro().toString().toUpperCase())
				.cep(avro.getCep().toString())
				.cidade(avro.getCidade().toString().toUpperCase())
				.uf(avro.getUf().toString().toUpperCase())
				.guide(avro.getGuide().toString())
				.ticketMorador(avro.getTicketMorador().toString())
				.build();
		
		return moradorDto;
	}

}
