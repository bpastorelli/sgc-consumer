package br.com.sgc.config.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	  @Autowired
	  private KafkaProperties properties;

	  @Bean
	  public ConsumerFactory<String, String> consumerFactory() {
	      return new DefaultKafkaConsumerFactory<>(
	              properties.buildConsumerProperties());
	  }

	  @Bean
	  public ConcurrentKafkaListenerContainerFactory<String, String>
	      kafkaListenerContainerFactory() {

	      ConcurrentKafkaListenerContainerFactory<String, String> listener = 
	            new ConcurrentKafkaListenerContainerFactory<>();

	      listener.setConsumerFactory(consumerFactory());

	      // Não falhar, caso ainda não existam os tópicos para consumo
	      listener.getContainerProperties()
	          .setMissingTopicsFatal(false);

	      // ### AQUI
	      // Commit manual do offset
	      listener.getContainerProperties().setAckMode(AckMode.MANUAL);

	      // ### AQUI
	      // Commits síncronos
	      listener.getContainerProperties().setSyncCommits(Boolean.TRUE);

	      return listener;
	    }
	
}
