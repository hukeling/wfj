package shop.customer.service;
import shop.customer.pojo.Customer;
import util.service.IBaseService;
/**
 * 客户信息Service接口
 * @author ss
 *
 */
public interface ICustomerService extends IBaseService <Customer> {
	public boolean saveOrUpdateShengJiWoDeShenFen(Customer customer);
	/**
	 * 注册用户
	 * @return
	 */
	public Customer saveCustomer(Customer customer); 
	/**
	 * 通过sql语句修改单个对象或者批量对象
	 */
	public boolean updateObject(String sql);
	/**
	 * 注册
	 * @param customer 用户对象
	 * @param trueName  真实姓名
	 * @param cardNo  身份证号
	 * @param ip  
	 */
	public Customer  saveRegister(Customer customer,String trueName,String cardNo,String ip,String registerType);
	/**
	 * 注册用户--手机
	 */
	public Customer saveCustomerByPhone(Customer customer) ;
}
