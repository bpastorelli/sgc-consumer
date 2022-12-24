package br.com.sgc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sgc.dto.VinculoResidenciaDto;
import br.com.sgc.entities.VinculoResidencia;

@Mapper(componentModel = "spring")
public abstract class VinculoResidenciaMapper {
	
	@Mapping(target = "morador.id", source = "moradorId")
	@Mapping(target = "residencia.id", source = "residenciaId")
	public abstract VinculoResidencia visitanteDtoToVisitante(VinculoResidenciaDto dto); 
}
