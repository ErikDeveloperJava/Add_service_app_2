package net.adsService.controller;

import net.adsService.model.Category;
import net.adsService.model.User;
import net.adsService.pages.Pages;
import net.adsService.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class SearchCategoryController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{id}")
    public String searchAd(@PathVariable("id")String strId, @RequestAttribute("user")User user,
                           Pageable pageable, Model model){
        int id = parseToInteger(strId);
        Category category;
        if(id == -1 || (category = categoryService.getOneById(id)) == null){
            return "redirect:/";
        }else {
            model.addAttribute("pageData",categoryService.searchAdByCategory(id,user,pageable));
            model.addAttribute("category",category);
            model.addAttribute("page",pageable.getPageNumber());
            return SEARCH_AD;
        }
    }

    private int parseToInteger(String strId){
        int id;
        try {
            id = Integer.parseInt(strId);
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
