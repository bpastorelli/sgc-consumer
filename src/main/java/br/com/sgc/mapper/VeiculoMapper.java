package br.com.sgc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sgc.dto.VeiculoDto;
import br.com.sgc.dto.VisitaDto;
import br.com.sgc.entities.Veiculo;
import br.com.sgc.entities.VinculoVeiculo;

@Mapper(componentModel = "spring")
public abstract class VeiculoMapper {
	
	public abstract Veiculo veiculoDtoToVeiculo(VeiculoDto dto);
	
	@Mapping(target = "veiculo.id", source = "id")
	@Mapping(target = "visitante.id", source = "visitanteId")
	public abstract VinculoVeiculo veiculoToVinculoVeiculo(Veiculo dto);
	
	@Mapping(target = "placa", source = "placa")
	@Mapping(target = "marca", source = "veiculoVisita.marca")
	@Mapping(target = "modelo", source = "veiculoVisita.modelo")
	@Mapping(target = "cor", source = "veiculoVisita.cor")
	@Mapping(target = "ano", source = "veiculoVisita.ano")
	public abstract Veiculo visitaDtoToVeiculo(VisitaDto dto);
}
