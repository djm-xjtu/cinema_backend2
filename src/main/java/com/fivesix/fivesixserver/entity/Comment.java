package com.fivesix.fivesixserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    private Integer id;
    private Integer cinema_id;
    private String username;
    private String content;
    private String mood;
    private Double rating;
}
