package net.adsService.repository;

import net.adsService.model.AttributeAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeAdRepository extends JpaRepository<AttributeAd,Integer> {

    List<AttributeAd> findAllByAdId(int adId);
}
