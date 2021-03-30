/**
 * 
 */
package com.yh.svc.gov.test.springboot2.command;

import java.util.List;


/**
 * @author luchao 2019-01-08
 *
 */
public class OrderVo {
	private String orderCode;
	private String address;
	private String name;
	private int status;
	private float amount;
	private List<OrderDetailVo> details;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public List<OrderDetailVo> getDetails() {
		return details;
	}
	public void setDetails(List<OrderDetailVo> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		System.out.println(" enter the OrderVo. toString...............");
		return "OrderVo [orderCode=" + orderCode + ", address=" + address + ", name=" + name + ", status=" + status
				+ ", amount=" + amount + ", details size=" + (details==null? 0: details.size()) + "]";
	}
	
	
	
}
