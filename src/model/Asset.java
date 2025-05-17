package model;

import java.sql.Date;

public class Asset {
   
	private int assetId;
    private String type;
    private String brand;
    private String serialNumber;
    private String assignedTo;
    private Date assignedDate;
    private String status;

    // No-arg constructor (required for result mapping)
    public Asset() {}

    // Constructor used when inserting a new asset
    public Asset(String type, String brand, String serialNumber, String status) {
    	
        this.type = type;
        this.brand = brand;
        this.serialNumber = serialNumber;
        this.status = status;
    }

    
    // ==== GETTERS ====
    public int getAssetId() {
        return assetId;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public String getStatus() {
        return status;
    }

    // ==== SETTERS ====
    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
