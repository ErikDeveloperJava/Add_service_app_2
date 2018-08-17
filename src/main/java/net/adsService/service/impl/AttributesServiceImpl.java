package net.adsService.service.impl;

import net.adsService.model.Category;
import net.adsService.model.CategoryAttribute;
import net.adsService.repository.CategoryAttributeRepository;
import net.adsService.repository.CategoryRepository;
import net.adsService.service.AttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributesServiceImpl implements AttributesService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryAttributeRepository attributeRepository;

    @Transactional(readOnly = true)
    public List<CategoryAttribute> getByCategoryId(int categoryId) {
        List<CategoryAttribute> attributes = new ArrayList<>(attributeRepository.findAllByCategoryId(categoryId));
        addAttributes(categoryId,attributes);
        return attributes;
    }

    private void addAttributes(int categoryId,List<CategoryAttribute> attributes){
        Category parent = categoryRepository.getParent(categoryId);
        if(parent != null){
            attributes.addAll(attributeRepository.findAllByCategoryId(parent.getId()));
            addAttributes(parent.getId(),attributes);
        }
    }
}
