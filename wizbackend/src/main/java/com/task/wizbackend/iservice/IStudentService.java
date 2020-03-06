package com.task.wizbackend.iservice;

import com.task.wizbackend.exception.LoginFailedException;
import com.task.wizbackend.model.Student;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStudentService {

    void saveStudent(Student student) throws DuplicateKeyException;
    Student getStudent(String username, String password) throws LoginFailedException, LoginFailedException;
    List<Student> getAllStudents();
}
