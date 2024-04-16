package com.kpi.journalrest.repository;

import com.kpi.journalrest.entity.Subject;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectRepository {
    @PersistenceContext
    EntityManager entityManager;

    public Optional<Subject> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Subject.class, id));
    }

    public List<Subject> findByJournalId(Long journalId) {
        return entityManager.createQuery("select s from Subject s where s.journal.id=:journalId", Subject.class)
                .setParameter("journalId", journalId)
                .getResultList();
    }

    public boolean existById(Long id) {
        return entityManager.createQuery(
                        "select count(s) from Subject s where s.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }
}
