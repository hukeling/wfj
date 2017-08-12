package shop.customer.service;
import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerInfo;
import util.service.IBaseService;
/**
 * 会员个性信息Service接口
 * @author ss
 *
 */
public interface IShopCustomerInfoService extends IBaseService <ShopCustomerInfo> {
	public Boolean saveOrUpdateCustomerInfo(Customer customer,ShopCustomerInfo customerInfo);
}
