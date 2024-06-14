package br.com.sgc.converter.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.sgc.ContribuicaoAvro;
import br.com.sgc.converter.ConvertAvroToObject;
import br.com.sgc.entities.Lancamento;
import br.com.sgc.entities.Morador;
import br.com.sgc.entities.Residencia;
import br.com.sgc.utils.Utils;

@Component
public class ConvertContribuicaoAvroToListLancamento implements ConvertAvroToObject<List<Lancamento>, ContribuicaoAvro> {
	
	@Override
	public List<Lancamento> convert(ContribuicaoAvro contribuicao) {
		

		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		contribuicao.getLancamentos().forEach(l -> {
			
			Morador morador = Morador.builder()
					.id(l.getMoradorId())
					.build();
			
			Residencia residencia = Residencia.builder()
					.id(l.getResidenciaId())
					.build();
			
			Lancamento lancamento = new Lancamento();
			lancamento.setId(l.getId() == null ? 0 : l.getId());
			lancamento.setDataPagamento(Utils.convertToLocalDateTime(l.getDataPagamento()));
			lancamento.setDataCriacao(LocalDate.now());
			lancamento.setPeriodo(l.getPeriodo().toString());
			lancamento.setDocumento(l.getDocumento().toString());
			lancamento.setMorador(morador);
			lancamento.setResidencia(residencia);
			lancamento.setValor(BigDecimal.valueOf(l.getValor()));
			lancamento.setRequisicaoId(contribuicao.getRequisicaoId().toString());
			lancamento.setPage(contribuicao.getPage());
			lancamento.setTotalPages(contribuicao.getTotalPages());
			
			lancamentos.add(lancamento);
			
		});
		
		
		return lancamentos;
	}

}
