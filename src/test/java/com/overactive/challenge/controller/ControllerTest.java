package com.overactive.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.overactive.challenge.entity.Reward;
import com.overactive.challenge.entity.Transaction;

@WebMvcTest
class ControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	void calculateRewardPointsURLTest() throws Exception {
        mockMvc.perform(post("/awards/calculate/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(toJson(testData())))
        .andExpect(status().isOk());
	}

	@Test
	void calculateRewardPointsTest() {
		Controller ct = new Controller();
		ResponseEntity<List<Reward>> rw = ct.calculateRewardPoints(testData(), 1);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(rw);
		System.out.println(json);
	}
	
	private List<Transaction> testData() {
		Transaction tr = new Transaction();
		tr.setId(10);
		tr.setCustomer_id(101);
		tr.setCustomer_name("Client_10");
		tr.setValue(120.5);
		tr.setMonth(1);
		Transaction tr1 = new Transaction();
		tr1.setId(20);
		tr1.setCustomer_id(201);
		tr1.setCustomer_name("Client_20");
		tr1.setValue(60.0);
		tr1.setMonth(1);
		Transaction tr2 = new Transaction();
		tr2.setId(30);
		tr2.setCustomer_id(301);
		tr2.setCustomer_name("Client_30");
		tr2.setValue(70.0);
		tr2.setMonth(1);
		Transaction tr3 = new Transaction();
		tr3.setId(10);
		tr3.setCustomer_id(101);
		tr3.setCustomer_name("Client_10");
		tr3.setValue(150.0);
		tr3.setMonth(1);
		Transaction tr4 = new Transaction();
		tr4.setId(40);
		tr4.setCustomer_id(401);
		tr4.setCustomer_name("Client_40");
		tr4.setValue(55.0);
		tr4.setMonth(2);
		
		List<Transaction> rec = new ArrayList<>();
		rec.add(tr);
		rec.add(tr1);
		rec.add(tr2);
		rec.add(tr3);
		rec.add(tr4);
		return rec;
	}
	
	public static String toJson(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
