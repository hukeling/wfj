package shop.front.customer.service;
import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import shop.customer.pojo.Customer;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.pojo.Sonaccount;
import shop.order.pojo.Orders;
import shop.store.pojo.ShopInfo;
import util.service.IBaseService;
/**
 * IAccountCommentService - 前台用户商品评价Service描述信息
 */
public interface IAccountCommentService extends IBaseService<EvaluateGoods> {
	/**
	 * 添加评价信息
	 * @param order 订单信息
	 * @param array 评价信息
	 * @param customer 用户
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Boolean saveComment(Orders order,JSONArray array,Customer customer,ServletContext servletContext,Sonaccount son,ShopInfo si);
}
