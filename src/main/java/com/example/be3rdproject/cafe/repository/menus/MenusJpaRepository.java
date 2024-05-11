package java.com.example.be3rdproject.cafe.repository.menus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenusJpaRepository extends JpaRepository<Menus,Integer> {
}

