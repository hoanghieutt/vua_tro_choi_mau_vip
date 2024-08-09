package com.example.shopapp.services;

import com.example.shopapp.Responses.OrderResponse;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrder(Long id);

    OrderResponse updateOrder(Long id , OrderDTO orderDTO);

    void deleteOrder(long id);

    List<OrderResponse> getAllOrder(Long userId);
}
