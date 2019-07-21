package com.thoughtworks.parking_lot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ParkingOrder {
    @Id
    @GeneratedValue
    private long id;

    private String parkingLotName;
    private int numberPlate;
    private long createdTime;
    private long leavedTime;
    private boolean orderStatus = true;

    public ParkingOrder() {
    }

    public ParkingOrder(String parkingLotName, int numberPlate, long createdTime, long leavedTime, boolean orderStatus) {
        this.parkingLotName = parkingLotName;
        this.numberPlate = numberPlate;
        this.createdTime = createdTime;
        this.leavedTime = leavedTime;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public int getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(int numberPlate) {
        this.numberPlate = numberPlate;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getLeavedTime() {
        return leavedTime;
    }

    public void setLeavedTime(long leavedTime) {
        this.leavedTime = leavedTime;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
}
