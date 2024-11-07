package com.example.cp2396g11gr1.model.account;

import java.util.List;

public interface AccountDAO {
    public int checkLogin(String email, String password);
    public List<Accounts> AllStaff();
    public List<Accounts> findAccountByEmail(String fullName);
    public boolean addAccount(Accounts account);
    public boolean updateAccount(Accounts account);
    public boolean deleteAccount(Accounts account);
}
