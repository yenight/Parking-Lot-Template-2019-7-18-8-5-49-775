package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.Exception.NoParkingSpaceException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParkingOrderController {
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping("/parking-orders")
    public ResponseEntity findOrders() {
        return ResponseEntity.ok(parkingLotRepository.findAll());
    }

    @PostMapping("/parking-orders")
    public ResponseEntity createOrder(@RequestBody ParkingOrder order) throws NoParkingSpaceException {
        ParkingLot parkingLot = parkingLotRepository.findAll().stream()
                .filter(x-> x.getName().equals(order.getParkingLotName()))
                .findFirst()
                .orElse(null);
        List<ParkingOrder> parkingOrders = parkingOrderRepository.findAll().stream()
                .filter(x -> {
                    assert parkingLot != null;
                    return x.getParkingLotName().equals(parkingLot.getName()) && x.isOrderStatus();
                })
                .collect(Collectors.toList());
        assert parkingLot != null;
        if (parkingOrders.size() >= parkingLot.getCapacity()) {
            return ResponseEntity.badRequest().body("停车场已经满");
            // throw new NoParkingSpaceException("停车场已经满");
        } else {
            ParkingOrder createdOrder = parkingOrderRepository.saveAndFlush(order);
            return ResponseEntity.ok(createdOrder);
        }
    }

    @PutMapping("/parking-orders/{id}")
    public ResponseEntity updateOrder(@PathVariable long id) {
        ParkingOrder parkingOrder = parkingOrderRepository.findById(id).orElse(null);
        assert parkingOrder != null;
        if (parkingOrder.isOrderStatus()) {
            parkingOrder.setOrderStatus(false);
            parkingOrder.setLeavedTime(Instant.now().getEpochSecond());
            return ResponseEntity.ok(parkingOrder);
        }
        return ResponseEntity.badRequest().build();
    }
}
