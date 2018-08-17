package net.adsService.service;

import net.adsService.model.User;

public interface LikesAndDislikesService {

    String addLike(User user, int adId);

    String addDislikes(User user,int adId);
}
