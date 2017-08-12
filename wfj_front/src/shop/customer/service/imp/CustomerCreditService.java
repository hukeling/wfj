package shop.customer.service.imp;
import shop.customer.dao.ICustomerCreditDao;
import shop.customer.pojo.CustomerCredit;
import shop.customer.service.ICustomerCreditService;
import util.service.BaseService;
/**
 * 会员等级Service接口实现类
 * @author ss
 *
 */
public class CustomerCreditService extends BaseService  <CustomerCredit> implements ICustomerCreditService{
	@SuppressWarnings("unused")
	private ICustomerCreditDao customerCreditDao;
	public void setCustomerCreditDao(ICustomerCreditDao customerCreditDao) {
		this.baseDao = this.customerCreditDao = customerCreditDao;
	}
}
