package com.kpi.journalrest.mapper;

import com.kpi.journalrest.dto.StudentDto;
import com.kpi.journalrest.entity.Student;
import jakarta.ejb.Stateless;

@Stateless
public class StudentMapper {

    public StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .build();
    }

}
