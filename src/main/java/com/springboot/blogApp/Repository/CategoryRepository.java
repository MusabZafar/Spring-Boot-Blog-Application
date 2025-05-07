package com.springboot.blogApp.Repository;

import com.springboot.blogApp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
