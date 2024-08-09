package com.example.shopapp.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
// kích hoạt tính năng của kafka
@EnableKafka
public class Kafkacomsumer {

    //biến để lưu địa chỉ của các Kafka broker
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    //biến để lưu ID nhóm của Kafka consumer.
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    // chịu trách nhiệm tạo ra các consumer Kafka.
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        // Thiết lập địa chỉ của Kafka broker.
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Thiết lập ID nhóm của Kafka consumer.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // Thiết lập ID nhóm của Kafka consumer.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Thiết lập deserializer cho value của message là StringDeserializer.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // Tắt việc sử dụng thông tin kiểu dữ liệu trong header.
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        // Tạo một đối tượng ConcurrentKafkaListenerContainerFactory.
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // Cấu hình factory này với ConsumerFactory đã được định nghĩa trước đó.
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
