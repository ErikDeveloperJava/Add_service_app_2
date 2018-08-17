package net.adsService.service;

import net.adsService.form.CategoryRequestForm;
import net.adsService.model.Category;
import net.adsService.model.User;
import net.adsService.pages.MainPage;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    MainPage searchAdByCategory(int categoryId, User user, Pageable pageable);

    Category getOneById(int id);

    void add(CategoryRequestForm form);
}
