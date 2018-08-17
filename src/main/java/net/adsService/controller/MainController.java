package net.adsService.controller;

import net.adsService.model.User;
import net.adsService.model.UserRole;
import net.adsService.pages.Pages;
import net.adsService.service.MainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class MainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MainService mainService;

    @GetMapping(value = "")
    public String main(@RequestAttribute("user")User user,
                       Pageable pageable, Model model){
        if(user.getRole().equals(UserRole.ADMIN)){
            LOGGER.debug("authenticated user role 'ADMIN' redirect to '/admin' url");
            return "redirect:/admin";
        }
        model.addAttribute("pageData",mainService.get(user,pageable));
        model.addAttribute("page",pageable.getPageNumber());
        return INDEX;
    }
}
