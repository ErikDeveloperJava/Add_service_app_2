package net.adsService.service.impl;

import net.adsService.model.Ad;
import net.adsService.model.User;
import net.adsService.repository.AdRepository;
import net.adsService.repository.UserRepository;
import net.adsService.service.LikesAndDislikesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LikesAndDislikesServiceImpl implements LikesAndDislikesService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    @Override
    public String addLike(User user, int adId) {
        List<Ad> likes = userRepository.findAllLikes(user.getId());
        List<Ad> dislikes = userRepository.findAllDislikes(user.getId());
        Ad ad = adRepository.getOne(adId);
        if(!likes.contains(ad)){
            if(dislikes.contains(ad)){
                dislikes.remove(ad);
                likes.add(ad);
                user.setLikes(likes);
                user.setDislikes(dislikes);
                userRepository.save(user);
                LOGGER.debug("dislike removed,like saved");
                return "like-active dislike-not";
            }else {
                likes.add(ad);
                user.setDislikes(dislikes);
                user.setLikes(likes);
                userRepository.save(user);
                LOGGER.debug("like saved");
                return "like-active";
            }
        }else {
            likes.remove(ad);
            user.setDislikes(dislikes);
            user.setLikes(likes);
            userRepository.save(user);
            LOGGER.debug("like removed");
            return "like-not";
        }
    }

    @Override
    public String addDislikes(User user, int adId) {
        List<Ad> dislikes = userRepository.findAllDislikes(user.getId());
        List<Ad> likes = userRepository.findAllLikes(user.getId());
        Ad ad = adRepository.getOne(adId);
        if(!dislikes.contains(ad)){
            if(likes.contains(ad)){
                likes.remove(ad);
                dislikes.add(ad);
                user.setLikes(likes);
                user.setDislikes(dislikes);
                userRepository.save(user);
                LOGGER.debug("like removed,dislike saved");
                return "dislike-active like-not";
            }else {
                dislikes.add(ad);
                user.setDislikes(dislikes);
                userRepository.save(user);
                LOGGER.debug("dislike saved");
                return "dislike-active";
            }
        }else {
            dislikes.remove(ad);
            user.setDislikes(dislikes);
            userRepository.save(user);
            LOGGER.debug("dislike removed");
            return "dislike-not";
        }
    }
}
