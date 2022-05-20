package br.com.sgc.amqp.service;

import br.com.sgc.errorheadling.RegistroException;

public interface ConsumerService<T> {

	void action(T message) throws RegistroException;
	
}
