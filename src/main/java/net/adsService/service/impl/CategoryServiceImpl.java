package net.adsService.service.impl;

import net.adsService.form.CategoryRequestForm;
import net.adsService.model.*;
import net.adsService.pages.AdDetails;
import net.adsService.pages.MainPage;
import net.adsService.repository.AdRepository;
import net.adsService.repository.CategoryRepository;
import net.adsService.repository.RegionRepository;
import net.adsService.repository.UserRepository;
import net.adsService.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MainPage searchAdByCategory(int categoryId, User user, Pageable pageable) {
        List<Region> regions = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<AdDetails> adDetails = new ArrayList<>();
        int size = categoryRepository.countByChidlrenList(categoryId);
        List<Ad> adList;
        if(size == 0){
            adList = adRepository.findAllByCategoryId(categoryId,pageable);
        }else {
            adList = adRepository.findAllByCategory(categoryId,pageable);
        }
        for (Ad ad : adList) {
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
                .pages(calculatePages(size==0,categoryId))
                .adDetails(adDetails)
                .popularAds(adRepository.getPopulars(PageRequest.of(0,8)))
                .build();
    }

    @Override
    public Category getOneById(int id) {
        return categoryRepository.getOne(id);
    }

    @Transactional
    public void add(CategoryRequestForm form) {
        Category category = Category.builder()
                .name(form.getNameEn())
                .nameRu(form.getNameRu())
                .nameArm(form.getNameArm())
                .parent(form.getParent() == -1 ? null : Category.builder().id(form.getParent()).build())
                .build();
        categoryRepository.save(category);
        LOGGER.debug("category saved");
    }


    private int[] calculatePages(boolean flag,int categoryId){
        int size = 8;
        int length;
        long count;
        if(flag) {
            count = adRepository.countByCategoryId(categoryId);
        }else {
            count = adRepository.countByCategory(categoryId);
        }
        if(count % size != 0){
            length = (int) (count/size + 1);
        }else {
            length = (int) (count/size);
        }
        return new int[length];
    }
}
