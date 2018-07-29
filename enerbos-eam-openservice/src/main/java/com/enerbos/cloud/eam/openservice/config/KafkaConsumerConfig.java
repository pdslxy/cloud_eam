package com.enerbos.cloud.eam.openservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author yanzy
 * @version 1.0.0
 * @date 2017/9/28 17:15
 * @Description kafka 消费配置
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	/**
	 * 消费、生产地址
	 */
	@Value("${spring.kafka.address}")
	private String address;
	/**
	 * 消费、生产组的id
	 */
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(4);
		factory.getContainerProperties().setPollTimeout(4000);

		return factory;
	}

	@Bean
	public KafkaWarningRunner kafkaListeners() {
		return new KafkaWarningRunner();
	}

	public ConsumerFactory<String, String> consumerFactory() {

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		return new DefaultKafkaConsumerFactory<String, String>(properties);
	}

}
