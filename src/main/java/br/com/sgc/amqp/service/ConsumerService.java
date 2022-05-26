package br.com.sgc.amqp.service;

public interface ConsumerService<T> {

	void action(T message) throws Exception;
	
}
