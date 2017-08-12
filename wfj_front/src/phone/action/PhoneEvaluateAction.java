package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerService;
import shop.customer.service.IEvaluateGoodsService;
import shop.front.customer.service.IAccountCommentService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;

/**
 * 商品评价action
 * @author Norberta
 *
 */
@Controller
@SuppressWarnings({ "unused", "serial" })
public class PhoneEvaluateAction extends BaseAction {
	private static final Object String = null;

	@Resource
	private IEvaluateGoodsService evaluateGoodsService;

	@Resource
	private IAccountCommentService accountCommentService;

	@Resource
	private IOrdersService ordersService;

	@Resource
	private ICustomerService customerService;

	@Resource
	private IOrdersListService ordersListService;

	private String evaluateGoods;

	private String customerId;

	/**
	 * Android
	 * 
	 * @throws IOException
	 */
	public void evaluateGood() throws IOException {
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		// Android
		JSONObject jsonObject = JSONObject.fromObject(evaluateGoods);
		customerId = jsonObject.get("customerId").toString();
		String ordersId = jsonObject.get("ordersId").toString();
		int ecount = evaluateGoodsService.getCount(" where ordersId = "
				+ ordersId);
		if (ecount > 0) {
			jo.accumulate("Status", false);
			pw.print(jo);
			pw.flush();
			pw.close();
			return;
		}
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		Sonaccount son = (Sonaccount) session.getAttribute("sonaccount");
		ShopInfo si = (ShopInfo) session.getAttribute("shopInfo");
		Orders order = (Orders) ordersService.getObjectById(ordersId);
		evaluateGoods = jsonObject.get("evaluateGoods").toString();
		JSONArray js = JSONArray.fromObject(evaluateGoods);
		for (Object object : js) {
			String s = object.toString();
			JSONObject jt = JSONObject.fromObject(object);
			String content = jt.get("content").toString();
			Integer level = (Integer) jt.get("level");
			String orderDetailId = jt.get("orderDetailId").toString();
			OrdersList ordersList = (OrdersList) ordersListService
					.getObjectById(orderDetailId);
			JSONArray array = JSONArray.fromObject(jt);
			jsonObject.accumulate("isa", 1);
			jsonObject.accumulate("productId", ordersList.getProductId());
			jsonObject.accumulate("ordersId", order.getOrdersId());
			jsonObject.accumulate("ordersNo", order.getOrdersNo());
			accountCommentService.saveComment(order, array, customer,
					servletContext, son, si);
		}
		order.setOrdersState(9);
		ordersService.saveOrUpdateObject(order);
		jo.accumulate("Status", true);
		pw.println(jo);
		pw.flush();
		pw.close();
	}

	/**
	 * IOS
	 * 
	 * @throws IOException
	 */
	public void evaluateGoods() throws IOException {
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");

		// IOS

		evaluateGoods = evaluateGoods.replaceAll("=", ":");
		evaluateGoods = evaluateGoods.replace("(", "[");
		evaluateGoods = evaluateGoods.replace(")", "]");
		String ordersId = evaluateGoods.substring(
				evaluateGoods.indexOf('{') + 1, evaluateGoods.indexOf(':'))
				.trim();
		int ecount = evaluateGoodsService.getCount(" where ordersId = "
				+ ordersId);
		if (ecount > 0) {
			jo.accumulate("Status", false);
			pw.print(jo);
			pw.flush();
			pw.close();
			return;
		}
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		Sonaccount son = (Sonaccount) session.getAttribute("sonaccount");
		ShopInfo si = (ShopInfo) session.getAttribute("shopInfo");
		Orders order = (Orders) ordersService.getObjectById(ordersId);
		JSONObject eGoods = JSONObject.fromObject(evaluateGoods);
		JSONArray jsonArray = JSONArray.fromObject(eGoods.get(ordersId));
		for (Object object : jsonArray) {
			String string = object.toString();
			JSONObject jsonObject = JSONObject.fromObject(object);
			String content = (String) jsonObject.get("content");
			Integer level = (Integer) jsonObject.get("level");
			String orderDetailId = jsonObject.get("orderDetailId").toString();
			OrdersList ordersList = (OrdersList) ordersListService
					.getObjectById(orderDetailId);
			jsonObject.accumulate("isa", 1);
			jsonObject.accumulate("productId", ordersList.getProductId());
			jsonObject.accumulate("ordersId", order.getOrdersId());
			jsonObject.accumulate("ordersNo", order.getOrdersNo());
			JSONArray array = JSONArray.fromObject(jsonObject);
			accountCommentService.saveComment(order, array, customer,
					servletContext, son, si);
		}
		order.setOrdersState(9);
		ordersService.saveOrUpdateObject(order);
		jo.accumulate("Status", true);
		pw.println(jo);
		pw.flush();
		pw.close();
	}

	public void setEvaluateGoodsService(
			IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}

	public void setEvaluateGoods(String evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setAccountCommentService(
			IAccountCommentService accountCommentService) {
		this.accountCommentService = accountCommentService;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}

}
