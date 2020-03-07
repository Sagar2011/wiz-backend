package com.task.wizbackend.controller;

import com.task.wizbackend.iservice.IStudentService;
import com.task.wizbackend.model.ResponseModel;
import com.task.wizbackend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
public class StudentController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ResponseModel responseModel;

    // For providing user google profile
    @GetMapping("/profile")
    public Student getUserProfile(HttpServletRequest request) {
        Student student = iStudentService.loadByUsername(request);
        return student;
    }

    @GetMapping("/students")
    public ResponseEntity<?> studentsLists(HttpServletRequest request){
        ArrayList<Student> students = (ArrayList<Student>) iStudentService.getAllStudents();
        responseModel.setStatusCode(200);
        responseModel.setMessage("students list");
        responseModel.setResults(students);
        return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
    }
}
