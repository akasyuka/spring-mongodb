package com.example.demo.api;


import com.example.demo.dao.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    StudentService studentService;

    @GetMapping("/")
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

}
