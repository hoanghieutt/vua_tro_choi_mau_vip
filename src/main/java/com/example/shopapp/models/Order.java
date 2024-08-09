package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname",length = 100)
    private String fullname;

    @Column(name = "email",length = 100)
    private String email;

    @Column(name = "phone_number",length = 100)
    private String phoneNumber;

    @Column(name = "address",length = 100)
    private String address;


    @Column(name = "note",length = 100)
    private String note;


    @Column(name = "order_date",length = 100)
    private LocalDateTime orderDate;

     @Column(name = "status")
    private String status;

     @Column(name = "total_money")
    private Integer totalMoney;

     @Column(name = "shipping_method")
    private String shippingMethod;

     @Column(name = "shipping_address")
    private String shippingAddress;

     @Column(name = "shipping_date")
    private String shippingDate;

     @Column(name = "tracking_number")
    private String trackingNumber;

     @Column(name = "payment_method")
    private String paymentMethod;

     @Column(name = "active")
    private Boolean active; // thuoc ve admin






}
