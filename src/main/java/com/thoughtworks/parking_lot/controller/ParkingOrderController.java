package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.Exception.NoParkingSpaceException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @GetMapping("/parking-orders")
    public ResponseEntity findOrders() {
        return ResponseEntity.ok(parkingOrderService.findAll());
    }

    @PostMapping("/parking-orders")
    public ResponseEntity createOrder(@RequestBody ParkingOrder order) throws NoParkingSpaceException {
        if (parkingOrderService.createOrder(order)) {
            return ResponseEntity.badRequest().body("停车场已经满");
            // throw new NoParkingSpaceException("停车场已经满");
        } else {
            ParkingOrder createdOrder = parkingOrderService.save(order);
            return ResponseEntity.ok(createdOrder);
        }
    }

    @PutMapping("/parking-orders/{id}")
    public ResponseEntity updateOrder(@PathVariable long id) {
        ParkingOrder parkingOrder = parkingOrderService.uodate(id);
        if (parkingOrder == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(parkingOrder);
    }
}
