package net.adsService.repository;

import net.adsService.model.Ad;
import net.adsService.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findAllByParentIsNull();

    List<Category> findAllByParentId(int parentId);

    int countByParentId(int parentId);

    @Query("select c from Category c where size(c.childrenList)=0 ")
    List<Category> findAllByChildrenListIsEmpty();

    @Query("select c.parent from Category c where c.id = :childId")
    Category getParent(@Param("childId") int childId);

    @Query("select size(c.childrenList) from Category c where c.id=:id")
    int countByChidlrenList(@Param("id")int id);
}
