package com.example.shopapp.services;

import java.util.List;

public interface RabbitmqService {

  void push(String queue, String message);

  void push(String queue, Object message);

  void push(String queue, List<Object> messages);

}
