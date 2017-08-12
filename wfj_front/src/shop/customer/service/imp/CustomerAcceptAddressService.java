package shop.customer.service.imp;
import java.util.List;

import shop.customer.dao.ICustomerAcceptAddressDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import util.service.BaseService;
/**
 * 用户地址Service接口实现类
 * @author ss
 *
 */
@SuppressWarnings("all")
public class CustomerAcceptAddressService extends BaseService  <CustomerAcceptAddress> implements ICustomerAcceptAddressService{
	private ICustomerAcceptAddressDao customerAcceptAddressDao;
	public void setCustomerAcceptAddressDao(ICustomerAcceptAddressDao customerAcceptAddressDao) {
		this.baseDao = this.customerAcceptAddressDao = customerAcceptAddressDao;
	}
	/**
	 * 设置默认地址
	 * @param customer 当前登录人
	 * @param CAAID 操作的地址的Id
	 * @return 默认地址
	 */
	public CustomerAcceptAddress saveOrUpdateSendAddress(Customer customer,Integer CAAId){
		//找到之前所有的默认收货地址更改成普通的地址
		List<CustomerAcceptAddress> list = customerAcceptAddressDao.findObjects("where o.isSendAddress=1 and o.customerId="+customer.getCustomerId());
		for(CustomerAcceptAddress CAA :list){
			CAA.setIsSendAddress(0);
			customerAcceptAddressDao.saveOrUpdateObject(CAA);
		}
		//设置默认地址
		if(CAAId!=null){
			CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressDao.get("where o.customerAcceptAddressId="+CAAId+" and o.customerId="+customer.getCustomerId());
			customerAcceptAddress.setIsSendAddress(1);
			customerAcceptAddress = customerAcceptAddressDao.saveOrUpdateObject(customerAcceptAddress);
			return customerAcceptAddress;
		}
		return null;
	}
	/**
	 * 
	 */
	public void updateIsSendAddress(Customer customer){
		customerAcceptAddressDao.updateObject(" update shop_customeracceptaddress a set a.isSendAddress = 0 where a.customerId = "+customer.getCustomerId()+" and a.isSendAddress=1 ");
		//找到之前所有的默认收货地址更改成普通的地址
/*		List<CustomerAcceptAddress> list = customerAcceptAddressDao.findObjects("where o.isSendAddress=1 and o.customerId="+customer.getCustomerId());
		for(CustomerAcceptAddress CAA :list){
			CAA.setIsSendAddress(0);
			customerAcceptAddressDao.saveOrUpdateObject(CAA);
		}*/
	}
}