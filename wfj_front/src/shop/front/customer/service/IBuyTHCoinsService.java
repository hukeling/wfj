package shop.front.customer.service;
import javax.servlet.ServletContext;

import shop.customer.pojo.Customer;
/**
 * 购买天海币service接口类
 * @author mf
 *
 */
public interface IBuyTHCoinsService {
	/**
	 * 后买天海币后执行操作
	 * 1.插入收支明细记录
	 * 2.插入天海比明细记录
	 * @author mf
	 */
	public void saveBuyInfo(Customer customer,Integer bd,Integer proportione,ServletContext servletContext);
}
