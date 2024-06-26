package com.example.be3rdproject.cafe.web.controller;

import com.example.be3rdproject.cafe.dto.CafeDto;
import com.example.be3rdproject.cafe.service.CafeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
@Slf4j
public class CafeController {
    private final CafeService cafeService;

    @GetMapping
    @Operation(summary = "서울 전체 카페 조회")
    public ResponseEntity<List<CafeDto>> getAllCafes() {
        return ResponseEntity.ok(cafeService.findAllCafes());
    }

    @GetMapping("/score")
    @Operation(summary = "평점 높은 순으로 카페 조회")
    public ResponseEntity<List<CafeDto>> getCafesByScore() {
        return ResponseEntity.ok(cafeService.findCafesByScore());
    }

    @GetMapping("/review-count")
    @Operation(summary = "리뷰 수가 많은 순으로 카페 조회")
    public ResponseEntity<List<CafeDto>> getCafesByReviewCount() {
        return ResponseEntity.ok(cafeService.findCafesByReviewCount());
    }

    @GetMapping("/address")
    @Operation(summary = "각 구에 해당하는 카페 조회  ex) address?address=성동구 " +
            "성동구,영등포구,강남구,강동구,마포구,종로구,중구,노원구,성북구,용산구,서초구\n" +
            "마포구,송파구,동작구,구로구,서대문구,광진구,은평구 ")

    public ResponseEntity<List<CafeDto>> getCafeByAddress(@RequestParam("address") String address) {
        List<CafeDto> cafes = cafeService.findCafesByAddress(address);
        if (cafes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cafes);
    }


    @GetMapping("/{id}")
    @Operation(summary = "특정 카페 (cafe_id)로 조회")
    public ResponseEntity<CafeDto> getCafeById(@PathVariable("id") Long id) {
        Optional<CafeDto> cafe = cafeService.findCafeById(id);
        return cafe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}