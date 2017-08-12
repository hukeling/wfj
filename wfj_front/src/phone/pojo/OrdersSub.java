package phone.pojo;

import java.util.List;

import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
/**
 * 继承自Orders;额外添加了订单明细list，订单创建时间和店铺联系电话
 * @author hukeling
 *
 */
public class OrdersSub extends Orders {
	private List<OrdersList> list;
	private String creatTime;
	private String shopPhone;
	 
	public void setList(List<OrdersList> list) {
		this.list = list;
	}


	public List<OrdersList> getList() {
		return list;
	}

	
	public String getCreatTime() {
		return creatTime;
	}

	public String getShopPhone() {
		return shopPhone;
	}


	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}


	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}


	
	
}
