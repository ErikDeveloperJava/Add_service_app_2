package net.adsService.repository;

import net.adsService.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    List<Image> findAllByAdId(int adId);
}
