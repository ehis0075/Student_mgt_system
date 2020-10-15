package com.example.demo1.controllerApi;

import com.example.demo1.models.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private static  final List<Student> STUDENTS = Arrays.asList(
         new Student(1, "james bond"),
            new Student(2, "sarah ali"),
            new Student(3, "joi vee")
            );

    @GetMapping(path = "/{studentId}")
    public  Student getStudentById(@PathVariable("studentId") Integer studentId){
        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student "+ studentId+ "does not exist"));

    }
}
