package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.dto.ReviewDto;
import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import com.example.be3rdproject.cafe.repository.cafes.CafesJpaRepository;
import com.example.be3rdproject.cafe.repository.review.Review;
import com.example.be3rdproject.cafe.repository.review.ReviewJpaRepository;
import com.example.be3rdproject.cafe.web.error.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final CafesJpaRepository cafesJpaRepository;

    public List<ReviewDto> findAllReviews() {
        return reviewJpaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ReviewDto> findReviewById(Long id) {
        return Optional.ofNullable(reviewJpaRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ReviewNotFoundException("이 ID에 대한 리뷰를 찾을 수 없습니다 :: " + id)));
    }

    @Transactional
    public ReviewDto saveReview(ReviewDto reviewDTO) {
        Cafes cafe = cafesJpaRepository.findById(reviewDTO.getCafeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "제공된 카페ID가 존재 하지 않습니다"));

        // JPQL을 이용하여 리뷰 카운트 증가
        cafesJpaRepository.incrementReviewCountByCafeId(reviewDTO.getCafeId());
        cafesJpaRepository.flush(); // 변경 내용을 즉시 반영

        Review review = convertToEntity(reviewDTO);
        review.setCafe(cafe);
        Review savedReview = reviewJpaRepository.save(review);

        return convertToDTO(savedReview);
    }


    @Transactional
    public void deleteReview(Long id) {
        if (!reviewJpaRepository.existsById(id)) {
            throw new ReviewNotFoundException("이 ID에 대한 리뷰를 찾을 수 없습니다 :: " + id);
        }
        reviewJpaRepository.deleteById(id);
    }

    @Transactional
    public ReviewDto updateReview(Long id, ReviewDto reviewDTO) {
        Review review = reviewJpaRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("이 ID에 대한 리뷰를 찾을 수 없습니다 :: " + id));

        Cafes cafe = cafesJpaRepository.findById(reviewDTO.getCafeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "제공된 카페ID가 존재 하지 않습니다"));

        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewScore(reviewDTO.getReviewScore());
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setCafe(cafe);

        Review updatedReview = reviewJpaRepository.save(review);
        return convertToDTO(updatedReview);
    }


    private ReviewDto convertToDTO(Review review) {
        return ReviewDto.builder()
                .id(review.getReviewId())
                .reviewerName(review.getReviewerName())
                .reviewContent(review.getReviewContent())
                .reviewScore(review.getReviewScore())
                .cafeId(Long.valueOf(review.getCafe().getCafeId()))
                .build();
    }

    private Review convertToEntity(ReviewDto reviewDTO) {
        Review review = Review.builder()
                .reviewContent(reviewDTO.getReviewContent())
                .reviewScore(reviewDTO.getReviewScore())
                .reviewerName(reviewDTO.getReviewerName())
                .build();

        // cafeId로 Cafes 엔티티를 찾아서 설정
        cafesJpaRepository.findById(reviewDTO.getCafeId()).ifPresent(review::setCafe);

        return review;
    }
}
