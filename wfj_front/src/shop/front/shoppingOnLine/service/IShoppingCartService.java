package shop.front.shoppingOnLine.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import shop.customer.pojo.Customer;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import util.service.IBaseService;
/**
 * IShoppingCartService - 类描述信息
 */
public interface IShoppingCartService extends IBaseService<ShoppingCart> {
	/**
  	 * 添加或者修改购物车数据
  	 * @param productId商品Id
  	 * @param num数量
  	 * @param buyType购买方式
  	 * @param cookies
  	 * @param customer操作用户
  	 * @return
  	 */
  	public boolean saveOrUpdateCookieCar(String cookieValue,Customer customer);
  	/**
  	 * 实际保存商品添加到购物车之后
  	 * @param productId
  	 * @param num
  	 * @param buyType
  	 * @param customer
  	 */
  	public boolean saveOrUpdateCar(Integer productId,String num,String shopInfoId,Customer customer,String day,String sku);
  	/**
  	 * 批量添加到购物车
  	 * @param productData
  	 */
  	public boolean saveOrUpdateShopCar(Customer customer,String productData);
  	/**
  	 * 根据sku批量添加到购物车
  	 * @param productData
  	 */
  	public boolean saveOrUpdateShopCarBySku(Customer customer,String productData);
  	/**
  	 * 查看购物车信息
  	 * @param customer
  	 * @param cookies
  	 * @return
  	 */
	public Map<Object,Object> gotoShoppingCart(Customer customer,Cookie[] cookies,Map cartCountMap);
	//去重方法
	public List<ShoppingCart> findUnSame(String field,String where);
}
