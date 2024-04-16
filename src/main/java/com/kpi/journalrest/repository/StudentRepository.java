package com.kpi.journalrest.repository;

import com.kpi.journalrest.entity.Student;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRepository {

    @PersistenceContext(unitName = "demo1")
    EntityManager entityManager;

    public boolean existById(Long id) {
        return entityManager.createQuery(
                        "select count(s) from Student s where s.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

}
