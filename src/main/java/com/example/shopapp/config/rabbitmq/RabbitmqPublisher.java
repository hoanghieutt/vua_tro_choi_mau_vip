package com.example.shopapp.config.rabbitmq;


import com.example.shopapp.services.RabbitmqService;
import com.example.shopapp.services.impl.RabbitmqServiceImpl;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitmqPublisher {


    @Bean
    public RabbitmqService rabbitmqService(
            RabbitTemplate rabbitTemplate, RabbitTemplate rabbitTemplateSimple
    ) {
        return new RabbitmqServiceImpl(rabbitTemplate, rabbitTemplateSimple);
    }

    @Bean(name = "rabbitTemplate")
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        return template;
    }

    @Bean(name = "rabbitTemplateSimple")
    public RabbitTemplate rabbitTemplateSimple(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new SimpleMessageConverter());

        return template;
    }

}
