package com.pragma.plazoleta.infrastructure.output.jpa.data;

import com.pragma.plazoleta.domain.model.Category;
import com.pragma.plazoleta.infrastructure.output.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryDataLoader implements CommandLineRunner {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count()==0){
            categoryRepository.save(categoryEntityMapper.toCategoryEntity(new Category("Hamburguesas")));
            categoryRepository.save(categoryEntityMapper.toCategoryEntity(new Category("Pizzas")));
            categoryRepository.save(categoryEntityMapper.toCategoryEntity(new Category("Bebidas")));
        }
    }
}
