package com.kpi.journalrest.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Page<T> {
    int totalPages;
    long totalResults;
    int currentPage;
    T data;

    public static <T> Page<T> of(int totalPages, long totalResults, int currentPage, T data) {
        return new Page<T>(totalPages, totalResults, currentPage, data);
    }
}
