package shop.order.service.imp;
import shop.order.dao.IOrdersDao;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import util.service.BaseService;
/**
 * 订单service层实现类
 * @author 张攀攀
 *
 */
public class OrdersService extends BaseService  <Orders> implements IOrdersService{
	@SuppressWarnings("unused")
	private IOrdersDao ordersDao;
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.baseDao =this.ordersDao= ordersDao;
	}
}