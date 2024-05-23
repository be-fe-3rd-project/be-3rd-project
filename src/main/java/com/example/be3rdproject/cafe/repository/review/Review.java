package com.example.be3rdproject.cafe.repository.review;

import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafes cafe;

    @Column(name="reviewer_name")
    private String reviewerName;

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
