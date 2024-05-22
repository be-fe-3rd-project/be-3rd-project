package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.dto.ReviewDto;
import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import com.example.be3rdproject.cafe.repository.cafes.CafesJpaRepository;
import com.example.be3rdproject.cafe.repository.review.Review;
import com.example.be3rdproject.cafe.repository.review.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
        return reviewJpaRepository.findById(id).map(this::convertToDTO);
    }

    public ReviewDto saveReview(ReviewDto reviewDTO) {
        Optional<Cafes> cafe = cafesJpaRepository.findById(reviewDTO.getCafeId());
        if (!cafe.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided Cafe ID does not exist");
        }
        Review review = convertToEntity(reviewDTO);
        review.setCafe(cafe.get());
        Review savedReview = reviewJpaRepository.save(review);
        return convertToDTO(savedReview);
    }


    public void deleteReview(Long id) {
        reviewJpaRepository.deleteById(id);
    }

    public ReviewDto updateReview(Long id, ReviewDto reviewDTO) {
        Review review = reviewJpaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found for this id :: " + id));

        Optional<Cafes> cafe = cafesJpaRepository.findById(reviewDTO.getCafeId());
        if (!cafe.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided Cafe ID does not exist");
        }
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewScore(reviewDTO.getReviewScore());
        review.setReviewerName(reviewDTO.getReviewerName());
        review.setCafe(cafe.get());

        Review updatedReview = reviewJpaRepository.save(review);
        return convertToDTO(updatedReview);
    }

    private ReviewDto convertToDTO(Review review) {
        ReviewDto reviewDTO = new ReviewDto();
        reviewDTO.setId(review.getReviewId());
        reviewDTO.setReviewerName(review.getReviewerName());
        reviewDTO.setReviewContent(review.getReviewContent());
        reviewDTO.setReviewScore(review.getReviewScore());
        reviewDTO.setCafeId(Long.valueOf(review.getCafe().getCafeId()));
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDto reviewDTO) {
        Review review = new Review();
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewScore(reviewDTO.getReviewScore());
        review.setReviewerName(reviewDTO.getReviewerName());

        // cafeId로 Cafes 엔티티를 찾아서 설정
        Optional<Cafes> cafe = cafesJpaRepository.findById(reviewDTO.getCafeId());
        cafe.ifPresent(review::setCafe);

        return review;
    }
}
