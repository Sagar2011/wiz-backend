package com.task.wizbackend.repository;

import com.task.wizbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findByUsername(String username);
    Student findByEmail(String email);
}
