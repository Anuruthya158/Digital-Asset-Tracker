package controller;

import dao.AssetDAO;
import model.Asset;
import java.util.List;

public class AssetController {
    private AssetDAO assetDAO = new AssetDAO();

    public boolean addAsset(String type, String brand, String serialNumber) {
        Asset asset = new Asset(type, brand, serialNumber, "available");
        return assetDAO.addAsset(asset);
    }

    public List<Asset> viewAllAssets() {
        return assetDAO.getAllAssets();
    }

    public boolean assignAsset(int assetId, String employeeName) {
        return assetDAO.assignAsset(assetId, employeeName);
    }

    public boolean returnAsset(int assetId) {
        return assetDAO.returnAsset(assetId);
    }

    public List<Asset> viewAssetsByUser(String username) {
        return assetDAO.getAssetsByUser(username);
    }
}
