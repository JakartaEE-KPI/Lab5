package com.kpi.journalrest.repository;

import com.kpi.journalrest.entity.Journal;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JournalRepository {
    @PersistenceContext
    EntityManager entityManager;

    public boolean existById(Long id) {
        return entityManager.createQuery(
                        "select count(j) from Journal j where j.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

}
