package com.kpi.journalrest.service;

import com.kpi.journalrest.entity.Student;
import com.kpi.journalrest.repository.StudentRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentService {

    @EJB
    StudentRepository studentRepository;

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public boolean existById(Long id) {
        return studentRepository.existById(id);
    }
}
