package com.kpi.journalrest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarksPageDto {
    List<MarkDto> marks;
    PaginationDto pagination;
}
