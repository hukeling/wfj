package shop.front.store.service;
import shop.cityCourier.pojo.CityCourier;
import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.order.pojo.Orders;
import shop.order.pojo.Shipping;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;
/**
 * IShopOrdersService - 类描述信息
 */
public interface IShopOrdersService extends IBaseService<Orders> {
	/**
	 * 更改订单已发货状态(走物流公司)
	 * @return
	 */
	public Orders saveOrUpdateChangeShipments(Orders orders ,Shipping shipping,Customer customer,ShopInfo shopInfo,Sonaccount son);
	/**
	 * 更改订单已发货状态(走同城快递)
	 * @return
	 */
	public Orders saveOrUpdateChangeShipments(Orders orders ,CityCourier cityCourier,Customer customer,ShopInfo shopInfo,Sonaccount son);
	/**
	 * 更改订单正在配送状态
	 * @return
	 */
	public Orders saveOrUpdateChanggeOrdersState(Orders orders ,Customer customer,ShopInfo shopInfo,Sonaccount son);
}
