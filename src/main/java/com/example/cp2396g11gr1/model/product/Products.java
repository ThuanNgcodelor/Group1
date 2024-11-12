package com.example.cp2396g11gr1.model.product;

public class Products {
    private int id;
    private String productName;
    private double price;
    private String image;
    private int quantity;
    private String categoryName;
    private String areaName;
    private String chipName;
    private String supplierName;

    private int categoryID;
    private int chipID;
    private int areaID;
    private int supplierID;

    private int stockQuantity;
    private int stockSold;
    private double stockAmount;

    // Default constructor
    public Products() {}

    // Constructor with main fields
    public Products(String productName, double price, String image, int quantity,
                    String categoryName, String areaName, String chipName, String supplierName) {
        this.productName = productName;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.areaName = areaName;
        this.chipName = chipName;
        this.supplierName = supplierName;
    }

    // Getters and Setters for main properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Category, Area, Chip, and Supplier details
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getChipName() {
        return chipName;
    }

    public void setChipName(String chipName) {
        this.chipName = chipName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getChipID() {
        return chipID;
    }

    public void setChipID(int chipID) {
        this.chipID = chipID;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    // Stock-related properties
    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStockSold() {
        return stockSold;
    }

    public void setStockSold(int stockSold) {
        this.stockSold = stockSold;
    }

    public double getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(double stockAmount) {
        this.stockAmount = stockAmount;
    }
}
