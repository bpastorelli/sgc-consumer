package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.VisitanteAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.VisitanteDto;

@Component
public class ConvertVisitanteAvroToVisitanteDtoImpl implements ConvertAvroToObject<VisitanteDto, VisitanteAvro> {

	@Override
	public VisitanteDto convert(VisitanteAvro avro) {
		
		VisitanteDto visitanteDto = VisitanteDto
				.builder()
				.nome(avro.getNome().toString().toUpperCase())
				.cpf(avro.getCpf().toString())
				.rg(avro.getRg().toString())
				.cep(avro.getCep().toString())
				.endereco(avro.getEndereco().toString().toUpperCase())
				.numero(avro.getNumero().toString())
				.bairro(avro.getBairro().toString())
				.complemento(avro.getComplemento().toString().toUpperCase())
				.cidade(avro.getCidade().toString().toUpperCase())
				.uf(avro.getUf().toString().toUpperCase())
				.telefone(avro.getTelefone().toString())
				.celular(avro.getCelular().toString())
				.guide(avro.getGuide().toString())
				.build();
		
		return visitanteDto;
	}

}
