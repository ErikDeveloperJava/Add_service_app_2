package net.adsService.controller;

import net.adsService.model.Category;
import net.adsService.repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category")
    public List<Category> load(@RequestParam("parentId") int parentId){
        LOGGER.debug("parentId : {}",parentId);
        if(parentId == 0){
            List<Category> categories = categoryRepository.findAllByParentIsNull();
            for (Category category : categories) {
                category.setChild(true);
            }
            return categories;
        }else {
            List<Category> categories = categoryRepository.findAllByParentId(parentId);
            for (Category category : categories) {
                int count = categoryRepository.countByParentId(category.getId());
                category.setChild(count == 0 ? false : true);
            }
            return categories;
        }
    }

    @PostMapping("/category-by-child")
    public List<Category> loadAllByChild(@RequestParam("child")int child){
        List<Category> categories = new ArrayList<>();
        Category category;
        int childId = child;
        while ((category = categoryRepository.getParent(childId)) != null){
            categories.add(category);
            childId = category.getId();
        }
        return categories;
    }


    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}
