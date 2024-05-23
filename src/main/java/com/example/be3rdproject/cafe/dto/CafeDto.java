package com.example.be3rdproject.cafe.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Float score;
    private String category;
    private String area;
    private Boolean parking;
    private Boolean wifi;
    private Boolean animal;
    private String thumb;
    private String holiday;
    private Integer reviewCount;
    private List<MenuDto> menuList;
}
