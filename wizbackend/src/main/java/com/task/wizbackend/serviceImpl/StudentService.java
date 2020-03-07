package com.task.wizbackend.serviceImpl;

import com.task.wizbackend.exception.LoginFailedException;
import com.task.wizbackend.iservice.IStudentService;
import com.task.wizbackend.model.Student;
import com.task.wizbackend.repository.StudentRepo;
import com.task.wizbackend.util.CookieUtil;
import com.task.wizbackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepo studentRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Value("${cookie_name}")
    private static String jwtTokenCookieName;

    @Override
    public void saveStudent(Student student) throws DuplicateKeyException{
        if(studentRepo.findByUsername(student.getUsername())==null && studentRepo.findByEmail(student.getEmail())==null){
            student.setCreatedAt(new Date());
            student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
            studentRepo.save(student);
        } else {
            logger.error("exception occured at:: {}", new Date());
            throw new DuplicateKeyException("Duplicate key found");
        }
    }

    @Override
    public Student getStudent(String username, String password) throws LoginFailedException {
        Student student = studentRepo.findByUsername(username);
        if(student!=null){
            boolean flag = bCryptPasswordEncoder.matches(password,student.getPassword());
            if(flag){
                return studentRepo.findByUsername(username);
            } else {
                logger.error("exception occured at:: {}", new Date());
                throw new LoginFailedException("Authentication failed");
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }


    // For getting the user profile using username from the database
    @Override
    public Student loadByUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user;
            try {
                Claims claims = Jwts.parser().setSigningKey("W!z-$ecret").parseClaimsJws(token.replace("Bearer ", "")).getBody();
                user = claims.get("un", String.class);
                if (user != null) {
                    return studentRepo.findByUsername(user);
                }
            } catch (ExpiredJwtException expiredException) {
                logger.error("Expired Exception");
            } catch (Exception exception) {
                logger.error("Jwt Token Error", exception);
            }
        }
        return null;
    }
}
