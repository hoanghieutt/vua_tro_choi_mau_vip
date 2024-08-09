package com.example.shopapp.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Kafkatopic {
    // biến để lưu tên chủ đề Kafka mà sẽ được tạo ra.
    @Value("${application.kafka.topic.create_product}")
    private String topicCreateProduct;

    // giá trị phân vùng
    @Value("${application.kafka.topic.partition-count:3}")
    private int topicPartitionCount;

    // tạo 1 đối tượng "newtopic" thông qua "TopicBuilder"
    @Bean
    public NewTopic topicCreateProduct() {
        return TopicBuilder.name(topicCreateProduct)
                .partitions(topicPartitionCount)
                .build();
    }



}
