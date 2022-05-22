package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.VisitaAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.VeiculoVisitaDto;
import br.com.sgc.dto.VisitaDto;

@Component
public class ConvertVisitaAvroToVisitaDtoImpl implements ConvertAvroToObject<VisitaDto, VisitaAvro> {

	@Override
	public VisitaDto convert(VisitaAvro avro) {
		
		VeiculoVisitaDto veiculoVisita = VeiculoVisitaDto
				.builder()
				.marca(avro.getVeiculoVisita().getMarca().toString().toUpperCase())
				.modelo(avro.getVeiculoVisita().getModelo().toString().toUpperCase())
				.cor(avro.getVeiculoVisita().getCor().toString().toUpperCase())
				.ano(avro.getVeiculoVisita().getAno())
				.build();
		
		VisitaDto visitaDto = VisitaDto
				.builder()
				.guide(avro.getGuide().toString())
				.rg(avro.getRg().toString())
				.placa(avro.getPlaca().toString().toUpperCase())
				.residenciaId(avro.getResidenciaId())
				.veiculoVisita(veiculoVisita)
				.build();
		
		return visitaDto;
	}

}
