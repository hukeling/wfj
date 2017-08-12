package shop.customer.service.imp;
import shop.customer.dao.ICustomerMsgDao;
import shop.customer.pojo.CustomerMsg;
import shop.customer.service.ICustomerMsgService;
import util.service.BaseService;
/**
 * 用户留言Service接口实现类
 * @author ss
 *
 */
public class CustomerMsgService extends BaseService  <CustomerMsg> implements ICustomerMsgService{
	@SuppressWarnings("unused")
	private ICustomerMsgDao customerMsgDao;
	public void setCustomerMsgDao(ICustomerMsgDao customerMsgDao) {
		this.baseDao = this.customerMsgDao = customerMsgDao;
	}
}