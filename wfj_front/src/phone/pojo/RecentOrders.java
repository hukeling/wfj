package phone.pojo;

import java.util.List;

import shop.order.pojo.*;

public class RecentOrders {
	private String ordersId;
	
	private String ordersNo;
	
	private String finalAmount;
	
	private List<OrdersList> list;

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}

	public void setList(List<OrdersList> list) {
		this.list = list;
	}

	public String getOrdersId() {
		return ordersId;
	}

	public String getOrdersNo() {
		return ordersNo;
	}

	public String getFinalAmount() {
		return finalAmount;
	}

	public List<OrdersList> getList() {
		return list;
	}
	
	
	
}
