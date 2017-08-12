package shop.customer.service;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import util.service.IBaseService;
/**
 * 用户地址Service接口
 * @author ss
 *
 */
public interface ICustomerAcceptAddressService  extends IBaseService <CustomerAcceptAddress> {
	/**
	 * 设置默认地址
	 * @param customer 当前登录人
	 * @param CAAID 操作的地址的Id
	 * @return 默认地址
	 */
	public CustomerAcceptAddress saveOrUpdateSendAddress(Customer customer,Integer CAAId);
	/**
	 * 
	 * @param customer
	 */
	public void updateIsSendAddress(Customer customer);
}
