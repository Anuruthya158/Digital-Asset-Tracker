package dao;

import model.Asset;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetDAO {

    // Insert new asset
    public boolean addAsset(Asset asset) {
        String query = "INSERT INTO assets (type, brand, serial_number, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, asset.getType());
            ps.setString(2, asset.getBrand());
            ps.setString(3, asset.getSerialNumber());
            ps.setString(4, asset.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View all assets
    public List<Asset> getAllAssets() {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

        	while (rs.next()) {
        	    Asset asset = new Asset();
        	    asset.setAssetId(rs.getInt("asset_id"));
        	    asset.setType(rs.getString("type"));
        	    asset.setBrand(rs.getString("brand"));
        	    asset.setSerialNumber(rs.getString("serial_number")); // make sure this method exists
        	    asset.setAssignedTo(rs.getString("assigned_to"));     // make sure this method exists
        	    asset.setAssignedDate(rs.getDate("assigned_date"));   // make sure this method exists
        	    asset.setStatus(rs.getString("status"));
        	    assets.add(asset);
        	}

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    // Assign an asset to employee
    public boolean assignAsset(int assetId, String employeeName) {
        String query = "UPDATE assets SET assigned_to = ?, assigned_date = ?, status = 'assigned' WHERE asset_id = ? AND status = 'available'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, employeeName);
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, assetId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Return an asset
    public boolean returnAsset(int assetId) {
        String query = "UPDATE assets SET assigned_to = NULL, assigned_date = NULL, status = 'available' WHERE asset_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, assetId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void searchBy(String column, String value) {
        String query = "SELECT * FROM assets WHERE " + column + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("asset_id") +
                                   ", Type: " + rs.getString("type") +
                                   ", Brand: " + rs.getString("brand") +
                                   ", Serial: " + rs.getString("serial_number") +
                                   ", Assigned To: " + rs.getString("assigned_to") +
                                   ", Assigned Date: " + rs.getString("assigned_date") +
                                   ", Status: " + rs.getString("status"));
            }

            if (!found) {
                System.out.println("No assets found matching your search.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Asset> getAssetsByUser(String username) {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets WHERE assigned_to = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Asset asset = new Asset();
                asset.setAssetId(rs.getInt("asset_id"));
                asset.setType(rs.getString("type"));
                asset.setBrand(rs.getString("brand"));
                asset.setSerialNumber(rs.getString("serial_number"));
                asset.setStatus(rs.getString("status"));
                asset.setAssignedTo(rs.getString("assigned_to"));
                asset.setAssignedDate(rs.getDate("assigned_date"));
                assets.add(asset);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching assets by user: " + e.getMessage());
        }

        return assets;
    }

}
