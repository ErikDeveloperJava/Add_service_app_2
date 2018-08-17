package net.adsService.repository;

import net.adsService.model.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute,Integer> {

    List<CategoryAttribute> findAllByCategoryId(int categoryId);
}
