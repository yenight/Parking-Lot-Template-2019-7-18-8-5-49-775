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
//            if (parkingLot.getName() == null) {
//                throw new Exception();
//            }
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
    public ResponseEntity getParkingLots(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "15") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
        Page<ParkingLot> parkingLots =  parkingLotRepository.findAll(pageable);
        return ResponseEntity.ok(parkingLots.getContent());
    }

    @GetMapping("/parking-lots/{id}")
    public ResponseEntity getParkingLotById(@PathVariable int id) {
        ParkingLot parkingLot =  parkingLotRepository.findById((long)id).orElse(null);
        return ResponseEntity.ok(parkingLot);
    }

    @PutMapping("/parking-lots/{id}")
    public ResponseEntity updateParkingLotById(@PathVariable long id, @RequestBody int newCount) {
        ParkingLot parkingLot = parkingLotRepository.findById(id).orElse(null);
        parkingLot.setCapacity(newCount);
        ParkingLot savedParkingLot = parkingLotRepository.saveAndFlush(parkingLot);
        return ResponseEntity.ok(savedParkingLot);
    }
}
