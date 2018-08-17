package net.adsService.service;

import net.adsService.model.Ad;
import net.adsService.model.AttributeAd;
import net.adsService.model.User;
import net.adsService.pages.AdPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdService {

    int create(Ad ad, List<AttributeAd> attributes,List<MultipartFile> image);

    AdPage getById(User user, int adId);

    boolean existById(int id);

    void deleteById(int id);
}
