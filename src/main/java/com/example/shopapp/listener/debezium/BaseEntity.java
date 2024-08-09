//package com.example.shopapp.listener.debezium;
//
//import lombok.Data;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.MappedSuperclass;
//import javax.persistence.PrePersist;
//import java.util.UUID;
//
//@Data
//@MappedSuperclass
//public class BaseEntity {
//
//  @javax.persistence.Id
//  @org.springframework.data.annotation.Id
//  private String id;
//
//  @CreatedDate
//  private Long createdAt;
//
//  @CreatedBy
//  private String createdBy;
//
//  @PrePersist
//  private void ensureId() {
//    if (this.getId() == null || this.getId().isEmpty()) {
//      this.setId(UUID.randomUUID().toString());
//    }
//  }
//}
