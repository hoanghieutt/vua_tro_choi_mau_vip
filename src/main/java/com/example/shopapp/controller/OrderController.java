package com.example.shopapp.controller;

import com.example.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createorder(@Valid @RequestBody OrderDTO orderDTO,
                                         BindingResult result
    ){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok("createorder successfully");
        }catch (Exception e){
            return   ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    @GetMapping("/{user_id}") // theem bien duong dan "user_id"
    public  ResponseEntity<?> getuser(@Valid @PathVariable("user_id") Long userid ){
        try {
            return ResponseEntity.ok("lay selece order tu user_id ");
        }catch (
            Exception e
        ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long id,
                                         @Valid @RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok("cap nhat thong tin 1 Order ");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable("id") Long id){
        // xoa mem cap nhat truong axtive = false
        return ResponseEntity.ok("order deleted successfully");
    }
}
