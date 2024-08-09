package com.example.shopapp.listener.debezium;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class BaseCDC<T extends BaseEntity> {
  
  private T before;
  private T after;
  
  @JsonProperty("op")
  private String operation;

  public Map<String, Object> getFieldsChanged() {
    return new HashMap<>();
  }
}
