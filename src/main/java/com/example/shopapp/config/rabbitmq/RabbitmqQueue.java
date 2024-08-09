package com.example.shopapp.config.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// lớp cấu hình của bạn để định nghĩa hàng đợi RabbitMQ.
@Configuration
public class RabbitmqQueue {
    @Value("${application.rabbitmq.queue.create_product}")
    private String queueCreateProduct;

// trả về một đối tượng Queue mới với tên được lấy từ biến queueCreateProduct
    @Bean
    public Queue queueCreateProduct(){
        return new Queue(queueCreateProduct);
    }

}
