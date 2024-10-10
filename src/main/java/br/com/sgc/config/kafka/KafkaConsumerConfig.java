package br.com.sgc.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public KafkaListenerContainerFactory<?> jsonContainerFactory(
			ConsumerFactory<? super Integer, ? super String> consumerFactory){
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setMessageConverter(new JsonMessageConverter());
		factory.setConcurrency(1);
		return factory;
	}
	
}
