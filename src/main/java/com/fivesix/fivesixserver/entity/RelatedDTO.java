package com.fivesix.fivesixserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedDTO {
    private String username;
    private Integer cinema_id;
    private Double rating;
}
