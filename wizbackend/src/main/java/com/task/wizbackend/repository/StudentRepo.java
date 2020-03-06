package com.task.wizbackend.repository;

import com.task.wizbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, String> {
    Student findByUsername(String username);
    Student findByPassword(String password);
    Student findByEmail(String email);
}
