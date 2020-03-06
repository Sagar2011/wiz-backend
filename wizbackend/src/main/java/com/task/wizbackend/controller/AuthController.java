package com.task.wizbackend.controller;

import com.task.wizbackend.iservice.IStudentService;
import com.task.wizbackend.model.Student;
import com.task.wizbackend.util.CookieUtil;
import com.task.wizbackend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private IStudentService iStudentService;
    static String domain;
    static String jwtTokenCookieName;
    static String redirectUrl;
    @Value("${Domain}")
    public void setDomain(String Domain) {
        domain = Domain;
    }

    @Value("${cookie_name}")
    public void setJwtTokenCookieName(String cookieName) {
        jwtTokenCookieName = cookieName;
    }

    @Value("${redirect-url}")
    public void setUrl(String url) {
       redirectUrl = url;
    }


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/login")
    public RedirectView login(@RequestHeader String username, @RequestHeader String password, HttpServletResponse httpServletResponse){
      try{  Student student = iStudentService.getStudent(username,password);
        String jwtToken = JwtUtil.addToken(student);
        Cookie cookie = CookieUtil.create(httpServletResponse, jwtTokenCookieName, jwtToken, false, -1, domain);
        RedirectView redirectview = new RedirectView();
        redirectview.setUrl(redirectUrl);
        return redirectview;
    }catch (Exception ex){
            logger.error("exception occured at {}", ex.getMessage());
            return null;
      }
    }

    @PostMapping("/student")
    public RedirectView registerStudent(@RequestBody Student student, HttpServletResponse httpServletResponse){
        try{
            iStudentService.saveStudent(student);
            String jwtToken = JwtUtil.addToken(student);
            Cookie cookie = CookieUtil.create(httpServletResponse, jwtTokenCookieName, jwtToken, false, -1, domain);
            RedirectView redirectview = new RedirectView();
            redirectview.setUrl(redirectUrl);
            return redirectview;
        }catch (Exception ex){
            logger.error("exception occured at {}", ex.getMessage());
            return null;
        }
    }

}
