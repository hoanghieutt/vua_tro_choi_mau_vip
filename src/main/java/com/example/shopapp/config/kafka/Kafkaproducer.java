package com.example.shopapp.config.kafka;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Kafkaproducer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String,Object> producerConfigs(){
        // tạo ra một map chứa các cấu hình cần thiết cho Kafka Producer
        Map<String, Object> props = new HashMap<>();
        // Thiết lập địa chỉ của Kafka broker.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Thiết lập serializer cho key của message là StringSerializer
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Thiết lập serializer cho value của message là JsonSerializer.
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Tắt việc thêm thông tin kiểu dữ liệu vào header.
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return props;

    }

    @Bean
    @Primary
    public KafkaTemplate<String, Object> kafkaTemplate() {

        //  tạo ra một đối tượng ProducerFactory
        // một giao diện chịu trách nhiệm tạo ra các nhà sản xuất Kafka.
        ProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>( producerConfigs());

        // trả về một đối tượng KafkaTemplate mới
        //cung cấp các phương thức tiện lợi để gửi tin nhắn đến các chủ đề Kafka.
        return new KafkaTemplate<>(factory);
    }

}
