package shop.front.customer.action;
import java.util.List;

import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import util.action.BaseAction;
public class ShippingAddressesAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ICustomerAcceptAddressService customerAcceptAddressService ;
	private List<CustomerAcceptAddress> addressList; //shopping address list
	/**
	 * 购物地址列表
	 * @return
	 */
	public String addressList(){
		int userId = 1;
		addressList = customerAcceptAddressService.findObjects(" where customerId = "+ userId);
		return SUCCESS;
	}
	public ICustomerAcceptAddressService getCustomerAcceptAddressService() {
		return customerAcceptAddressService;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public List<CustomerAcceptAddress> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<CustomerAcceptAddress> addressList) {
		this.addressList = addressList;
	}
}
