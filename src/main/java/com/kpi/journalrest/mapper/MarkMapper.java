package com.kpi.journalrest.mapper;

import com.kpi.journalrest.dto.MarkDto;
import com.kpi.journalrest.dto.MarkRequest;
import com.kpi.journalrest.entity.Mark;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Stateless
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkMapper {

    @EJB
    StudentMapper studentMapper;

    @EJB
    SubjectMapper subjectMapper;

    public MarkDto toDto(Mark mark) {
        if (mark == null) {
            return null;
        }
        return MarkDto.builder()
                .id(mark.getId())
                .point(mark.getPoint())
                .presence(mark.getPresence())
                .student(studentMapper.toDto(mark.getStudent()))
                .subject(subjectMapper.toDto(mark.getSubject()))
                .build();
    }

    public Mark patchUpdate(Mark mark, MarkRequest markRequest) {
        if (markRequest == null || mark == null) {
            return null;
        }
        if (markRequest.getPresence() != null) {
            mark.setPresence(markRequest.getPresence());
        }
        if (markRequest.getPoint() != null) {
            mark.setPoint(markRequest.getPoint());
        }
        return mark;
    }

    public Mark putUpdate(Mark mark, MarkRequest markRequest) {
        if (markRequest == null || mark == null) {
            return null;
        }
        mark.setPresence(markRequest.getPresence());
        mark.setPoint(markRequest.getPoint());
        return mark;
    }

    public List<MarkDto> toDtoList(List<Mark> marks) {
        return marks.stream()
                .map(this::toDto)
                .toList();
    }
}
