package com.example.shopapp.config.rabbitmq;

import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqComsumer {

    @Value("${application.rabbitmq.properties.concurrent_consumers:3}")
    private int concurrentConsumers;

    @Value("${application.rabbitmq.properties.max_concurrent_consumers:10}")
    private int maxConcurrentConsumers;

    @Value("${application.rabbitmq.properties.prefetch:1}")
    private int prefetch;

    @Value("${application.rabbitmq.properties.max_attempts:3}")
    private int maxAttempts;

    @Value("${application.rabbitmq.properties.initial_interval:30000}")
    private long initialInterval;

    @Value("${application.rabbitmq.properties.multiplier:2.0}")
    private double multiplier;

    @Value("${application.rabbitmq.properties.max_interval:60000}")
    private long maxInterval;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setPrefetchCount(prefetch);
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxAttempts(maxAttempts)
                        .backOffOptions(initialInterval, multiplier, maxInterval)
                        .build()
        );
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        return factory;
    }
}
