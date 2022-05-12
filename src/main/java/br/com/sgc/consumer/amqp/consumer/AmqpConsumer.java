package br.com.sgc.consumer.amqp.consumer;

public interface AmqpConsumer<T> {
	void consumer(T t);
}
