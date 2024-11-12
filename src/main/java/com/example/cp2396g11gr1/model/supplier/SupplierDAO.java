package com.example.cp2396g11gr1.model.supplier;

import com.example.cp2396g11gr1.model.Area.Area;

import java.util.List;

public interface SupplierDAO {
    public boolean addSupplier(Supplier supplier);
    public void updateSupplier(Supplier supplier);
    public boolean deleteSupplier(Supplier supplier);
    public List<Supplier> GetAllSupplier();
    public List<Supplier> searchSupplier(String keyword);
}
