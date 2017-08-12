package shop.customer.service;
import shop.customer.pojo.CusService;
import shop.customer.pojo.ShopCustomerService;
import util.service.IBaseService;
/**
 * 客服信息Service接口
 * @author wy
 *
 */
public interface ICustomerServiceService extends IBaseService <CusService> {
	/**
	 * 保存客服信息及客服与店铺管理员关联关系
	 * @param cusService客服信息对象
	 * @param shopCustomerService客服与店铺管理员关联关系对象
	 * @return 保存成功为true
	 */
	public void saveOrUpdateCustomerService(CusService cusService,ShopCustomerService shopCustomerService);
}
