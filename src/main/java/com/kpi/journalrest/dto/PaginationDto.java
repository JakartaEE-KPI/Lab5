package com.kpi.journalrest.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginationDto {

    int totalPages;
    long totalResults;
    int currentPage;

}
