package com.kpi.journalrest.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageRequest {

    Integer page;
    Integer size;

    public static PageRequest of(Integer page, Integer size) {
        if (page == null || size == null) {
            return new PageRequest(1, 10);
        } else {
            return new PageRequest(page, size);
        }
    }

}
