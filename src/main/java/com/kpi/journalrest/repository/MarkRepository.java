package com.kpi.journalrest.repository;

import com.kpi.journalrest.common.Page;
import com.kpi.journalrest.common.PageRequest;
import com.kpi.journalrest.entity.Mark;
import com.kpi.journalrest.entity.Student;
import com.kpi.journalrest.entity.Subject;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<Mark> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Mark.class, id));
    }

    public List<Mark> findByStudentId(Long studentId) {
        return entityManager.createQuery("select m from Mark m where m.student.id=:studentId", Mark.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public Page<List<Mark>> findStudentSubjectMarks(Long studentId, Long subjectId, Long journalId, PageRequest pageRequest) {
        List<Mark> marks = entityManager.createQuery("""
                        select m from Mark m
                        join m.subject.journal j
                        where m.student.id=:studentId
                            and m.subject.id=:subjectId
                            and j.id=:journalId""", Mark.class)
                .setParameter("studentId", studentId)
                .setParameter("subjectId", subjectId)
                .setParameter("journalId", journalId)
                .setFirstResult((pageRequest.getPage() - 1) * pageRequest.getSize())
                .setMaxResults(pageRequest.getSize())
                .getResultList();

        Long totalCount = entityManager.createQuery("""
                        select count(m) from Mark m
                        join m.subject.journal j
                        where m.student.id=:studentId
                            and m.subject.id=:subjectId
                            and j.id=:journalId""", Long.class)
                .setParameter("studentId", studentId)
                .setParameter("subjectId", subjectId)
                .setParameter("journalId", journalId)
                .getSingleResult();

        return Page.of((int) Math.ceil((double) totalCount / pageRequest.getSize()), totalCount, pageRequest.getPage(), marks);
    }

    public Mark save(Mark mark) {
        return entityManager.merge(mark);
    }

    public void deleteById(Long id) {
        Query query = entityManager.createQuery("delete from Mark m where m.id=:id");
        query.setParameter("id", id).executeUpdate();
    }

    public void delete(Mark mark) {
        entityManager.remove(mark);
    }

    public void testTransaction(Student student, Subject subject, Integer markCount) {
        for (int i = 0; i < markCount; i++) {
            int point = (int) (Math.random() * 100);
            System.out.println(point);
            if (point < 15) {
                throw new RuntimeException("UNEXPECTED");
            }
            this.save(Mark.builder()
                    .student(student)
                    .subject(subject)
                    .point(point)
                    .presence(Math.random() < 0.5)
                    .build());
        }
    }

    public boolean existById(Long id) {
        return entityManager.createQuery(
                        "select count(m) from Mark m where m.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0;
    }
}
