package net.adsService.service.impl;

import net.adsService.model.*;
import net.adsService.pages.AdDetails;
import net.adsService.pages.AdminMainPage;
import net.adsService.pages.MainPage;
import net.adsService.repository.AdRepository;
import net.adsService.repository.CategoryRepository;
import net.adsService.repository.RegionRepository;
import net.adsService.repository.UserRepository;
import net.adsService.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MainPage get(User user, Pageable pageable) {
        List<Region> regions = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<AdDetails> adDetails = new ArrayList<>();
        for (Ad ad : adRepository.findAll(
                PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by(Sort.Direction.DESC,"createdDate"))).getContent()) {
            AdDetails adDetail = AdDetails.builder()
                    .ad(ad)
                    .build();
            if(user.getRole().equals(UserRole.USER)){
                adDetail.setLike(userRepository.likesCountByAdId(ad.getId(),user.getId())== 0 ? false : true);
                adDetail.setDislike(userRepository.dislikesCountByAdId(ad.getId(),user.getId())== 0 ? false : true);
            }
            adDetail.setLikes(adRepository.likesCount(ad.getId()));
            adDetail.setDislikes(adRepository.dislikesCount(ad.getId()));
            adDetails.add(adDetail);
        }
        if(user.getRole().equals(UserRole.ANONYMOUS)){
            regions = regionRepository.findAll();
        }else {
            categories = categoryRepository.findAllByChildrenListIsEmpty();
        }
        return MainPage.builder()
                .regions(regions)
                .categories(categories)
                .pages(calculatePages(false))
                .adDetails(adDetails)
                .popularAds(adRepository.getPopulars(PageRequest.of(0,8)))
                .build();
    }

    @Override
    public AdminMainPage getAdminPage(Pageable pageable) {
        return AdminMainPage.builder()
                .users(userRepository.findAll(pageable).getContent())
                .pages(calculatePages(true))
                .build();
    }

    private int[] calculatePages(boolean user){
        int size = 8;
        int length;
        long count;
        if(user){
            count = userRepository.count();
        }else{
         count = adRepository.count();
        }
        if(count % size != 0){
            length = (int) (count/size + 1);
        }else {
            length = (int) (count/size);
        }
        return new int[length];
    }
}
