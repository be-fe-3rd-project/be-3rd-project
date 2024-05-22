package com.example.be3rdproject.cafe.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String reviewerName;
    private String reviewContent;
    private Float reviewScore;
    private Long cafeId;
}
