package shop.order.service.imp;
import shop.order.dao.IShippingDao;
import shop.order.pojo.Shipping;
import shop.order.service.IShippingService;
import util.service.BaseService;
/**
 * 发货详情Service接口实现类
 * @author ss
 *
 */
public class ShippingService extends BaseService<Shipping> implements IShippingService{
	@SuppressWarnings("unused")
	private IShippingDao shippingDao;
	public void setShippingDao(IShippingDao shippingDao) {
		this.baseDao = this.shippingDao = shippingDao;
	}
}
