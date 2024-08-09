package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Social_accounts")
@Entity
@Builder
public class SocialAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider",nullable = false,length = 20)
    private String provider;

    @Column(name = "provider_id",nullable = false,length = 20)
    private String providerId;

    @Column(name = "email",length = 150)
    private String email;

    @Column(name = "name",length = 150)
    private String name;
}
