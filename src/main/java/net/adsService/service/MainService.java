package net.adsService.service;

import net.adsService.model.User;
import net.adsService.pages.AdminMainPage;
import net.adsService.pages.MainPage;
import org.springframework.data.domain.Pageable;

public interface MainService {

    MainPage get(User user, Pageable pageable);

    AdminMainPage getAdminPage(Pageable pageable);
}
