//package com.example.shopapp.services.impl;
//
//import com.example.shopapp.document.modol.student;
//import com.example.shopapp.document.service.BaseServiceDocumenImpl;
//import com.example.shopapp.repositoriesmongo.StudentRepository;
//import com.example.shopapp.services.IStudentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@Service
//public class StudentService extends BaseServiceDocumenImpl<student> implements IStudentService {
//
//    private final MongoTemplate mongoTemplate;
//    private final StudentRepository studentRepository;
//
//    public StudentService( MongoTemplate mongoTemplate, MongoTemplate mongoTemplate1, StudentRepository studentRepository) {
//        super(student.class, mongoTemplate);
//        this.mongoTemplate = mongoTemplate1;
//        this.studentRepository = studentRepository;
//
//    }
//    @Override
//    public List<student> findByName(String name) {
//        var query = Query.query(Criteria.where("name").is(name));
//        List<student> students = mongoTemplate.find(query,student.class);
//        return students;
//    }
//
//    @Override
//    public List<student> findAll() {
//        return studentRepository.findAll();
//    }
//    @Override
//    public void deleteById(String id) {
//
//    }
//
//}
