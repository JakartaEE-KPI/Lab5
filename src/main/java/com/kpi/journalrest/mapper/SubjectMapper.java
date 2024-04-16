package com.kpi.journalrest.mapper;

import com.kpi.journalrest.dto.SubjectDto;
import com.kpi.journalrest.entity.Subject;
import jakarta.ejb.Stateless;

@Stateless
public class SubjectMapper {

    public SubjectDto toDto(Subject subject) {
        if (subject == null) {
            return null;
        }
        return SubjectDto.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .build();
    }

}
