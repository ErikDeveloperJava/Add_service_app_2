package net.adsService.service.impl;

import net.adsService.model.*;
import net.adsService.pages.AdPage;
import net.adsService.repository.*;
import net.adsService.service.AdService;
import net.adsService.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AdServiceImpl implements AdService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AttributeAdRepository attributesRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeAdRepository attributeAdRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional(rollbackFor = Exception.class)
    public int create(Ad ad, List<AttributeAd> attributes, List<MultipartFile> images) {
        Random random = new Random();
        ad = adRepository.save(ad);
        String parent = ad.getUser().getUsername() + "/" + ad.getId();
        if (!attributes.isEmpty()) {
            for (AttributeAd attribute : attributes) {
                attribute.setAd(ad);
            }
            attributesRepository.saveAll(attributes);
        }
        List<Image> imageNames = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            String child = ad.getId() + ad.getTitle() + random.nextInt(2000000) + ".jpg";
            Image image = Image.builder()
                    .ad(ad)
                    .name(parent + "/" + child)
                    .build();
            imageNames.add(image);
            try {
                imageUtil.save(parent, child, images.get(i));
            } catch (Exception e) {
                imageUtil.deleteAll(parent);
                throw e;
            }
        }
        try {
            imageRepository.saveAll(imageNames);
            ad.setImage(imageNames.get(0).getName());
        } catch (Exception e) {
            imageUtil.deleteAll(parent);
            throw e;
        }
        LOGGER.info("ad successfully saved");
        return ad.getId();
    }

    @Override
    public AdPage getById(User user, int adId) {
        Ad ad = adRepository.getOne(adId);
        boolean like = false;
        boolean dislike = false;
        List<Category> categories = new ArrayList<>();
        List<Region> regions = new ArrayList<>();
        if (user.getRole().equals(UserRole.USER)) {
            if (!user.equals(ad.getUser())) {
                like = userRepository.likesCountByAdId(adId, user.getId()) == 0 ? false : true;
                dislike = userRepository.dislikesCountByAdId(adId, user.getId()) == 0 ? false : true;
            }
            categories = categoryRepository.findAllByChildrenListIsEmpty();
        } else {
            regions = regionRepository.findAll();
        }
        return AdPage.builder()
                .ad(ad)
                .like(like)
                .regions(regions)
                .dislike(dislike)
                .categories(categories)
                .likes(adRepository.likesCount(adId))
                .dislikes(adRepository.dislikesCount(adId))
                .images(imageRepository.findAllByAdId(adId))
                .comments(commentRepository.findAllByAdId(adId))
                .attributes(attributeAdRepository.findAllByAdId(adId))
                .build();
    }

    @Override
    public boolean existById(int id) {
        return adRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        adRepository.deleteById(id);
        LOGGER.debug("ad deleted");
    }
}
