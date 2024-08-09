package com.example.shopapp.listener.debezium;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

@Slf4j
public class MapperUtil {
  
  private static MapperUtil ourInstance = new MapperUtil();
  
  private ModelMapper modelMapper;
  
  private MapperUtil() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }
  
  private static MapperUtil getInstance() {
    return ourInstance;
  }
  
  public static List<Object> toList(String json) {
    if(json==null || json.isEmpty()){
      return new ArrayList<>();
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, List.class);
    } catch (IOException e) {
      log.error("(toList)json: {}, ex: {}", json, getFullStackTrace(e));
      return new ArrayList<>();
    }
  }
  
  public static List<Object> toList(Object obj) {
    var objJson = toJson(obj);
    return toList(objJson);
  }
  
  /**
   * Convert use camel case
   * Student ( firstName: Peter, lastName: Michael) --> Map( firstName -> Peter, lastName -> Michael) 
   * @param object
   * @return
   */
  public static Map<String, Object> toMap(Object object) {
    return toMap(toJson(object));
  }
  
  public static Map<String, Object> toMap(String json) {
    log.debug("(toMap)json: {}", json);
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, new TypeReference<>() {
      });
    } catch (IOException ex) {
      log.error("(toMap)ex: {}", getFullStackTrace(ex));
      return null;
    }
  }

  public static Map<String, Object> toMapIncludeNullValue(Object object) {
    return toMap(toJsonIncludeNulValue(object));
  }
  
  public static Map<String, Object> toMapExcludeNullValue(Object obj) {
    Map map = new HashMap<>();
    var objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    try {
      var object = objectMapper.writeValueAsString(obj);
      map = objectMapper.readValue(object, Map.class);
    } catch (JsonProcessingException e) {
      log.error("Convert objet error. Message: {}", e.getMessage());
    }
    return map;
  }
  
  public static String toJson(Object object) {
    log.debug("(toJson)object: {}", object);
    if (object == null) {
      return null;
    }
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    try {
      return mapper.writeValueAsString(object);
    } catch (IOException ex) {
      log.error("(toJson)ex: {}", getFullStackTrace(ex));
      return object.toString();
    }
  }

  public static String toJsonIncludeNulValue(Object object) {
    log.debug("(toJson)object: {}", object);
    if (object == null) {
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    try {
      return mapper.writeValueAsString(object);
    } catch (IOException ex) {
      log.error("(toJson)ex: {}", getFullStackTrace(ex));
      return object.toString();
    }
  }
  
  public static ModelMapper getModelMapper() {
    return getInstance().modelMapper;
  }
  
  public static ObjectMapper getMapper() {
    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
