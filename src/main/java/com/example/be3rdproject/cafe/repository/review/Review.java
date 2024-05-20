package com.example.be3rdproject.cafe.repository.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Lob
    @Column(name = "review_content ")
    private String reviewContent;

    @Column(name = "review_date",nullable = false, updatable = false)
    private LocalDateTime reviewDate;

    @Column(name = "review_score")
    private Float reviewScore;
    @PrePersist
    protected void onCreate() {
        reviewDate = LocalDateTime.now();
    }

}
