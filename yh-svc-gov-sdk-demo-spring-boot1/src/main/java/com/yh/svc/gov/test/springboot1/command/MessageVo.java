/**
 * 
 */
package com.yh.svc.gov.test.springboot1.command;

import java.util.List;

/**
 * @author luchao 2019-01-08
 *
 */
public class MessageVo {
	private String appId;
	private String node;
	private String comment;
	private List<OrderVo> orders;
	
	public MessageVo() {}
	
	
	public MessageVo(String appId, String node, String comment, List<OrderVo> orders) {
		super();
		this.appId = appId;
		this.node = node;
		this.comment = comment;
		this.orders = orders;
	}


	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public List<OrderVo> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderVo> orders) {
		this.orders = orders;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "MessageVo [appId=" + appId + ", node=" + node + "]";
	}
	
}
