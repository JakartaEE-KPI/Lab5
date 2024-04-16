package com.kpi.journalrest.service;

import com.kpi.journalrest.repository.JournalRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JournalService {

    @EJB
    JournalRepository journalRepository;

    public boolean existById(Long id) {
        return journalRepository.existById(id);
    }

}
