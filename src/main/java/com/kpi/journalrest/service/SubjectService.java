package com.kpi.journalrest.service;

import com.kpi.journalrest.entity.Subject;
import com.kpi.journalrest.repository.SubjectRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectService {

    @EJB
    SubjectRepository subjectRepository;

    public boolean existById(Long id) {
        return subjectRepository.existById(id);
    }

    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }
}
