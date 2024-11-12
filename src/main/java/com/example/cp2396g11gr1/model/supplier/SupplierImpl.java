package com.example.cp2396g11gr1.model.supplier;

import com.example.cp2396g11gr1.config.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierImpl implements SupplierDAO {
    Connection conn = MyConnection.getConnection();

    @Override
    public boolean addSupplier(Supplier supplier) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO supplier (supplierName, address, phone, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getSupplierAddress());
            statement.setInt(3, supplier.getSupplierPhone());
            statement.setString(4, supplier.getSupplierEmail());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE supplier SET supplierName = ?, address = ?, phone = ?, email = ? WHERE id = ?");
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getSupplierAddress());
            statement.setInt(3, supplier.getSupplierPhone());
            statement.setString(4, supplier.getSupplierEmail());
            statement.setInt(5, supplier.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteSupplier(Supplier supplier) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM supplier WHERE id = ?");
            statement.setInt(1, supplier.getId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Supplier> GetAllSupplier() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM supplier");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                suppliers.add(new Supplier(
                        resultSet.getInt("supplierID"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("address"),
                        resultSet.getInt("phone"),
                        resultSet.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public List<Supplier> searchSupplier(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM supplier WHERE supplierName LIKE ? OR address LIKE ? OR email LIKE ?");
            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            statement.setString(3, searchPattern);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                suppliers.add(new Supplier(
                        resultSet.getInt("supplierID"),
                        resultSet.getString("supplierName"),
                        resultSet.getString("address"),
                        resultSet.getInt("phone"),
                        resultSet.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}
