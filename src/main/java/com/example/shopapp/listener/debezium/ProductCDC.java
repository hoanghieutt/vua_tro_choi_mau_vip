//package com.example.shopapp.listener.debezium;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//@Data
//@NoArgsConstructor
//public class ProductCDC {
//
//    @Override
//    public Map<String, Object> getFieldsChanged() {
//        if (Objects.isNull(getBefore()) || Objects.isNull(getAfter())) {
//            return new HashMap<>();
//        }
//
//        return MapUtil.getChanged(
//                MapperUtil.toMapIncludeNullValue(getBefore().toClassRegular()),
//                MapperUtil.toMapIncludeNullValue(getAfter().toClassRegular())
//        );
//    }
//
//    @Data
//    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//    @NoArgsConstructor
//    public static class ClassRegularDto extends ClassRegular {
//
//        public ClassRegular toClassRegular() {
//            var classRegular = new ClassRegular();
//            MapperUtil.getModelMapper().map(this, classRegular);
//            return classRegular;
//        }
//
//        public ClassRegular toClassRegularWithoutNotes() {
//            var classRegular =toClassRegular();
//            classRegular.setNotes(null);
//            return classRegular;
//        }
//    }
//}
