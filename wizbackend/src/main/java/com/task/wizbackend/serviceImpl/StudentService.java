package com.task.wizbackend.serviceImpl;

import com.task.wizbackend.exception.LoginFailedException;
import com.task.wizbackend.iservice.IStudentService;
import com.task.wizbackend.model.Student;
import com.task.wizbackend.repository.StudentRepo;
import com.task.wizbackend.util.CookieUtil;
import com.task.wizbackend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
}
