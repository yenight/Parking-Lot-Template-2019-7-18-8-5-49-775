package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Test
    @Transactional
    public void should_return_order_when_request_create_an_order_api() throws Exception {
        mockMvc.perform(post("/parking-orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "  \"parkingLotName\": \"aaa\",\n" +
                        "  \"numberPlate\": 123,\n" +
                        "  \"createdTime\": 1563701889,\n" +
                        "  \"leavedTime\": 0,\n" +
                        "  \"orderStatus\": true\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberPlate").value("123"))
                .andExpect(jsonPath("$.parkingLotName").value("aaa"));
    }

    @Test
    @Transactional
    public void should_return_order_when_request_update_an_order_api() throws Exception {
        mockMvc.perform(put("/parking-orders/26"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value(false));
    }

    @Test
    @Transactional
    public void should_return_null_when_request_create_an_order_api_with_parking_lot_has_not_space() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("bbb", 4399, 1563701889, 0, true);
        parkingOrderRepository.save(parkingOrder);

        mockMvc.perform(post("/parking-orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "  \"parkingLotName\": \"bbb\",\n" +
                        "  \"numberPlate\": 123456,\n" +
                        "  \"createdTime\": 1563701889,\n" +
                        "  \"leavedTime\": 0,\n" +
                        "  \"orderStatus\": true\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}