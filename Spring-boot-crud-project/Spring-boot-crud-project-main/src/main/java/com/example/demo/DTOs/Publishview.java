package com.example.demo.DTOs;

import lombok.*;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Publishview {
    private Long id;
    private String edition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Long authorId;
    private Long bookId;
    private String bookName;
    private String authorName;


}
