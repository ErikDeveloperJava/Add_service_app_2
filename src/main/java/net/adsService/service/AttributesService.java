package net.adsService.service;

import net.adsService.model.CategoryAttribute;

import java.util.List;

public interface AttributesService {

    List<CategoryAttribute> getByCategoryId(int categoryId);
}
