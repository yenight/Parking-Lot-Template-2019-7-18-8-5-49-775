package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingOrderService {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public boolean createOrder(ParkingOrder order) {
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
        return parkingOrders.size() >= parkingLot.getCapacity();
    }

    public List<ParkingOrder> findAll() {
        return parkingOrderRepository.findAll();
    }

    public ParkingOrder save(ParkingOrder order) {
        return parkingOrderRepository.saveAndFlush(order);
    }

    public ParkingOrder uodate(long id) {
        ParkingOrder parkingOrder = parkingOrderRepository.findById(id).orElse(null);
        assert parkingOrder != null;
        if (parkingOrder.isOrderStatus()) {
            parkingOrder.setOrderStatus(false);
            parkingOrder.setLeavedTime(Instant.now().getEpochSecond());
            return parkingOrder;
        } else {
            return null;
        }

    }
}
