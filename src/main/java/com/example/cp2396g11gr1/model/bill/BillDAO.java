package com.example.cp2396g11gr1.model.bill;



import com.example.cp2396g11gr1.controller.Order_out;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface BillDAO {

    public boolean addBill(Bills bills);
    public List<Bills> getAllBills();
    public List<Bills> getAllBills2();
    public List<Bills> getAllBillDateNow();
    public List<Order_out> showDetailsBill(Bills bills);
    public boolean updateStatusBill(Bills bills);
    public void updateOrder(Bills bills);
    public Map<Timestamp, Double> sumBill();
    public Map<Timestamp, Double> sumBillDays();

}
