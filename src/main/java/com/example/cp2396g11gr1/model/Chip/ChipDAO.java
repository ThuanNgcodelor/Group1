package com.example.cp2396g11gr1.model.Chip;

import com.example.cp2396g11gr1.model.category.Category;

import java.util.List;

public interface ChipDAO {
    public boolean addCategory(Chip chip);
    public void updateCategory(Chip chip);
    public boolean deleteCategory(Chip chip);
    public List<Chip> getAllCategory();
    public List<Chip> searchCategory(String keyword);
}
