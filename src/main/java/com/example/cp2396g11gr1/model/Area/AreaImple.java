package com.example.cp2396g11gr1.model.Area;

import com.example.cp2396g11gr1.config.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AreaImple implements AreaDAO {

    // Get the connection to the database
    Connection conn = MyConnection.getConnection();

    // Add a new Area
    @Override
    public boolean addArea(Area area) {
        try {
            String query = "INSERT INTO area (areaName) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, area.getName());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing Area
    @Override
    public void updateArea(Area area) {
        try {
            String query = "UPDATE area SET areaName = ? WHERE areaID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, area.getName());
            statement.setInt(2, area.getId());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Area updated successfully!");
            } else {
                System.out.println("No area found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete an Area by its ID
    @Override
    public boolean deleteArea(Area area) {
        try {
            String query = "DELETE FROM area WHERE areaID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, area.getId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all areas from the database
    @Override
    public List<Area> GetAllArea() {
        List<Area> areas = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM area");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Area area = new Area();
                area.setId(rs.getInt("areaID"));
                area.setName(rs.getString("areaName"));
                areas.add(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areas;
    }

    // Search for areas based on a keyword (by name)
    @Override
    public List<Area> searchArea(String keyword) {
        List<Area> areas = new ArrayList<>();
        String query = "SELECT * FROM area WHERE areaName LIKE ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Area area = new Area();
                area.setId(rs.getInt("areaID"));
                area.setName(rs.getString("areaName"));
                areas.add(area);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areas;
    }
}
