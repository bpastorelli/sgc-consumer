package br.com.sgc.mapper;

import org.mapstruct.Mapper;

import br.com.sgc.dto.VisitanteDto;
import br.com.sgc.entities.Visitante;

@Mapper(componentModel = "spring")
public abstract class VisitanteMapper {
	
	public abstract Visitante visitanteDtoToVisitante(VisitanteDto dto); 
}
