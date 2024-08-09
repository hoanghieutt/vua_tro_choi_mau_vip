package com.example.shopapp.services.impl;


import com.example.shopapp.services.RabbitmqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RabbitmqServiceImpl implements RabbitmqService {

  private final RabbitTemplate rabbitTemplate;
  private final RabbitTemplate rabbitTemplateSimple;

  public RabbitmqServiceImpl(RabbitTemplate rabbitTemplate, RabbitTemplate rabbitTemplateSimple) {
    this.rabbitTemplate = rabbitTemplate;
    this.rabbitTemplateSimple = rabbitTemplateSimple;
  }

  @Override
  public void push(String queue, String message) {
    log.info("(push)queue: {}, message: {}", queue, message);
    rabbitTemplateSimple.convertAndSend(queue, message);
  }

  @Override
  public void push(String queue, Object message) {
    log.info("(push)queue: {}, message: {}", queue, message);
    rabbitTemplate.convertAndSend(queue, message);
  }

  @Override
  public void push(String queue, List<Object> messages) {
    log.info("(push)queue: {}, messages: {}", queue, messages.size());
    for (Object message : messages) {
      rabbitTemplate.convertAndSend(queue, message);
    }
  }

}
