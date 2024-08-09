//package com.example.shopapp.controller;
//
//import com.example.shopapp.document.modol.student;
//import com.example.shopapp.http_reponses.SuccessResponse;
//import com.example.shopapp.services.impl.StudentService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@RequiredArgsConstructor
//@Slf4j
//@RestController
//@RequestMapping("${api.prefix}/student")
//public class StudentController {
//    private final StudentService studentService;
//
//    @GetMapping("search")
//    public ResponseEntity<?> searchstudent(@RequestParam(defaultValue = "", value = "keyword") String keyword) {
//        if (keyword.isEmpty()) {
//            return ResponseEntity.ok(new SuccessResponse<>("keyword nothing"));
//        }
//        List<student> students = studentService.findByName(keyword);
//        return ResponseEntity.ok().body(new SuccessResponse<>(students));
//    }
//    @GetMapping("")
//    public ResponseEntity<List<student>> findAlldata(){
//        List<student> students = studentService.findAll();
//        return ResponseEntity.ok().body(students);
//    }
//}
