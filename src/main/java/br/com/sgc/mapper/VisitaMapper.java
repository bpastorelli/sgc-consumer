package br.com.sgc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sgc.VeiculoAvro;
import br.com.sgc.dto.VisitaDto;
import br.com.sgc.entities.Visita;

@Mapper(componentModel = "spring")
public abstract class VisitaMapper {
	
	public abstract VeiculoAvro veiculoDtoToVeiculoAvro(VisitaDto dto);
	
	@Mapping(target = "residencia.id", source = "residenciaId")
	public abstract Visita visitaDtoToVisita(VisitaDto dto); 
}
