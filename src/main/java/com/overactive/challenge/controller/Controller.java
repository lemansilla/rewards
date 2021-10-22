package com.overactive.challenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.overactive.challenge.entity.Reward;
import com.overactive.challenge.entity.Transaction;

@RestController
@RequestMapping("/awards")
public class Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	private static final int REWARD_2_MUL = 2;
	private static final int REWARD_1_MUL = 1;
	
	//Given a record of every transaction during a three month period.
	//Supposition: those three months belong to a one year only.
	@PostMapping("/calculate/{m1}")
	public ResponseEntity<List<Reward>> calculateRewardPoints(@RequestBody(required=false) List<Transaction> record,
			@PathVariable(value="m1") int m1) {
		
		if (m1 < 0 || m1 > 10 || record == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		//Get the data for ever month (I can move all this calculation to a SERVICE)
		List<Transaction> fm1 = getMonthData(record, m1);
		List<Transaction> fm2 = getMonthData(record, m1 + 1);
		List<Transaction> fm3 = getMonthData(record, m1 + 2);
		List<List<Transaction>> dataByMonth = new ArrayList<>();
		dataByMonth.add(fm1);
		dataByMonth.add(fm2);
		dataByMonth.add(fm3);
		
		List<Reward> result = new ArrayList<>();
		for (List<Transaction> data : dataByMonth) {
			//Get 2 points reward from the whole list
			List<Transaction> filter1 = data.stream().filter(x -> x.getValue() > 100.0).collect(Collectors.toList());
			//filter1.forEach(x -> System.out.println(x.getValue()));	
			
			//Get 1 points reward from the whole list
			List<Transaction> filter2 = data.stream().
					filter(x -> (x.getValue() > 50.0 && x.getValue() <= 100.0)).collect(Collectors.toList());
			
			//Get list of customers
			List<Integer> customerList = data.stream().map(Transaction::getCustomer_id).collect(Collectors.toList());
			List<Integer> customers = customerList.stream().distinct().collect(Collectors.toList()); //Unique list of customers
			
			//Get the final reward list
			for (Integer cust : customers) {
				Reward rw = new Reward();
				rw.setId(cust);
				filter1.forEach(x -> { if (x.getCustomer_id() == cust) {
					log.info("called ... {}", cust);
					rw.setReward1(REWARD_2_MUL * x.getValue().intValue()); }
				});
				filter2.forEach(x -> { if (x.getCustomer_id() == cust) {
					rw.setReward1(REWARD_1_MUL * x.getValue().intValue()); }
				});
				rw.setTotal();
				result.add(rw);
			}
		}
		
		log.info("Calculating rewards points for customer id {}", record);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	private List<Transaction> getMonthData(List<Transaction> record, int month) {
		List<Transaction> fm1 = record.stream().filter(x -> x.getMonth() == month).collect(Collectors.toList());
		return fm1;
	}

}
