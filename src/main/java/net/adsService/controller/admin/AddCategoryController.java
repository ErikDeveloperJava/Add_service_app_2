package net.adsService.controller.admin;

import net.adsService.form.CategoryRequestForm;
import net.adsService.form.CategoryResponseForm;
import net.adsService.pages.Pages;
import net.adsService.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/category")
public class AddCategoryController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Environment environment;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String showAddPage(){
        return ADD_CATEGORY;
    }

    @PostMapping("/add")
    public @ResponseBody
    CategoryResponseForm add(@Valid CategoryRequestForm form, BindingResult result,
                             Locale locale){
        String lang = locale.getLanguage();
        LOGGER.debug("form : {},lang :{}",form,lang);
        if(result.hasErrors()){
            List<FieldError> errors = new ArrayList<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                FieldError error =
                        new FieldError("category",fieldError.getField(),
                                environment.getProperty(fieldError.getField() + ".error." + lang));
                errors.add(error);
            }
            return CategoryResponseForm.builder()
                    .errors(errors)
                    .build();
        }else {
            categoryService.add(form);
            return CategoryResponseForm.builder()
                    .success(true)
                    .successMsg(environment.getProperty("category.add.success." + lang))
                    .build();
        }
    }
}
