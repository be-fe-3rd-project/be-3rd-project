package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.dto.ReviewDto;
import com.example.be3rdproject.cafe.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "리뷰 전체 조회")
    public List<ReviewDto> getAllReviews() {
        return reviewService.findAllReviews();
    }

    @GetMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 조회")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("id") Long id) {
        Optional<ReviewDto> reviewDto = reviewService.findReviewById(id);
        return reviewDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    @Operation(summary = "리뷰 생성")
    public ReviewDto createReview(@RequestBody ReviewDto reviewDTO) {
        return reviewService.saveReview(reviewDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 업데이트")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("id") Long id, @RequestBody ReviewDto reviewDetails) {
        try {
            ReviewDto updatedReview = reviewService.updateReview(id, reviewDetails);
            return ResponseEntity.ok(updatedReview);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "id로 특정 리뷰 삭제")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
    }
}
