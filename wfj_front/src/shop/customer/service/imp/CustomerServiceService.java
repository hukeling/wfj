package shop.customer.service.imp;
import shop.customer.dao.ICustomerServiceDao;
import shop.customer.dao.IShopCustomerServiceDao;
import shop.customer.pojo.CusService;
import shop.customer.pojo.ShopCustomerService;
import shop.customer.service.ICustomerServiceService;
import util.service.BaseService;
/**
 * 客户信息Service接口实现类
 * 
 * @author ss
 * 
 */
public class CustomerServiceService extends BaseService<CusService> implements
		ICustomerServiceService {
	private ICustomerServiceDao customerServiceDao;
	private IShopCustomerServiceDao shopCustomerServiceDao;
	
	/**
	 * 保存客服信息及客服与店铺管理员关联关系
	 * @param cusService客服信息对象
	 * @param shopCustomerService客服与店铺管理员关联关系对象
	 * @return 保存成功为true
	 */
	public void saveOrUpdateCustomerService(CusService cusService,ShopCustomerService shopCustomerService) {
		cusService = customerServiceDao.saveOrUpdateObject(cusService);
		shopCustomerService.setCustomerServiceId(cusService.getCustomerServiceId());
		shopCustomerServiceDao.saveOrUpdateObject(shopCustomerService);
	}
	
	public void setCustomerServiceDao(ICustomerServiceDao customerServiceDao) {
		this.baseDao = this.customerServiceDao = customerServiceDao;
	}
	public void setShopCustomerServiceDao(IShopCustomerServiceDao shopCustomerServiceDao) {
		this.shopCustomerServiceDao = shopCustomerServiceDao;
	}
}
