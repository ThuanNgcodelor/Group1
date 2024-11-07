package com.example.cp2396g11gr1.model.account;

import com.example.cp2396g11gr1.config.MyConnection;
import com.example.cp2396g11gr1.controller.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountImple implements AccountDAO {
    Connection conn = MyConnection.getConnection();
    @Override
    public List<Accounts> AllStaff() {
        List<Accounts> list = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT id,fullname, email, password, phone, address, role FROM accounts");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Accounts account = new Accounts(
                        resultSet.getInt("id"),
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getInt("role")
                );
                list.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public List<Accounts> findAccountByEmail(String email) {
        List<Accounts> list = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT id,fullname, email, password, phone, address, role FROM accounts WHERE fullname LIKE ?");
            statement.setString(1, "%" + email + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Accounts account = new Accounts(
                        resultSet.getInt("id"),
                        resultSet.getString("fullname"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getInt("role")
                );
                list.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public boolean addAccount(Accounts account) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO accounts (fullname, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, account.getFullName());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getPhone());
            statement.setString(5, account.getAddress());
            statement.setInt(6, account.getRole());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateAccount(Accounts account) {
        String sql = "UPDATE accounts SET fullname = ?, password = ?, phone = ?, address = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, account.getFullName());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getPhone());
            statement.setString(4, account.getAddress());
            statement.setString(5,account.getEmail());
            statement.setInt(6, account.getId());

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update account: " + e.getMessage());
        }
    }
    @Override
    public boolean deleteAccount(Accounts account) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM accounts WHERE id = ?");
            statement.setInt(1, account.getId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int checkLogin(String email, String password) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT id, email, password, fullname, role FROM accounts WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Data.accountID = resultSet.getInt("id");
                Data.fullName = resultSet.getString("fullname");
                return resultSet.getInt("role");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
