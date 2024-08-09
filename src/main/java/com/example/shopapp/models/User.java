package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname",nullable = false,length = 355)
    private String fullname;

    @Column(name = "phone_number",nullable = false,length = 355)
    private String phoneNumber;

    @Column(name = "address",nullable = false,length = 355)
    private String address;

    @Column(name = "password",nullable = false,length = 355)
    private String password;

    private Boolean active;

    @Column(name = "date_of_birth",nullable = false,length = 355)
    private Date dateOfBirth;

    @Column(name = "facebook_account_id",nullable = false,length = 355)
    private int facebookAaccountId;

    @Column(name = "google_account_id",nullable = false,length = 355)
    private int googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;






}
