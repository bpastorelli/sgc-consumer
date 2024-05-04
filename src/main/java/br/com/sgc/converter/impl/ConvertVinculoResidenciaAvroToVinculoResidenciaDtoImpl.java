package br.com.sgc.converter.impl;

import org.springframework.stereotype.Component;

import br.com.sgc.VinculoResidenciaAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.dto.VinculoResidenciaDto;

@Component
public class ConvertVinculoResidenciaAvroToVinculoResidenciaDtoImpl implements ConvertAvroToObject<VinculoResidenciaDto, VinculoResidenciaAvro> {

	@Override
	public VinculoResidenciaDto convert(VinculoResidenciaAvro avro) {
		
		VinculoResidenciaDto vinculoDto = VinculoResidenciaDto
				.builder()
				.moradorId(avro.getMoradorId())
				.residenciaId(avro.getResidenciaId())
				.guide(avro.getGuide().toString())
				.build();
		
		return vinculoDto;
	}

}
