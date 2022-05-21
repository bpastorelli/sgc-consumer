package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.VeiculoAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.VeiculoDto;

@Component
public class ConvertVeiculoAvroToVeiculoDtoImpl implements ConvertAvroToObject<VeiculoDto, VeiculoAvro> {

	@Override
	public VeiculoDto convert(VeiculoAvro avro) {
		
		VeiculoDto veiculoDto = VeiculoDto
				.builder()
				.placa(avro.getPlaca().toString())
				.marca(avro.getMarca().toString())
				.modelo(avro.getModelo().toString())
				.cor(avro.getCor().toString())
				.ano(avro.getAno())
				.visitanteId(avro.getVisitanteId())
				.ticketVisitante(avro.getTicketVisitante().toString())
				.guide(avro.getGuide().toString())
				.build();
		
		return veiculoDto;
	}

}
