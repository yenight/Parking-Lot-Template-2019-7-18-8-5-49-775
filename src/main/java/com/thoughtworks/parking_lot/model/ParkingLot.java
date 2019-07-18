package com.thoughtworks.parking_lot.model;

import javax.naming.Name;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String name;

    @Min(0)
    private int capacity;

    private String position;

    public ParkingLot() {
    }

    public ParkingLot(@Min(0) int capacity, String name, String position) {
        this.name = name;
        this.capacity = capacity;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
