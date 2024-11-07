package com.example.cp2396g11gr1.model.category;

import java.util.List;

public interface CategoryDAO{
    public boolean addCategory(Category cate);
    public void updateCategory(Category cate);
    public boolean deleteCategory(Category cate);
    public List<Category> getAllCategory();
    public List<Category> searchCategory(String keyword);
}
