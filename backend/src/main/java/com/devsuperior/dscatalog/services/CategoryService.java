package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> result = categoryRepository.findAll(pageable);
        return result.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("RESOURCE NOT FOUND !"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category entity = new Category();
        copyDtoToEntity(categoryDTO, entity);
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            copyDtoToEntity(categoryDTO, entity);
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ID NOT FOUND: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("RESOURCE NOT FOUND !");
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("REFRENCE INTEGRITY FAIL!");
        }
    }

    private void copyDtoToEntity(CategoryDTO categoryDTO, Category entity) {
        entity.setName(categoryDTO.getName());
    }

}
