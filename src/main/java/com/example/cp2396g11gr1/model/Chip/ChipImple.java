package com.example.cp2396g11gr1.model.Chip;

import com.example.cp2396g11gr1.config.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChipImple implements ChipDAO {
    Connection conn = MyConnection.getConnection();

    @Override
    public boolean addCategory(Chip chip) {
        try {
            String query = "INSERT INTO chip (chipName) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, chip.getName());
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateCategory(Chip chip) {
        try {
            String query = "UPDATE chip SET chipName = ? WHERE chipID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, chip.getName());
            statement.setInt(2, chip.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Chip updated successfully!");
            } else {
                System.out.println("No chip found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteCategory(Chip chip) {
        try {
            String query = "DELETE FROM chip WHERE chipID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, chip.getId());
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Chip> getAllCategory() {
        List<Chip> chips = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM chip");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Chip chip = new Chip();
                chip.setId(rs.getInt("chipID"));
                chip.setName(rs.getString("chipName"));
                chips.add(chip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chips;
    }

    @Override
    public List<Chip> searchCategory(String keyword) {
        List<Chip> chips = new ArrayList<>();
        String query = "SELECT * FROM chip WHERE chipName LIKE ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Chip chip = new Chip();
                chip.setId(rs.getInt("chipID"));
                chip.setName(rs.getString("chipName"));
                chips.add(chip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chips;
    }
}
