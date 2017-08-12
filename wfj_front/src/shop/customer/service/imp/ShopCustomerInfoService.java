package shop.customer.service.imp;
import shop.customer.dao.ICustomerDao;
import shop.customer.dao.IShopCustomerInfoDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.IShopCustomerInfoService;
import util.service.BaseService;
/**
 * 会员个性信息Service接口实现类
 * @author ss
 *
 */
public class ShopCustomerInfoService extends BaseService  <ShopCustomerInfo> implements IShopCustomerInfoService{
	private IShopCustomerInfoDao shopCustomerInfoDao;
	private ICustomerDao customerDao;
	public void setShopCustomerInfoDao(IShopCustomerInfoDao shopCustomerInfoDao) {
		this.baseDao = this.shopCustomerInfoDao = shopCustomerInfoDao;
	}
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	public Boolean saveOrUpdateCustomerInfo(Customer customer,ShopCustomerInfo customerInfo){
		boolean falg = true;
		try {
			Customer newCustomer = customerDao.getObjectById(String.valueOf(customer.getCustomerId()));
			ShopCustomerInfo newCustomerInfo = shopCustomerInfoDao.getObjectById(String.valueOf(customerInfo.getShopCustomerInfoId()));
			//会员邮箱
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setPhone(customer.getPhone());
			//会员信息真实姓名
			newCustomerInfo.setTrueName(customerInfo.getTrueName());
			newCustomerInfo.setSex(customerInfo.getSex());
			newCustomerInfo.setRegionLocation(customerInfo.getRegionLocation());
			newCustomerInfo.setAddress(customerInfo.getAddress());
			newCustomerInfo.setTelephone(customerInfo.getTelephone());
			newCustomerInfo.setQq(customerInfo.getQq());
			newCustomer = customerDao.saveOrUpdateObject(newCustomer);
			newCustomerInfo = shopCustomerInfoDao.saveOrUpdateObject(newCustomerInfo);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			falg = false;
		}
		return falg;
	}
}
