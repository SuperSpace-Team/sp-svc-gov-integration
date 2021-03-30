/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

/**
 * @author luchao 2019-01-08
 *
 */
public class OrderDetailVo {
	private String sku;
	private int count;
	private double price;
	private double total;
	private String skuName;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	@Override
	public String toString() {
		return "OrderDetailVo [sku=" + sku + ", count=" + count + ", price=" + price + ", total=" + total + ", skuName="
				+ skuName + "]";
	}
	
	

}
