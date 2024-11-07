package com.example.cp2396g11gr1.model.bill;



import com.example.cp2396g11gr1.config.MyConnection;
import com.example.cp2396g11gr1.controller.Order_out;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillImple implements BillDAO{
    @Override
    public Map<Timestamp, Double> sumBillDays() {
        Map<Timestamp, Double> billSum = new HashMap<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT date, SUM(total) AS total_sum FROM bill GROUP BY date ORDER BY date;"
            );
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("date");
                double totalSum = rs.getDouble("total_sum");
                billSum.put(timestamp, totalSum);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return billSum;
    }
    @Override
    public Map<Timestamp, Double> sumBill() {
        Map<Timestamp, Double> billSumByDate = new HashMap<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT date, SUM(total) AS total_sum FROM bill GROUP BY date ORDER BY date;"
            );
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("date"); // Sử dụng Timestamp để có cả ngày và giờ
                double totalSum = rs.getDouble("total_sum");
                billSumByDate.put(timestamp, totalSum);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return billSumByDate;
    }

    Connection conn = MyConnection.getConnection();

    @Override
    public void updateOrder(Bills bills) {
        try{
            PreparedStatement statement = conn.prepareStatement("update bill set status = ? where billID = ?");
            statement.setInt(1,1);
            statement.setInt(2,bills.getBillID());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateStatusBill(Bills bills) {
        try{
            PreparedStatement statement = conn.prepareStatement("UPDATE bill set status = ? where billID = ?");
            statement.setInt(1,1);
            statement.setInt(2,bills.getBillID());
            statement.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Order_out> showDetailsBill(Bills bills) {
        List<Order_out> orderList = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT id,productName,price,quantity FROM orders WHERE billID = ?");
            statement.setInt(1, bills.getBillID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order_out orderOut = new Order_out();
                orderOut.setOrder_id(resultSet.getInt("id"));
                orderOut.setPrice(resultSet.getInt("price"));
                orderOut.setProductName(resultSet.getString("productName"));
                orderOut.setQuantity(resultSet.getInt("quantity"));
                orderList.add(orderOut);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    @Override
    public List<Bills> getAllBillDateNow() {
        List<Bills> bills = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime startOfDay = LocalDateTime.of(today, midnight);

        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT b.billID, b.customerID, b.total, b.date, c.fullname " +
                            "FROM bill b " +
                            "LEFT JOIN customers c ON b.customerID = c.customerID " +
                            "WHERE b.status = 1 AND b.date >= ? AND b.date < ?"
            );
            statement.setTimestamp(1, Timestamp.valueOf(startOfDay));
            statement.setTimestamp(2, Timestamp.valueOf(startOfDay.plusDays(1)));

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Bills bill = new Bills();
                bill.setBillID(rs.getInt("billID"));
                bill.setCustomerID(rs.getInt("customerID"));
                bill.setTotalPrice(rs.getDouble("total"));
                bill.setDate(new Date(rs.getTimestamp("date").getTime()));
                bill.setFullName(rs.getString("fullname"));
                bills.add(bill);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bills;
    }



    @Override
    public List<Bills> getAllBills() {
        List<Bills> bills = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT * from bill where status = 0"
            );
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Bills bill = new Bills();
                bill.setBillID(rs.getInt("id"));
                bill.setDate(new Date(rs.getDate("date").getTime()));
                bill.setTotalPrice(rs.getDouble("total"));
                bills.add(bill);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bills;
    }

    @Override
    public List<Bills> getAllBills2() {
        List<Bills> bills = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM bill where status = 0");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Bills bill = new Bills();
                bill.setBillID(rs.getInt("billID"));
                bill.setCustomerID(rs.getInt("customerID"));
                bill.setTotalPrice(rs.getDouble("total"));
                bill.setDate(new Date(rs.getDate("date").getTime()));
                bills.add(bill);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bills;
    }

    @Override
    public boolean addBill(Bills bills) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        try {
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO bill(date, total,status) VALUES ( ?, ?, 0)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setTimestamp(1, new Timestamp(bills.getDate().getTime()));
            statement.setObject(2, bills.getTotalPrice());

            int check = statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            int newBillID = 0;
            if (rs.next()) {
                newBillID = rs.getInt(1);
            }

            PreparedStatement updateStatement = conn.prepareStatement(
                    "UPDATE orders SET billID = ? WHERE order_details = ?"
            );
            updateStatement.setInt(1, newBillID);
            updateStatement.setInt(2, 1);

            int updateCheck = updateStatement.executeUpdate();


            PreparedStatement updateStatusStatement = conn.prepareStatement(
                    "UPDATE orders SET order_details = 0 WHERE status = 0"
            );
            int updateStatusCheck = updateStatusStatement.executeUpdate();

            // Hoàn tất transaction
            conn.commit();

            return check > 0 && updateCheck > 0 && updateStatusCheck > 0;

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
