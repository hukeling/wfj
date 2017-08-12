package shop.order.service.imp;
import shop.order.dao.IOrdersOPLogDao;
import shop.order.pojo.OrdersOPLog;
import shop.order.service.IOrdersOPLogService;
import util.service.BaseService;
/**
 * 订单操作日志Service层实现类
 * @author 张攀攀
 *
 */
public class OrdersOPLogService extends BaseService<OrdersOPLog> implements IOrdersOPLogService{
	@SuppressWarnings("unused")
	private IOrdersOPLogDao ordersOPLogDao;
	public void setOrdersOPLogDao(IOrdersOPLogDao ordersOPLogDao) {
		this.baseDao = this.ordersOPLogDao = ordersOPLogDao;
	}
}