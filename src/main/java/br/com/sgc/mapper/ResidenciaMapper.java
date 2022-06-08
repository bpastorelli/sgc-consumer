package br.com.sgc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sgc.dto.ResidenciaDto;
import br.com.sgc.entities.Residencia;
import br.com.sgc.entities.VinculoResidencia;

@Mapper(componentModel = "spring")
public abstract class ResidenciaMapper {
	
	public abstract Residencia residenciaDtoToResidencia(ResidenciaDto dto);
	
	public abstract ResidenciaDto residenciaToResidenciaDto(Residencia residencia);
	
	public abstract VinculoResidencia residenciaToVinculoResidencia(Residencia entity);
	
	@Mapping(target = "residencia.id", source = "dto.id")
	@Mapping(target = "residencia.endereco", source = "dto.endereco")
	@Mapping(target = "residencia.numero", source = "dto.numero")
	@Mapping(target = "residencia.bairro", source = "dto.bairro")
	@Mapping(target = "residencia.cep", source = "dto.cep")
	@Mapping(target = "residencia.cidade", source = "dto.cidade")
	@Mapping(target = "residencia.uf", source = "dto.uf")
	@Mapping(target = "residencia.complemento", source = "dto.complemento")
	@Mapping(target = "residencia.guide", source = "dto.guide")
	public abstract VinculoResidencia residenciaDtoToVinculoResidencia(ResidenciaDto dto);

}
