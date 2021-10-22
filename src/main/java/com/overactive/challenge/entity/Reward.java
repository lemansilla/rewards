package com.overactive.challenge.entity;

public class Reward {
	
	int id;
	String Customer;
	Integer reward1;
	Integer reward2;
	Integer reward3;
	Integer total;
	
	public Reward() {
		reward1=0;
		reward2=0;
		reward3=0;
		total=0;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public Integer getReward1() {
		return reward1;
	}
	public void setReward1(Integer reward1) {
		this.reward1 += reward1;
	}
	public Integer getReward2() {
		return reward2;
	}
	public void setReward2(Integer reward2) {
		this.reward2 += reward2;
	}
	public Integer getReward3() {
		return reward3;
	}
	public void setReward3(Integer reward3) {
		this.reward3 += reward3;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal() {
		this.total = reward1 + reward2 + reward3;
	}

}
