package com.example.be3rdproject.cafe.repository.cafes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafesJpaRepository extends JpaRepository<Cafes,Long> {
}
