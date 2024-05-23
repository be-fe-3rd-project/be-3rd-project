package com.example.be3rdproject.cafe.repository.cafes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafesJpaRepository extends JpaRepository<Cafes,Long> {
//    List<Cafes> findAllByOrderByCafeScoreDesc();
//    List<Cafes> findAllByOrderByReviewCountDesc();
    List<Cafes> findByCafeAddressContaining(String address);

    @Modifying
    @Query("UPDATE Cafes c SET c.reviewCount = c.reviewCount + 1 WHERE c.cafeId = :cafeId")
    void incrementReviewCountByCafeId(@Param("cafeId") Long cafeId);
    @EntityGraph(attributePaths = {"menuList"})
    List<Cafes> findAll();
    Page<Cafes> findAllByOrderByCafeScoreDesc(Pageable pageable);
    Page<Cafes> findAllByOrderByReviewCountDesc(Pageable pageable);
//    Page<Cafes> findByCafeAddressContaining(String address, Pageable pageable);
}
