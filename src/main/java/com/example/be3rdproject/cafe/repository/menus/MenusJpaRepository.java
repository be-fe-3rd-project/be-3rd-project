package com.example.be3rdproject.cafe.repository.menus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenusJpaRepository extends JpaRepository<Menus,Long> {
    List<Menus> findByCafe_CafeId(Long cafeId);

}

