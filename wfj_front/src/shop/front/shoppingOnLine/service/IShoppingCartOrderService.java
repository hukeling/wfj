package shop.front.shoppingOnLine.service;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.Sonaccount;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.order.pojo.Orders;
import shop.product.pojo.ProductInfo;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;
/**
 * IShoppingCartOrderService - 类描述信息
 */
public interface IShoppingCartOrderService extends IBaseService<ShoppingCart> {
	/**
	 * 根据条件查询用户下的店铺id
	 * @param column 去重字段
	 * @param whereCondition  条件
	 * @return
	 */
	public List<ShoppingCart> findShoppingCartIds(String column,String whereCondition);
	/**
	 * 生成订单及保存相关表和清空cookie中相关数据
	 * @param orderJson订单的相关信息
	 * @param shoppingCartIds购物车的ids
	 * @return
	 */
	public Map<String,Object>  saveOrUpdateOrders(Orders orders,String shoppingCartIds,Customer customer,CustomerAcceptAddress custAddress,String stockUpDate,Sonaccount son,ShopInfo shopInfo,ServletContext servletContext);
	/**
	 * 直接购买--------生成订单及保存相关表和清空cookie中相关数据
	 * @param orderJson订单的相关信息
	 * @param shoppingCartIds购物车的ids
	 * @return
	 */
	public Map<String,Object>  saveOrUpdateOrdersBuy(Orders order,Customer customer,CustomerAcceptAddress custAddress,Integer productId,Integer quantity,Sonaccount son,ShopInfo shopInfo);
	/**
	 * 支付成功后修改状态和产品的销量
	 * @param ordersNo
	 */
	public void saveOrUpdateToPaySuccess(String ordersNo,Customer customer2,ShopInfo shopInfo2,Sonaccount son,String dealId);
	/**
	 * 根据商品id条件查询店铺id
	 * @param column 去重字段
	 * @param whereCondition  条件
	 * @return
	 */
	public List<ProductInfo> findShopInfoIdsByProduct(String column,String whereCondition);
	
	/**
	 * 支付成功后修改状态和产品的销量,收支明细
	 * @param ordersNo
	 */
	public void saveOrUpdateToPaySuccess(String tradeNo,String ordersNo);
	
}
