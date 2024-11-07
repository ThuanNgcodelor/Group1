package com.example.cp2396g11gr1.model.Area;

import com.example.cp2396g11gr1.model.category.Category;

import java.util.List;

public interface AreaDAO {
    public boolean addArea(Area area);
    public void updateArea(Area area);
    public boolean deleteArea(Area area);
    public List<Area> GetAllArea();
    public List<Area> searchArea(String keyword);
}
