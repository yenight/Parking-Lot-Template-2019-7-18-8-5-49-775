package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/parking-lots")
    public ResponseEntity getParkingLots(@RequestParam int page, @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
        Page<ParkingLot> parkingLots =  parkingLotRepository.findAll(pageable);
        return ResponseEntity.ok(parkingLots.getContent());
    }

}
