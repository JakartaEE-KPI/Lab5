package com.kpi.journalrest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkDto {

    Long id;
    Boolean presence;
    Integer point;
    StudentDto student;
    SubjectDto subject;

}
