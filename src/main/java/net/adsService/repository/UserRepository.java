package net.adsService.repository;

import net.adsService.model.Ad;
import net.adsService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select count(*) from User u join u.likes l on l.id = :adId and u.id = :userId")
    int likesCountByAdId(@Param("adId")int adId,@Param("userId")int userId);

    @Query("select count(*) from User u join u.dislikes l on l.id = :adId and u.id = :userId")
    int dislikesCountByAdId(@Param("adId")int adId,@Param("userId")int userId);

    @Query("select u.likes from User u where u.id = :id")
    List<Ad> findAllLikes(@Param("id")int id);

    @Query("select u.dislikes from User u where u.id = :id")
    List<Ad> findAllDislikes(@Param("id")int id);

    @Query("select distinct u from User u inner join Message m on (u.id=m.from.id and m.to.id=:id) or (u.id=m.to.id and m.from.id=:id)")
    List<User> findAllUsersWhereExistsMessages(@Param("id")int userId);
}
