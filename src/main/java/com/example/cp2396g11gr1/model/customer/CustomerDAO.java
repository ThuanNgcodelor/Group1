package com.example.cp2396g11gr1.model.customer;

import java.util.List;

public interface CustomerDAO {
    public List<Customers> showAllCustomers();
    public List<Customers> search(String keyword);
    public boolean addCustomer(Customers customer);
    public boolean updateCustomer(Customers customer);
    public boolean deleteCustomer(Customers customer);
}
