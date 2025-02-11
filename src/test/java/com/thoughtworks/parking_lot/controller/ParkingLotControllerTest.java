package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private ParkingLotRepository parkingLotRepository;

    @Test
    @Transactional
    public void should_return_parking_lot_when_request_create_a_parking_lot_api() throws Exception {
        mockMvc.perform(post("/parking-lots")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{\n" +
                    "\t\"name\": \"william\",\n" +
                    "\t\"capacity\": 100,\n" +
                    "\t\"position\": \"vvwqeewqewqv\"\n" +
                    "}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("william"));
    }

    @Test
    @Transactional
    public void should_return_none_when_request_create_a_parking_lot_api_with_null_name() throws Exception {
        mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "\t\"capacity\": 10,\n" +
                        "\t\"name\": null,\n" +
                        "\t\"position\": \"vvv\"\n" +
                        "}")
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void should_return_200_when_request_delete_a_parking_lot_api() throws Exception {
        mockMvc.perform(delete("/parking-lots/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void should_return_parking_lots_when_request_find_parking_lots_with_page_api() throws Exception {
        mockMvc.perform(get("/parking-lots?page=0&pageSize=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"name\": \"aaa\",\n" +
                        "        \"capacity\": 20,\n" +
                        "        \"position\": \"zhuhai\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"bbb\",\n" +
                        "        \"capacity\": 50,\n" +
                        "        \"position\": \"guangzhou\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 3,\n" +
                        "        \"name\": \"ccc\",\n" +
                        "        \"capacity\": 40,\n" +
                        "        \"position\": \"shenzhen\"\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    @Transactional
    public void should_return_parking_lot_when_request_find_parking_lots_by_id_api() throws Exception {
        mockMvc.perform(get("/parking-lots/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"aaa\",\n" +
                        "    \"capacity\": 20,\n" +
                        "    \"position\": \"zhuhai\"\n" +
                        "}"));
    }

    @Test
    @Transactional
    public void should_return_parking_lot_when_request_update_capacity_api() throws Exception {
        mockMvc.perform(put("/parking-lots/1")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("1000")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"aaa\",\n" +
                        "    \"capacity\": 1000,\n" +
                        "    \"position\": \"zhuhai\"\n" +
                        "}"));
    }
}