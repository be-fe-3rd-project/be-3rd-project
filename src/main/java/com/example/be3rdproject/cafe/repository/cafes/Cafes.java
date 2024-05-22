package com.example.be3rdproject.cafe.repository.cafes;

import com.example.be3rdproject.cafe.repository.menus.Menus;
import com.example.be3rdproject.cafe.repository.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cafes")
public class Cafes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Integer cafeId;

    @Column(name = "cafe_name", length = 45)
    private String cafeName;

    @Column(name = "cafe_address", length = 500)
    private String cafeAddress;

    @Column(name = "cafe_phone", length = 20)
    private String cafePhone;

    @Column(name = "cafe_score")
    private Float cafeScore;

    @Column(name = "cafe_category", length = 20)
    private String cafeCategory;

    @Column(name = "cafe_area", length = 20)
    private String cafeArea;

    @Column(name = "cafe_parking")
    private Boolean cafeParking;

    @Column(name = "cafe_wifi")
    private Boolean cafeWifi;

    @Column(name = "cafe_animal")
    private Boolean cafeAnimal;

    @Column(name = "cafe_thumb", length = 225)
    private String cafeThumb;

    @Column(name = "cafe_holiday", length = 20)
    private String cafeHoliday;

    @Column(name = "review_count")
    private Integer reviewCount;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    private List<Menus> menus;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

}
