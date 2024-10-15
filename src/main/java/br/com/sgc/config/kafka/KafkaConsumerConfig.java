package br.com.sgc.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Bean
	public StringJsonMessageConverter jsonConverter() {
	    return new StringJsonMessageConverter();
	}
	
}
