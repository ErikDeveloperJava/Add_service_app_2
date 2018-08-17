package net.adsService.repository;

import net.adsService.model.Ad;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AdRepository extends JpaRepository<Ad,Integer> {

    @Query("select size(a.likes) from Ad a where a.id = :id")
    int likesCount(@Param("id")int id);

    @Query("select size(a.dislikes) from Ad a where a.id = :id")
    int dislikesCount(@Param("id")int id);

    @Query("select a from Ad a order by size(a.likes) desc ")
    List<Ad> getPopulars(Pageable pageable);

    @Query("select a from Ad a inner join Category c1 on c1.id = :id and a.category member c1.childrenList")
    List<Ad> findAllByCategory(@Param("id")int id,Pageable pageable);

    List<Ad> findAllByCategoryId(int categoryId,Pageable pageable);

    int countByCategoryId(int id);

    @Query("select count(*) from Ad a inner join Category c1 on c1.id = :id and a.category member c1.childrenList")
    int countByCategory(@Param("id")int id);
}
