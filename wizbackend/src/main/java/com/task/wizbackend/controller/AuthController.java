package com.task.wizbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.wizbackend.iservice.IStudentService;
import com.task.wizbackend.model.ResponseModel;
import com.task.wizbackend.model.Student;
import com.task.wizbackend.util.CookieUtil;
import com.task.wizbackend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@Controller
public class AuthController {

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private ResponseModel responseModel;
    static String domain;
    static String jwtTokenCookieName;
    @Value("${Domain}")
    public void setDomain(String Domain) {
        domain = Domain;
    }

    @Value("${cookie_name}")
    public void setJwtTokenCookieName(String cookieName) {
        jwtTokenCookieName = cookieName;
    }




    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/login")
    public ResponseEntity<?>  login(@RequestHeader String username, @RequestHeader String password, HttpServletResponse httpServletResponse){
      try{  Student student = iStudentService.getStudent(username,password);
        String jwtToken = JwtUtil.addToken(student);
          responseModel.setStatusCode(200);
          responseModel.setMessage("registered");
          ArrayList<String> result = new ArrayList<>();
          result.add(jwtToken);
          responseModel.setResults(result);
          return new ResponseEntity<ResponseModel>(responseModel,HttpStatus.OK);
    }catch (Exception ex){
            logger.error("exception occured at {}", ex.getMessage());
            return null;
      }
    }

    @PostMapping("/student")
    public ResponseEntity<?> registerStudent(@RequestBody Student student, HttpServletResponse httpServletResponse){
        try{
            iStudentService.saveStudent(student);
            String jwtToken = JwtUtil.addToken(student);
            responseModel.setStatusCode(200);
            responseModel.setMessage("registered");
            ArrayList<String> result = new ArrayList<>();
            result.add(jwtToken);
            responseModel.setResults(result);
            return new ResponseEntity<ResponseModel>(responseModel,HttpStatus.OK);
        }catch (Exception ex){
            logger.error("exception occured at {}", ex.getMessage());
            return null;
        }
    }

}
