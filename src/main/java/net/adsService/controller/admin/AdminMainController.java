package net.adsService.controller.admin;

import net.adsService.model.User;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MainService mainService;

    @GetMapping
    public String main(@RequestAttribute("user")User user, Pageable pageable,
                       Model model){
        model.addAttribute("pageData",mainService.getAdminPage(pageable));
        model.addAttribute("page",pageable.getPageNumber());
        return ADMIN;
    }
}
