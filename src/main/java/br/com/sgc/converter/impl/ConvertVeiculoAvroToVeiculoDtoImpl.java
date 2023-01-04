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
				.placa(avro.getPlaca().toString().replace("-", "").toUpperCase())
				.marca(avro.getMarca().toString().toUpperCase())
				.modelo(avro.getModelo().toString().toUpperCase())
				.cor(avro.getCor().toString().toUpperCase())
				.ano(avro.getAno())
				.posicao(avro.getPosicao())
				.visitanteId(avro.getVisitanteId())
				.ticketVisitante(avro.getTicketVisitante() != null ? avro.getTicketVisitante().toString() : null)
				.guide(avro.getGuide() != null ? avro.getGuide().toString() : null)
				.build();
		
		return veiculoDto;
	}

}
