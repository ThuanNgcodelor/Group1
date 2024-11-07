package com.example.cp2396g11gr1.model.product;

import java.util.List;

public interface ProductDAO {
    public boolean addProducts(Products products);
    public boolean updateProducts(Products products);
    public boolean deleteProducts(Products products);
    public Products getProducts(int id);
    public List<Products> AllProducts();
    public List<Products> findProducts(String keyword);
    public void updateProductStock(int productID, int stock);
    public int getProductStock(int productID);
    public List<Products> showStock();

}
