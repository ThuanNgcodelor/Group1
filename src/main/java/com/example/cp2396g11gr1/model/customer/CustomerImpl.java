package com.example.cp2396g11gr1.model.customer;

import com.example.cp2396g11gr1.config.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerImpl implements CustomerDAO {
    Connection conn = MyConnection.getConnection();

    @Override
    public List<Customers> showAllCustomers() {
        List<Customers> customersList = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customers customer = new Customers(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("email")
                );
                customersList.add(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customersList;
    }

    @Override
    public List<Customers> search(String keyword) {
        List<Customers> customersList = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM customer WHERE name LIKE ? OR phone LIKE ? OR email LIKE ?");
            String query = "%" + keyword + "%";
            statement.setString(1, query);
            statement.setString(2, query);
            statement.setString(3, query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customers customer = new Customers(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("email")
                );
                customersList.add(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customersList;
    }

    @Override
    public boolean addCustomer(Customers customer) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO customer (name, address, phone, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getEmail());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customers customer) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE customer SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getEmail());
            statement.setInt(5, customer.getId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(Customers customer) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM customer WHERE id = ?");
            statement.setInt(1, customer.getId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
