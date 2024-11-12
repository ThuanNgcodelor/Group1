package com.example.cp2396g11gr1.model.supplier;

public class Supplier {
    private int id;
    private String supplierName;
    private String supplierAddress;
    private int supplierPhone;
    private String supplierEmail;
    public Supplier(int id, String supplierName, String supplierAddress, int supplierPhone, String supplierEmail) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
    }
    public Supplier() {}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public int getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(int supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
    @Override
    public String toString() {
        return supplierName;
    }
}
