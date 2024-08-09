package com.example.shopapp.listener.debezium;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MapUtil {

  private MapUtil(){

  }
  
  public static Map<String, Object> getChanged(
      Map<String, Object> before,
      Map<String, Object> after
  ) {
    log.debug("(getChanged)before: {}", before);
    log.debug("(getChanged)after: {}", after);
    var changeMap = new HashMap<String, Object>();
    before.forEach((key, value) -> {
      if (!Objects.equals(value, after.get(key))) {
        changeMap.put(key, after.get(key));
      }
    });
    
    log.debug("(getChanged)changeMap: {}", changeMap);
    return changeMap;
  }

  public static boolean isChangeField(Map<String, Object> fieldsChange, List<String> fields) {
    if (CollectionUtils.isEmpty(fieldsChange) || CollectionUtils.isEmpty(fields)) {
      return false;
    }
    for (var field : fields) {
      if (fieldsChange.containsKey(field)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isOnlyChangedFields(Map<String, Object> fieldsChange, List<String> fields) {
    if (CollectionUtils.isEmpty(fieldsChange) || CollectionUtils.isEmpty(fields)) {
      return false;
    }
    int count = fieldsChange.size();
    for (var field : fields) {
      if(fieldsChange.containsKey(field))
        count--;
    }
    return count <=0;
  }
}
