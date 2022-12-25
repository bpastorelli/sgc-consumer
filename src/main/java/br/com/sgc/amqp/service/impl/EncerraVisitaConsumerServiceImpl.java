package br.com.sgc.amqp.service.impl;

import java.sql.Time;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgc.amqp.service.ConsumerService;
import br.com.sgc.dto.EncerraVisitaDto;
import br.com.sgc.entities.Visita;
import br.com.sgc.repositories.VisitaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EncerraVisitaConsumerServiceImpl implements ConsumerService<EncerraVisitaDto> {
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@Override
	public void action(EncerraVisitaDto dto) throws Exception {
		
		log.info("Persistindo registro...");
		
		Visita visita = visitaRepository.findById(dto.getId()).get();
		visita.setDataSaida(new Date());
		visita.setHoraSaida(new Time(visita.getDataSaida().getTime()));
		visita.setPosicao(1);
		
		this.visitaRepository.save(visita);
		
	}

}
