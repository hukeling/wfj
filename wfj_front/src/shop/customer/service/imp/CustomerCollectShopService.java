package shop.customer.service.imp;
import shop.customer.dao.ICustomerCollectShopDao;
import shop.customer.pojo.CustomerCollectShop;
import shop.customer.service.ICustomerCollectShopService;
import util.service.BaseService;
/**
 * 用户收藏Service接口实现类
 * @author ss
 *
 */
public class CustomerCollectShopService extends BaseService  <CustomerCollectShop> implements ICustomerCollectShopService{
	@SuppressWarnings("unused")
	private ICustomerCollectShopDao customerCollectShopDao;
	public void setCustomerCollectShopDao(
			ICustomerCollectShopDao customerCollectShopDao) {
		this.baseDao = this.customerCollectShopDao = customerCollectShopDao;
	}
}