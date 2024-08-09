package com.example.shopapp.listener.rabbitmq;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductRabbitmqListener {

    private final IProductService productService;

    @RabbitListener(queues = "${application.rabbitmq.queue.create_product}")
    public void rabbitmqCreateProduct(Message message) {
        if (Objects.isNull(message)) {
            return;
        }

        try {
            log.info("(rabbitmqCreateProduct)message: {}", message);
            ProductDTO product = new ObjectMapper().readValue(message.getBody(), ProductDTO.class);
            log.info("(rabbitmqCreateProduct)product: {}", product);
            productService.createProduct(product);
        } catch (Exception exception) {
            log.error(
                    "(rabbitmqCreateProduct)message: {}, exception: {}",
                    message,
                    ExceptionUtils.getStackTrace(exception)
            );
        }
    }

}
