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
				.endereco(avro.getEndereco().toString())
				.numero(avro.getNumero())
				.complemento(avro.getComplemento().toString())
				.bairro(avro.getBairro().toString())
				.cep(avro.getCep().toString())
				.cidade(avro.getCidade().toString())
				.uf(avro.getUf().toString())
				.guide(avro.getGuide().toString())
				.ticketMorador(avro.getTicketMorador().toString())
				.build();
		
		return moradorDto;
	}

}
