package br.com.sgc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sgc.MoradorAvro;
import br.com.sgc.dto.MoradorDto;
import br.com.sgc.dto.ProcessoCadastroDto;
import br.com.sgc.entities.Morador;
import br.com.sgc.entities.VinculoResidencia;

@Mapper(componentModel = "spring")
public abstract class MoradorMapper {
	

	public abstract Morador moradorDtoToMorador(MoradorDto dto);
	
	public abstract MoradorDto moradorToMoradorDto(Morador morador);

	public abstract List<MoradorDto> listMoradorToListMoradorDto(List<Morador> moradores);
	
	public abstract List<Morador> listMoradorDtoToListMorador(List<MoradorDto> moradoresDto);
	
	public abstract MoradorAvro moradorDtoToMoradorAvro(MoradorDto dto);
	
	@Mapping(target = "morador.id", source = "dto.id")
	@Mapping(target = "morador.nome", source = "dto.nome")
	@Mapping(target = "morador.cpf", source = "dto.cpf")
	@Mapping(target = "morador.rg", source = "dto.rg")
	@Mapping(target = "morador.email", source = "dto.email")
	@Mapping(target = "morador.telefone", source = "dto.telefone")
	@Mapping(target = "morador.celular", source = "dto.celular")
	@Mapping(target = "morador.guide", source = "dto.guide")
	@Mapping(target = "morador.senha", source = "dto.senha")
	@Mapping(target = "morador.perfil", source = "dto.perfil")
	@Mapping(target = "morador.associado", source = "dto.associado")
	@Mapping(target = "morador.posicao", source = "dto.posicao")
	@Mapping(target = "morador.dataCriacao", source = "dto.dataCriacao")
	@Mapping(target = "residencia.id", source = "dto.residenciaId")
	public abstract VinculoResidencia moradorDtoToVinculoResidencia(MoradorDto dto);
	
	public abstract VinculoResidencia processoCadastroDtoToVinculoResidencia(ProcessoCadastroDto dto);

}
