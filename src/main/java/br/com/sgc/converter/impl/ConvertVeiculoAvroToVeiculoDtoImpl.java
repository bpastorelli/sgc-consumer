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
				.id(avro.getId())
				.placa(avro.getPlaca().toString().replace("-", "").toUpperCase())
				.marca(avro.getMarca().toString().toUpperCase())
				.modelo(avro.getModelo().toString().toUpperCase())
				.cor(avro.getCor().toString().toUpperCase())
				.ano(avro.getAno())
				.visitanteId(avro.getVisitanteId() != null ? avro.getVisitanteId() : 0)
				.ticketVisitante(avro.getTicketVisitante() != null ? avro.getTicketVisitante().toString() : null)
				.guide(avro.getGuide().toString())
				.posicao(avro.getPosicao())
				.build();
		
		return veiculoDto;
	}

}
