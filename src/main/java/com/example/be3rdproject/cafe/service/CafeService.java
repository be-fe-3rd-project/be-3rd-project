package com.example.be3rdproject.cafe.service;

import com.example.be3rdproject.cafe.dto.CafeDto;
import com.example.be3rdproject.cafe.dto.MenuDto;
import com.example.be3rdproject.cafe.repository.cafes.Cafes;
import com.example.be3rdproject.cafe.repository.cafes.CafesJpaRepository;
import com.example.be3rdproject.cafe.repository.menus.Menus;
import com.example.be3rdproject.cafe.repository.menus.MenusJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafesJpaRepository cafesJpaRepository;
    private final MenusJpaRepository menusJpaRepository;

    public List<CafeDto> findAllCafes() {
        return cafesJpaRepository.findAll().stream()
                .map(this::convertToDtoWithMenus)
                .collect(Collectors.toList());
    }

    public List<CafeDto> findCafesByScore() {
        Pageable topTwenty = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "cafeScore"));
        return cafesJpaRepository.findAllByOrderByCafeScoreDesc(topTwenty).stream()
                .map(this::convertToDtoWithMenus)
                .collect(Collectors.toList());
    }

    public List<CafeDto> findCafesByReviewCount() {
        Pageable topTwenty = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "reviewCount"));
        return cafesJpaRepository.findAllByOrderByReviewCountDesc(topTwenty).stream()
                .map(this::convertToDtoWithMenus)
                .collect(Collectors.toList());
    }

    public List<CafeDto> findCafesByAddress(String address) {
        return cafesJpaRepository.findByCafeAddressContaining(address).stream()
                .map(this::convertToDtoWithMenus)
                .collect(Collectors.toList());
    }

    public Optional<CafeDto> findCafeById(Long id) {
        Optional<Cafes> cafe = cafesJpaRepository.findById(id);
        if (cafe.isPresent()) {
            CafeDto cafeDto = convertToDtoWithMenus(cafe.get());
            return Optional.of(cafeDto);
        }
        return Optional.empty();
    }

    private CafeDto convertToDtoWithMenus(Cafes cafe) {
        List<MenuDto> menus = menusJpaRepository.findByCafe_CafeId(cafe.getCafeId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return CafeDto.builder()
                .id(cafe.getCafeId())
                .name(cafe.getCafeName())
                .address(cafe.getCafeAddress())
                .phone(cafe.getCafePhone())
                .score(cafe.getCafeScore())
                .category(cafe.getCafeCategory())
                .area(cafe.getCafeArea())
                .parking(cafe.getCafeParking())
                .wifi(cafe.getCafeWifi())
                .animal(cafe.getCafeAnimal())
                .thumb(cafe.getCafeThumb())
                .holiday(cafe.getCafeHoliday())
                .reviewCount(cafe.getReviewCount())
                .menuList(menus)
                .build();
    }

    private MenuDto convertToDto(Menus menu) {
        return MenuDto.builder()
                .id(menu.getMenuId())
                .name(menu.getMenuName())
                .price(menu.getMenuPrice())
                .build();
    }
}