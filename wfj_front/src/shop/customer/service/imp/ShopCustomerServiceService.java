package shop.customer.service.imp;
import shop.customer.dao.IShopCustomerServiceDao;
import shop.customer.pojo.ShopCustomerService;
import shop.customer.service.IShopCustomerServiceService;
import util.service.BaseService;
/**
 * 会员—客服信息Service接口实现类
 * 
 * @author wy
 * 
 */
public class ShopCustomerServiceService extends BaseService<ShopCustomerService> implements
		IShopCustomerServiceService {
	@SuppressWarnings("unused")
	private IShopCustomerServiceDao shopCustomerServiceDao;
	public void setShopCustomerServiceDao(IShopCustomerServiceDao shopCustomerServiceDao) {
		this.baseDao = this.shopCustomerServiceDao = shopCustomerServiceDao;
	}
}
