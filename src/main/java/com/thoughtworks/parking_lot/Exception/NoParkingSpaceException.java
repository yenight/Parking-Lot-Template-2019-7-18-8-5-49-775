package com.thoughtworks.parking_lot.Exception;

public class NoParkingSpaceException extends Exception {
    public NoParkingSpaceException(String message) {
        super(message);
    }
}
