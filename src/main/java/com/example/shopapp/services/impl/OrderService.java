package com.example.shopapp.services.impl;

import com.example.shopapp.Responses.OrderResponse;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
     private final UserRepository userRepository;
     private final OrderRepository orderRepository;
//     private final ModelMapper mapper;
    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        // tìm xem User_id có tồn tạo hay ko
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("ko tìm thấy User with id ="+orderDTO.getUserId()));
        // convert OderDOT => order
        // tạo một luồng ánh xạ riêng để kiểm soát việc ánh xạ

        return null;
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(long id) {

    }

    @Override
    public List<OrderResponse> getAllOrder(Long userId) {
        return null;
    }
}
