package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.dto.ReviewDto;
import com.example.be3rdproject.cafe.repository.review.Review;
import com.example.be3rdproject.cafe.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "리뷰 전체 조회")
    public List<ReviewDto> getAllReviews() {
        return reviewService.findAllReviews();
    }

    @GetMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 조회")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.findReviewById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "리뷰 생성")
    public ReviewDto createReview(@RequestBody ReviewDto reviewDTO) {
        return reviewService.saveReview(reviewDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 업데이트")
    public ReviewDto updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDetails) {
        ReviewDto review = reviewService.findReviewById(id).orElse(null);
        if (review != null) {
            review.setReviewContent(reviewDetails.getReviewContent());
            review.setReviewScore(reviewDetails.getReviewScore());
            return reviewService.saveReview(review);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 삭제")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

}
