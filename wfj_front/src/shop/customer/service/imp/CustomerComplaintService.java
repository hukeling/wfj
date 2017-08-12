package shop.customer.service.imp;
import shop.customer.dao.ICustomerComplaintDao;
import shop.customer.pojo.CustomerComplaint;
import shop.customer.service.ICustomerComplaintService;
import util.service.BaseService;
/**
 * CustomerComplaintService - 客户投诉Service接口实现类
 */
public class CustomerComplaintService extends BaseService  <CustomerComplaint> implements ICustomerComplaintService{
	@SuppressWarnings("unused")
	private ICustomerComplaintDao customerComplaintDao;
	public void setCustomerComplaintDao(ICustomerComplaintDao customerComplaintDao) {
		this.baseDao = this.customerComplaintDao = customerComplaintDao;
	}
}
