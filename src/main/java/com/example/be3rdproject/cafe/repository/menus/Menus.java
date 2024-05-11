package com.example.be3rdproject.cafe.repository.menus;

import com.example.be3rdproject.cafe.repository.cafes.Cafes;
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
@Table(name = "Menus")
public class Menus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "menu_name", length = 45)
    private String menuName;

    @Column(name = "menu_price", length = 20)
    private String menuPrice;

    @ManyToOne
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafes cafe;

}
