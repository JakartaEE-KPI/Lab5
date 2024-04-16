package com.kpi.journalrest.service;

import com.kpi.journalrest.common.Page;
import com.kpi.journalrest.common.PageRequest;
import com.kpi.journalrest.entity.Mark;
import com.kpi.journalrest.exception.ElementNotFoundException;
import com.kpi.journalrest.repository.MarkRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkService {

    @EJB
    MarkRepository markRepository;

    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    public Mark getById(Long id) {
        return markRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("mark by id=" + id));
    }

    public Page<List<Mark>> findStudentSubjectMarks(Long studentId, Long subjectId, Long journalId, PageRequest pageRequest) {
        return markRepository.findStudentSubjectMarks(studentId, subjectId, journalId, pageRequest);
    }

    public void deleteById(Long id) {
        Mark mark = this.getById(id);
        markRepository.delete(mark);
    }

}
