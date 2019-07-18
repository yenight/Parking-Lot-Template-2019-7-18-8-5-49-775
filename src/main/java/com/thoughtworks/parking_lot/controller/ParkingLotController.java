package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @PostMapping("/parking-lots")
    public ResponseEntity createParkingLot(@RequestBody ParkingLot parkingLot) {
        try {
            ParkingLot createdParkingLot = parkingLotRepository.saveAndFlush(parkingLot);
            return ResponseEntity.ok(createdParkingLot);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/parking-lots/{id}")
    public int deleteParkingLot(@PathVariable long id) {
        if (id > 0) {
            parkingLotRepository.deleteById(id);
            return 200;
        }
        return 400;
    }

}
