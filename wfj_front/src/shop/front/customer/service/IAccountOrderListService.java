package shop.front.customer.service;
import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.order.pojo.Orders;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;
/**
 * IAccountOrderListService - 类描述信息
 */
public interface IAccountOrderListService extends IBaseService<Orders> {
	/**
	 * 用户取消订单
	 * @param ordersId
	 */
	public void saveOrUpdateCancelOrder(Orders order,Customer customer,ShopInfo shopInfo2,Sonaccount son);
	/**
	 * 确认收货
	 * @param ordersId
	 * @param customer
	 */
	public void saveConfirmOrder(Integer ordersId,Customer customer,ShopInfo shopInfo2,Sonaccount son);
//	/**
//	 * 导出
//	 */
//	public boolean madeExl(List<String> listString) throws IOException;
}
