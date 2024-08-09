package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_detail")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetai(@Valid @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok("createorderdetail here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getorderdetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("getOrderDetail with id :"+id);
    };

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getorderdetails(@Valid @PathVariable("orderId") Long orderId
    ){
        return ResponseEntity.ok("getOrderDetails with orderId :"+orderId);
    };

    @PutMapping("{id}")
    public ResponseEntity<?> updateOrderdetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ){
        return ResponseEntity.ok("updateOrderDetail id :"+id+"form:"+orderDetailDTO);
    };
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id){
        return ResponseEntity.noContent().build();
    };

}
