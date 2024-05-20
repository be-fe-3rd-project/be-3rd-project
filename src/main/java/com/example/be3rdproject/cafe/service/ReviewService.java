package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.dto.ReviewDto;
import com.example.be3rdproject.cafe.repository.review.Review;
import com.example.be3rdproject.cafe.repository.review.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;

    public List<ReviewDto> findAllReviews() {
        return reviewJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public Optional<ReviewDto> findReviewById(Long id) {
        return reviewJpaRepository.findById(id).map(this::convertToDTO);
    }

    public ReviewDto saveReview(ReviewDto reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Review savedReview = reviewJpaRepository.save(review);
        return convertToDTO(savedReview);
    }

    public void deleteReview(Long id) {
        reviewJpaRepository.deleteById(id);
    }

    private ReviewDto convertToDTO(Review review) {
        ReviewDto reviewDTO = new ReviewDto();
        reviewDTO.setId(review.getReviewId());
        reviewDTO.setReviewContent(review.getReviewContent());
        reviewDTO.setReviewScore(review.getReviewScore());
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDto reviewDTO) {
        Review review = new Review();
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewScore(reviewDTO.getReviewScore());
        return review;
    }

}
