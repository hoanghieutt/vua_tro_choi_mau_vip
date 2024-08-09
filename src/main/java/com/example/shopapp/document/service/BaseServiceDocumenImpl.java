//package com.example.shopapp.document.service;
//
//import com.example.shopapp.document.modol.BaseEntity;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import java.util.List;
//
//public class BaseServiceDocumenImpl<T extends BaseEntity> implements IBaseDocumentSevice<T> {
//
//    protected final Class<T> tClass;
//    protected final MongoTemplate mongoTemplate;
//
//    public BaseServiceDocumenImpl(Class<T> tClass, MongoTemplate mongoTemplate) {
//        this.tClass = tClass;
//        this.mongoTemplate = mongoTemplate;
//    }
//
//
//    @Override
//    public List<T> findAll() {
//        return mongoTemplate.findAll(tClass);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        mongoTemplate.remove(mongoTemplate.findById(id, tClass));
//    }
//}
