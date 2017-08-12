package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import phone.pojo.OrdersListSub;
import phone.pojo.OrdersSub;
import phone.pojo.RecentOrders;
import phone.util.JsonIgnore;
import phone.util.PhoneStaticConstants;
import rating.buyersRecord.service.IBuyersRecordService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.IMallCoinService;
import shop.customer.service.IVirtualCoinService;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.lineofcreditItem.service.ILineofcreditItemService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.service.IReturnsApplyService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Escape;
import util.other.Utils;
import basic.service.IAreaService;
import discountcoupon.service.ICustomerdiscountcouponService;

@SuppressWarnings("all")
@Controller
public class PhoneOrderAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/** shoppingCartOrderService **/
	private IShoppingCartOrderService shoppingCartOrderService;

	@Resource
	private IOrdersListService ordersListService;
	/** 客户收货地址Service **/
	private ICustomerAcceptAddressService customerAcceptAddressService;
	/** 订单日志 **/
	@SuppressWarnings("unused")
	private IOrdersOPLogService ordersOPLogService;  
	/** 虚拟金币service **/
	private IVirtualCoinService virtualCoinService;
	private IMallCoinService mallCoinService;
	/** 购物车Service **/
	private IShoppingCartService shoppingCartService;
	private ShoppingCart shoppingCart;
	/** 订单信息 **/
	@SuppressWarnings("rawtypes")
	private List<Map> cartMap;
	/** shoppingIds **/
	private String ids;
	/** 收货地址 **/
	private CustomerAcceptAddress custAddress;
	/** 购物车Ids **/
	private String shopCartIds;
	/** 提交订单json格式存放 **/
	private String orderJson;
	/** 折扣钱数 **/
	private String discount;
	/** 订单号 **/
	private String ordersNo;
	private IOrdersService ordersService;
	/** 用户地址List **/
	private List<CustomerAcceptAddress> customerAcceptAddressList;
	/** 订单 **/
	private Orders orders = new Orders();
	/** 商品Id直接购买，不经过购物车 **/
	private Integer productId;
	/** 店铺Id直接购买，不经过购物车 **/
	private String shopInfoId;
	/** 去支付时的信息 **/
	@SuppressWarnings({ "unused", "rawtypes" })
	private List<Map> toPayList;
	/** 直接购买商品的数量 **/
	private Integer productNum;
	private Double salesPrice;
	/** 获取国家信息 **/
	@SuppressWarnings("rawtypes")
	private List<Map> countrys; // 国家
	/** 支付宝参数map **/
	private Map<String, Object> payMap = new HashMap<String, Object>();
	/** 区域service **/
	private IAreaService areaService;
	/** 一级地址 **/
	@SuppressWarnings("rawtypes")
	private List<Map> firstAreaList;
	/** 安全认证pojo **/
	private SafetyCertificate safetyCertificate;
	/** 按照不同的店铺拆分显示 **/
	private Map<Object, Object> shopInfoMap;
	/** 用户虚拟金币余额 **/
	private BigDecimal coinTotal;
	/** 记录是否所有商品均满足交易 **/
	private String isAllTransactionFlag;
	/** 是否开发票 **/
	private Integer isInvoice;
	/** 会员可用授信额度值 **/
	private String lineOfCredit;
	/** 会员总授信额度值 **/
	private String lineOfCreditTal;
	/** 会员授信额度使用明细表service **/
	private ILineofcreditItemService lineofcreditItemService;
	/** 会员等级升迁记录表service **/
	private IBuyersRecordService buyersRecordService;
	/** 输入的额度数量 **/
	private String number;
	/** 商品成交价格 **/
	private BigDecimal buyPrice;
	/** buyPrice是否为会员价 **/
	private String buyPriceCustomer = "0";
	/** 总订单号 */
	private String totalOrdersNo;
	/** 子订单号字符串组 */
	private String ordersNoAll;
	/** 支付宝显示的商品描述 **/
	private String subject = "您从SHOPJSP购买商品";
	/** 增值税发票抬头 */
	private String shopInfoCompanyName;

	private String addressisok;// 配送方式
	private String customerDiscountCouponID;// 优惠券id
	private ICustomerdiscountcouponService customerdiscountcouponService;// 用户优惠卷service
	private List<Map> ordersListMap;// 优惠券集合

	@Resource
	private IShopInfoService shopInfoService;

	private IReturnsApplyService returnsApplyService;// 退换申请Service

	private String customerId;// 用户id
	private int ordType;// 订单类型1待付款2待发货4待收货5待评价7退换货的
	private int ordersId;// 订单id
	private int ordersState;// 订单状态

	/**
	 * https://192.168.10.210:8443/wfj_front/phone/deleteOrders.action?ordersId=
	 * 840 根据订单id(ordersId)删除订单
	 * 
	 * @throws IOException
	 */
	public void deleteOrder() throws IOException {
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		boolean flag = ordersService.deleteObjectById(String.valueOf(ordersId));
		if (flag == true) {
			jo.accumulate("Status", true);
			jo.accumulate("Data", "删除订单成功！");
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "删除订单失败！");
		}
		out.write(jo.toString());
		System.out.println(jo.toString());
		out.flush();
		out.close();
	}

	/**
	 * https://192.168.10.210:8443/wfj_front/phone/payOrder.action?ordersNo=
	 * 根据订单编号(ordersNo)，订单状态改为已支付
	 * 
	 * @throws IOException
	 */
	public void payOrder() throws IOException {
		int oldState = PhoneStaticConstants.osUntreatedOrders;//1
		int newState = PhoneStaticConstants.osPayedOrders;//2
		String wherehql = "where o.ordersNo=" + ordersNo;
		String msg = "支付";
		changeOrdersState(oldState, newState, wherehql, msg);
	}

	/**
	 * 根据订单id(ordersNo)确认收货
	 * https://192.168.10.210:8443/wfj_front/phone/confirmGoods
	 * .action?ordersId=981
	 * 
	 * @throws IOException
	 */
	public void confirmGoods() throws IOException {
		int oldState = PhoneStaticConstants.osshippedOrders;//4
		int newState = PhoneStaticConstants.osGetgoodsOrders;//5
		String wherehql = "where o.ordersId=" + ordersId;
		String msg = "确认收货";
		changeOrdersState(oldState, newState, wherehql, msg);
	}

	/**
	 * 更新订单状态方法
	 * 
	 * @param oldState
	 *            :当前订单状态值
	 * @param newState
	 *            ：希望更改成的状态值
	 * @param wherehql
	 *            ：查询语句
	 * @param msg
	 *            ：更改完后信息提示
	 * @throws IOException
	 */
	public void changeOrdersState(int oldState, int newState, String wherehql,
			String msg) throws IOException {
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		Orders or = (Orders) ordersService.getObjectByParams(wherehql);
		if (or != null && or.getOrdersState() == oldState) {
			or.setOrdersState(newState);
			or = (Orders) ordersService.saveOrUpdateObject(or);
			if (or != null) {
				jo.accumulate("Status", true);
				jo.accumulate("Data", msg + "成功");
			} else {
				jo.accumulate("Status", false);
				jo.accumulate("Data", msg + "失败");
			}
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", msg + "失败");
		}
		out.write(jo.toString());
		System.out.println(jo.toString());
		out.flush();
		out.close();
	}

	/**
	 * 取各类状态的订单
	 * 
	 * @throws IOException
	 */
	/*
	 * public void getSimpleOrders() throws IOException { JSONObject jo = new
	 * JSONObject(); PrintWriter pw = response.getWriter();
	 * response.setContentType("text/html;charset=UTF-8"); String hql = ""; if
	 * (ordType == 0) { ordType = 0; } else if (ordType == 3) { ordType=2; }
	 * List<OrdersWithList> lo=new ArrayList(); List<Orders> orders =
	 * ordersService
	 * .findObjects(" where ordType="+ordType+" and customerId="+customerId);
	 * if(orders.size()>0){ for(int i=0;i<orders.size();i++){
	 * lo.get(i).setAddress(orders.get(i).getAddress());
	 * 
	 * } } JSONObject object = jo.fromObject(lo); jo.accumulate("1", object);
	 * pw.print(jo); pw.flush(); pw.close(); }
	 */

	/**
	 * https://192.168.10.210:8443/wfj_front/phone/searchOrdersbyOrdersNo.action
	 * ?ordersNo=2015122314181430 根据订单号（ordersNo）查询订单详情
	 * 
	 * @throws IOException
	 */
	public void searchOrdersByOrdersNo() throws IOException {
		String wherehql1 = "where o.ordersNo=" + ordersNo;
		this.searchOrders(wherehql1);
	}
	
	/**
	 * https://192.168.10.210:8443/wfj_front/phone/searchOrdersbyTotalOrdersNo.action?totalOrdersNo=2016010617142074
	 * 根据totalOrdersNo查询订单详情
	 * @throws IOException 
	 */
	public void searchOrdersByTotalOrdersNo() throws IOException{
		String wherehql1="where o.totalOrdersNo="+totalOrdersNo;
		this.searchOrders(wherehql1);	
	}


	/**
	 * https://192.168.10.210:8443/wfj_front/phone/searchAllOrders.action?
	 * customerId=459&ordType=1 根据用户id(customerId)和订单类型(ordType)查询用户订单信息
	 * 
	 * @throws IOException
	 */
	public void searchAllOrders() throws IOException {
		String wherehql1 = "";
		if (ordType == PhoneStaticConstants.toSendGoods) {
			wherehql1 = "where o.customerId="
					+ customerId
					+ " and ordersState=2 or ordersState=3 order by ordersState asc, createTime desc";
		} else if (ordType ==PhoneStaticConstants.toReceiveGoods) {
			wherehql1 = "where o.customerId="
					+ customerId
					+ " and ordersState=2 or ordersState=3 or ordersState=4 order by ordersState asc, createTime desc";
		} else if (ordType ==PhoneStaticConstants.toPay || ordType == PhoneStaticConstants.toEvaluate) {
			wherehql1 = "where o.customerId=" + customerId
					+ " and ordersState=" + ordType
					+ " order by ordersState asc, createTime desc";
		} else if (ordType ==PhoneStaticConstants.backOrChangeGoods) {
			wherehql1 = "where o.customerId=" + customerId;
			List<ReturnsApply> returnList = returnsApplyService
					.findObjects(wherehql1);// 根据用户id获取用户所有退货申请记录
			List<OrdersSub> osList = new ArrayList<OrdersSub>();// 最后输出的Data中的数据
			for (ReturnsApply ra : returnList) {
				ordersNo = ra.getOrdersNo();
				productId = ra.getProductId();
				String wherehql3 = "where o.ordersNo=" + ordersNo;
				Orders order = (Orders) ordersService
						.getObjectByParams(wherehql3);// 根据用户退货申请记录中的ordersNo获取订单详情信息
				OrdersSub ow = new OrdersSub();
				ow.setCustomerId(order.getCustomerId());
				ow.setConsignee(order.getConsignee());// 收货人姓名
				ow.setMobilePhone(order.getMobilePhone());// 收货人电话
				ow.setAddress(order.getAddress());// 收货人详细地址
				ow.setOrdersId(order.getOrdersId());// 订单id
				ow.setOrdersNo(order.getOrdersNo());// 订单编号
				ow.setTotalOrdersNo(order.getTotalOrdersNo());// 总订单号
				ow.setOrdersState(order.getOrdersState());// 订单状态：1、未处理(生成订单)；2、已付款；3、正在配货；4、已发货；5、已收货；6、订单取消；7、异常订单(退换货等、问题订单)；9、已评价；
				ow.setCreatTime(order.getCreateTime().toString());// 订单生成时间
				ow.setFinalAmount(order.getFinalAmount());// 最终支付的总金额
				ow.setFreight(order.getFreight());// 运费
				ow.setSendType(order.getSendType());// 配送方式：1快递公司2同城快递3线下自提
				ow.setPayMode(order.getPayMode());// 支付方式：1货到付款2支付宝3银行汇款4网银支付
				ow.setInvoiceType(order.getInvoiceType());// 发票类型：1：不开发票;2：普通发票;3：增值税发票
				ow.setInvoiceInfo(order.getInvoiceInfo());// 发票内容
				int shopInfoId = order.getShopInfoId();
				ShopInfo shopInfo = (ShopInfo) shopInfoService
						.getObjectById(String.valueOf(shopInfoId));
				ow.setShopPhone(shopInfo.getPhone());// 商铺联系电话
				String orderNo = order.getOrdersNo();
				String wherehql2 = "where o.ordersNo='" + orderNo + "'"
						+ " and productId=" + productId
						+ "order by createTime desc";
				OrdersList ol = (OrdersList) ordersListService
						.getObjectByParams(wherehql2);
				OrdersListSub ols = new OrdersListSub();// 重写OrdersList信息
				ols.setCount(ol.getCount());
				ols.setLogoImage(ol.getLogoImage());
				ols.setOrderDetailId(ol.getOrderDetailId());
				ols.setOrdersId(ol.getOrdersId());
				ols.setOrdersNo(ol.getOrdersNo());
				ols.setProductFullName(ol.getProductFullName());
				ols.setProductId(ol.getProductId());
				ols.setSalesPrice(ol.getSalesPrice());
				ols.setShopInfoId(ol.getShopInfoId());
				ols.setShopName(ol.getShopName());
				ols.setOrdersState(7);// 订单明细的订单状态设置为7
				List<OrdersListSub> olsList = new ArrayList<OrdersListSub>();
				olsList.add(ols);
				// 如果订单明细list中有值
				if (olsList.size() > 0) {
					JSONArray joarray = JsonIgnore.jsonIgnore(olsList,
							JsonIgnore.getIgnoreOrdersList());
					ow.setList(joarray);// 将订单明细放入订单中
					osList.add(ow);// 将订单信息放入订单list中
				}
			}
			JSONArray ja = JsonIgnore.jsonIgnore(osList,
					JsonIgnore.getIgnoreOrdersWithList());
			JSONObject jo = new JSONObject();
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			if (osList != null && osList.size() > 0) {
				jo.accumulate("Status", true);
				jo.accumulate("Data", ja);
			} else {
				jo.accumulate("Status", false);
				jo.accumulate("Data", "查询无结果");
			}
			out.write(jo.toString());
			out.flush();
			out.close();
		} else {
			wherehql1 = "where o.customerId=" + customerId
					+ "order by ordersState asc,createTime desc ";
		}
		this.searchOrders(wherehql1);
	}
	 
	/**
	 * 查询订单详情
	 * 
	 * @param wherehql1
	 *            :查询语句
	 * @throws IOException
	 */
	public void searchOrders(String wherehql1) throws IOException {
		List<Orders> orderList = ordersService.findObjects(wherehql1);
		List<OrdersSub> osList = new ArrayList<OrdersSub>();
		for (Orders order : orderList) {
			OrdersSub os = new OrdersSub();
			os.setCustomerId(order.getCustomerId());
			os.setConsignee(order.getConsignee());// 收货人姓名
			os.setMobilePhone(order.getMobilePhone());// 收货人电话
			os.setAddress(order.getAddress());// 收货人详细地址
			os.setOrdersId(order.getOrdersId());// 订单id
			os.setOrdersNo(order.getOrdersNo());// 订单编号
			os.setTotalOrdersNo(order.getTotalOrdersNo());// 总订单号
			os.setOrdersState(order.getOrdersState());// 订单状态：1、未处理(生成订单)；2、已付款；3、正在配货；4、已发货；5、已收货；6、订单取消；7、异常订单(退换货等、问题订单)；9、已评价；
			os.setCreatTime(order.getCreateTime().toString());// 订单生成时间
			os.setFinalAmount(order.getFinalAmount());// 最终支付的总金额
			os.setFreight(order.getFreight());// 运费
			os.setSendType(order.getSendType());// 配送方式：1快递公司2同城快递3线下自提
			os.setPayMode(order.getPayMode());// 支付方式：1货到付款2支付宝3银行汇款4网银支付
			os.setInvoiceType(order.getInvoiceType());// 发票类型：1：不开发票;2：普通发票;3：增值税发票
			os.setInvoiceInfo(order.getInvoiceInfo());// 发票内容
			int shopInfoId = order.getShopInfoId();
			ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectById(String
					.valueOf(shopInfoId));
			os.setShopPhone(shopInfo.getPhone());// 商铺联系电话
			String orderNo = order.getOrdersNo();
			String wherehql2 = "where o.ordersNo='" + orderNo + "'"
					+ "order by createTime desc";
			List<OrdersList> list = ordersListService.findObjects(wherehql2);
			List<OrdersListSub> olsList = new ArrayList<OrdersListSub>();
			for (OrdersList ol : list) {
				OrdersListSub olw = new OrdersListSub();
				olw.setCount(ol.getCount());
				olw.setLogoImage(ol.getLogoImage());
				olw.setOrderDetailId(ol.getOrderDetailId());
				olw.setOrdersId(ol.getOrdersId());
				olw.setOrdersNo(ol.getOrdersNo());
				olw.setProductFullName(ol.getProductFullName());
				olw.setProductId(ol.getProductId());
				olw.setSalesPrice(ol.getSalesPrice());
				olw.setShopInfoId(ol.getShopInfoId());
				olw.setShopName(ol.getShopName());

				// 判断该商品是否申请过退货，如未退过货（即无退货申请记录），就显示在订单中，如退过货，就将订单明细的订单状态设置为7
				String returnwherehql = "where o.ordersNo=" + olw.getOrdersNo()
						+ " and productId=" + olw.getProductId();
				ReturnsApply re = new ReturnsApply();
				re = (ReturnsApply) returnsApplyService
						.getObjectByParams(returnwherehql);
				if (re == null) {
					olw.setOrdersState(order.getOrdersState());
				} else {
					olw.setOrdersState(PhoneStaticConstants.osReturnorchangeOrders);
				}
				olsList.add(olw);
			}
			if (olsList.size() > 0) {
				JSONArray joarray = JsonIgnore.jsonIgnore(olsList,
						JsonIgnore.getIgnoreOrdersList());
				os.setList(joarray);// 将订单明细放入订单中
				osList.add(os);
			}
		}
		JSONArray ja = JsonIgnore.jsonIgnore(osList,
				JsonIgnore.getIgnoreOrdersWithList());
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		if (osList != null && osList.size() > 0) {
			jo.accumulate("Status", true);
			jo.accumulate("Data", ja);
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "查询无结果");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	}

	/**
	 * 确认订单提交到成功页面
	 * 
	 * @throws IOExceptions
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toPhoneSuccessOrder() throws IOException {
		Customer customer = null;
		if (customerId == null) {
			customer = (Customer) session.getAttribute("customer");
			customerId = customer.getCustomerId().toString();
		}
		ShopInfo shopInfo = (ShopInfo) shopInfoService
				.getObjectByParams(" where customerId=" + customerId);
		Sonaccount son = (Sonaccount) session.getAttribute("sonaccount");
		// 税率
		BigDecimal rates = null;
		// 发票信息
		if ("2".equals(orders.getInvoiceType().toString())) {// 普通发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setInvoiceInfo(orders.getInvoiceInfo());
			shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			String strgeneralRates = (String) fileUrlConfig.get("generalRates");
			rates = new BigDecimal(strgeneralRates);
		} else if ("3".equals(orders.getInvoiceType().toString())) {// 增值税发票
			shopInfo.setInvoiceType(orders.getInvoiceType());
			shopInfo.setTaxpayerNumber(orders.getTaxpayerNumber());
			shopInfo.setAddressForInvoice(orders.getAddressForInvoice());
			shopInfo.setPhoneForInvoice(orders.getPhoneForInvoice());
			shopInfo.setOpeningBank(orders.getOpeningBank());
			shopInfo.setBankAccountNumber(orders.getBankAccountNumber());
			// shopInfo.setCompanyName(orders.getCompanyNameForInvoice());
			if (Utils.objectIsNotEmpty(shopInfoCompanyName)) {
				shopInfo.setCompanyName(shopInfoCompanyName);
			}
			String vatRates = (String) fileUrlConfig.get("vatRates");
			rates = new BigDecimal(vatRates);
		} else {
			shopInfo.setInvoiceType(1);
		}
		shopInfo.setCustomerId(Integer.parseInt(customerId));
		shopInfo = (ShopInfo) shopInfoService.saveOrUpdateObject(shopInfo);
		session.setAttribute("shopInfo", shopInfo);
		// 查询之前商品的金额
		String hql = "";
		if (productId != null) {
			// 直接购买
			hql = "select c.marketPrice as marketPrice,b.shopInfoId as shopInfoId,"
					+ " c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice,d.price as price "
					+ " from ShopInfo b ,ProductInfo c,TuanProduct d"
					+ " where b.shopInfoId=c.shopInfoId and d.productId=c.productId and c.productId ="
					+ productId + " and  b.shopInfoId=" + shopInfoId;
		} else {
			// 购物车信息
			hql = "select d.brandId as brandId, a.quantity as quantity,a.discount as discount, c.marketPrice as marketPrice, a.shopCartId as shopCartId, a.stockUpDate as stockUpDate, b.shopInfoId as shopInfoId,b.minAmount as minAmount,b.postage as postage,c.productId as productId ,"
					+ " c.describle as describle,c.productName as productName,c.salesPrice as salesPrice,c.isChargeFreight as isChargeFreight ,c.freightPrice as freightPrice, c.storeNumber as storeNumber, c.productFullName as productFullName,"
					+ " b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale,c.stockUpDate as stockUpDate,c.sku as sku,"
					+ " b.shopName as shopName,b.customerName as customerName ,c.logoImg as smallImgUrl  from ShoppingCart a ,ShopInfo b ,ProductInfo c,Brand d"
					+ " where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId  and a.shopCartId in("
					+ shopCartIds + ") and a.customerId=" + customerId;
		}
		cartMap = shoppingCartOrderService.findListMapByHql(hql);
		BigDecimal totalPrice = new BigDecimal(0);// 商品总价格
		BigDecimal freightPrice = new BigDecimal(0);// 运费总价格
		BigDecimal finalAmount = new BigDecimal(0);// 支付总价格
		BigDecimal totalMarketPrice = new BigDecimal(0);// 市场价总价格
		BigDecimal num = new BigDecimal(0);// 商品数量
		shopInfoMap = new HashMap<Object, Object>();// 存放店铺集合
		List<Map> list = new ArrayList<Map>();// 一个list表示一个店铺
		for (Map map : cartMap) {
			// buyPrice=new BigDecimal(0);
			BigDecimal marketPrice = new BigDecimal(String.valueOf(map
					.get("marketPrice")));// 市场价
			BigDecimal salesPrice = new BigDecimal(String.valueOf(map
					.get("salesPrice")));// 销售价
			// getCustomerProductPrice(customer,marketPrice,salesPrice);
			// BigDecimal price=buyPrice;//成交价
			if (StringUtils.isEmpty(shopCartIds) && productNum != null) {
				num = new BigDecimal(productNum);
			} else {
				num = new BigDecimal(String.valueOf(map.get("quantity")));
			}
			map.put("productNum", num);
			// map.put("subTotal",price.multiply(num));
			// totalPrice=totalPrice.add(price.multiply(num));
			// map.put("subTotal",salesPrice.multiply(num));
			// totalPrice=totalPrice.add(salesPrice.multiply(num));
			// totalMarketPrice =
			// totalMarketPrice.add(marketPrice.multiply(num));
			list = new ArrayList<Map>();
			if (shopInfoMap.get(map.get("shopInfoId")) == null) {// 如果店铺集合中没有当前店铺则添加
				BigDecimal quantity = new BigDecimal(String.valueOf(map
						.get("quantity")));// 购买数量
				BigDecimal price = new BigDecimal(String.valueOf(map
						.get("salesPrice")));// 商品成交价
				BigDecimal minAmount = new BigDecimal(String.valueOf(map
						.get("minAmount")));
				BigDecimal shopInfoTotalPrice = new BigDecimal(0);
				// 总消费金额
				shopInfoTotalPrice = price.multiply(quantity);
				map.put("totalPrice", shopInfoTotalPrice.setScale(2,
						BigDecimal.ROUND_HALF_UP));
				if (shopInfoTotalPrice.compareTo(minAmount) > 0) {
					map.put("postage", 0);
				}
				list.add(map);
				// 保存店铺list（list为店铺中商品的集合）
				shopInfoMap.put(map.get("shopInfoId"), list);
			} else {// 如果在shopInfoMap中有当前店铺刚取出店铺
				list = (List<Map>) shopInfoMap.get(map.get("shopInfoId"));
				// 最底消费金额
				BigDecimal minAmount = new BigDecimal(String.valueOf(map
						.get("minAmount")));
				// 满足则包邮
				minAmount = minAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				// 商品成交价
				BigDecimal price = new BigDecimal(String.valueOf(map
						.get("salesPrice")));
				// 购买数量
				BigDecimal quantity = new BigDecimal(String.valueOf(map
						.get("quantity")));
				Map oneMap = list.get(0);
				BigDecimal shopInfoTotalPrice = (BigDecimal) oneMap
						.get("totalPrice");
				if (Utils.objectIsNotEmpty(shopInfoTotalPrice)) {
					// 总消费金额
					shopInfoTotalPrice = shopInfoTotalPrice.add(price
							.multiply(quantity));
				} else {
					shopInfoTotalPrice = price.multiply(quantity);
				}
				map.put("totalPrice", shopInfoTotalPrice.setScale(2,
						BigDecimal.ROUND_HALF_UP));
				List<Map> tempList = new ArrayList<Map>();
				for (Map m : list) {
					if (shopInfoTotalPrice.compareTo(minAmount) > 0) {
						m.put("postage", 0);
					}
					tempList.add(m);
				}
				if (shopInfoTotalPrice.compareTo(minAmount) > 0) {
					map.put("postage", 0);
				}
				tempList.add(map);
				// 保存店铺list（list为店铺中商品的集合）
				shopInfoMap.put(map.get("shopInfoId"), tempList);

				// 向店铺中添加商品
				// list.add(map);
			}

			// 取得折扣比例
			BigDecimal bdDiscount = (BigDecimal) map.get("discount");
			// 商品销售价*折扣比例*数量=消费总金额
			BigDecimal temTotalPrice = new BigDecimal(0);
			if (Utils.objectIsNotEmpty(bdDiscount)) {
				BigDecimal temp = new BigDecimal(0.1);
				// 消费总金额=商品销售价*折扣比例*数量
				temTotalPrice = salesPrice.multiply(num).multiply(bdDiscount)
						.multiply(temp);
				map.put("subTotal", temTotalPrice);
			} else {
				temTotalPrice = salesPrice.multiply(num);
				map.put("subTotal", temTotalPrice);
			}
			// 最终支付金额（折扣已打）商品销售价*折扣比例*数量
			finalAmount = finalAmount.add(temTotalPrice);
			// 商品总金额（折扣未计算）商品销售价*数量
			totalPrice = totalPrice.add(salesPrice.multiply(num));

			// if("2".equals(String.valueOf(map.get("isChargeFreight")))){
			// if(null!=map.get("freightPrice")){
			// freightPrice=freightPrice.add(new
			// BigDecimal(String.valueOf(map.get("freightPrice"))));
			// }
			// }
		}
		// finalAmount = totalPrice.add(freightPrice);//加上运费计算
		// finalAmount =
		// finalAmount.subtract(orders.getChangeAmount());//去掉信用额度抵扣
		Iterator<Object> keys = shopInfoMap.keySet().iterator();
		// 遍历得到运费总额
		// 定义一个店铺id字符串，用于校验当前商品是否存储在于同一店铺内
		String shopIds = ",";
		while (keys.hasNext()) {
			Integer key = (Integer) keys.next();
			List<Map> listMap = (List<Map>) shopInfoMap.get(key);
			Map m = listMap.get(0);
			// 获取店铺ID
			String shopInfoId = String.valueOf(m.get("shopInfoId"));
			// 判断店铺id是否被记录
			int index = shopIds.indexOf("," + shopInfoId + ",");
			if (index < 0) {// 没有被记录
				// 做记录
				shopIds += shopIds + ",";
				// 计入运费信息
				freightPrice = freightPrice.add(new BigDecimal(String.valueOf(m
						.get("postage"))));
			}
		}
		finalAmount = finalAmount.add(freightPrice);// 加上运费计算
		// finalAmount =
		// finalAmount.subtract(orders.getChangeAmount());//去掉金币抵扣值

		if (Utils.objectIsNotEmpty(rates)) {// 税率不为空
			finalAmount = finalAmount.add(rates.multiply(
					finalAmount.subtract(freightPrice)).multiply(
					new BigDecimal(0.01)));
		}
		finalAmount = finalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);// 保留两位小数
		// 页面取得应付金额
		orders.setFinalAmount(finalAmount);
		orders.setOrdersState(1);
		BigDecimal ordersFinalAmount = orders.getFinalAmount();
		ordersFinalAmount = ordersFinalAmount.setScale(2,
				BigDecimal.ROUND_HALF_UP);// 保留两位小数
		// finalAmount =
		// finalAmount.subtract(orders.getChangeAmount());//去掉金币抵扣值
		if (Utils.objectIsNotEmpty(orders.getOrderCouponAmount())) {
			finalAmount = finalAmount.subtract(orders.getOrderCouponAmount());// 去掉优惠券抵扣值
		}
		orders.setTotalAmount(totalPrice);// 商品总金额
		if (freightPrice.compareTo(orders.getFreight()) != 0) {// 运费金额比较
			ordersNo = null;
		} else if (finalAmount.compareTo(ordersFinalAmount) != 0) {// 应付总金额比较
			ordersNo = null;
		} else {
			ShoppingCart sc = (ShoppingCart) shoppingCartService
					.getObjectByParams("where o.customerId=" + customerId);
			orders.setOrdersState(1);
			if (StringUtils.isNotEmpty(shopCartIds)) {
				payMap = shoppingCartOrderService.saveOrUpdateOrders(orders,
						shopCartIds, customer, custAddress,
						sc.getStockUpDate(), son, shopInfo, servletContext);
			} else {
				payMap = shoppingCartOrderService.saveOrUpdateOrdersBuy(orders,
						customer, custAddress, productId, productNum, son,
						shopInfo);
			}
			payMap.put("subject", Escape.escape("您从商城购买商品"));
			ordersNo = String.valueOf(payMap.get("totalOrdersNo"));
			totalOrdersNo = String.valueOf(payMap.get("totalOrdersNo"));
			// safetyCertificate = getSafety(orders.getCustomerId());
			ordersNoAll = String.valueOf(payMap.get("ordersNoAll"));
		}
		List<RecentOrders> li = new ArrayList<RecentOrders>();

		List<Orders> orders = (List<Orders>) ordersService
				.findObjects(" where ordersNo in (" + payMap.get("ordersNoAll")
						+ ")");
		String ids = "";
		if (orders.size() > 1) {
			for (int i = 0; i < orders.size(); i++) {
				ids = orders.get(i).getOrdersId().toString();
				List<OrdersList> list2 = (List<OrdersList>) ordersListService
						.findObjects(" where ordersId in (" + ids + ")");
				RecentOrders ro = new RecentOrders();
				ro.setOrdersNo(orders.get(i).getOrdersNo());
				ro.setOrdersId(orders.get(i).getOrdersId().toString());
				ro.setFinalAmount(orders.get(i).getFinalAmount().toString());
				ro.setList(list2);
				li.add(ro);
			}
		} else {
			ids = orders.get(0).getOrdersId().toString();
			List<OrdersList> list2 = (List<OrdersList>) ordersListService
					.findObjects(" where ordersId in (" + ids + ")");
			RecentOrders ro = new RecentOrders();
			ro.setOrdersNo(orders.get(0).getOrdersNo());
			ro.setOrdersId(orders.get(0).getOrdersId().toString());
			ro.setFinalAmount(orders.get(0).getFinalAmount().toString());
			ro.setList(list2);
			li.add(ro);
		}
		String totalOrdersNo2 = orders.get(0).getTotalOrdersNo();
		request.setAttribute("totalOrdersNo", totalOrdersNo2);
		return SUCCESS;
		/*
		 * JSONObject jo = new JSONObject(); PrintWriter pw =
		 * response.getWriter();
		 * response.setContentType("text/html;charset=UTF-8");
		 * jo.accumulate("Status", true); jo.accumulate("Data", li);
		 * pw.println(jo); pw.flush(); pw.close();
		 */
	}

	public void test() throws IOException {
	}

	public void setShoppingCartOrderService(
			IShoppingCartOrderService shoppingCartOrderService) {
		this.shoppingCartOrderService = shoppingCartOrderService;
	}

	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}

	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}

	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}

	public void setMallCoinService(IMallCoinService mallCoinService) {
		this.mallCoinService = mallCoinService;
	}

	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public void setCartMap(List<Map> cartMap) {
		this.cartMap = cartMap;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setCustAddress(CustomerAcceptAddress custAddress) {
		this.custAddress = custAddress;
	}

	public void setShopCartIds(String shopCartIds) {
		this.shopCartIds = shopCartIds;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setCustomerAcceptAddressList(
			List<CustomerAcceptAddress> customerAcceptAddressList) {
		this.customerAcceptAddressList = customerAcceptAddressList;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}

	public void setToPayList(List<Map> toPayList) {
		this.toPayList = toPayList;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public void setCountrys(List<Map> countrys) {
		this.countrys = countrys;
	}

	public void setPayMap(Map<String, Object> payMap) {
		this.payMap = payMap;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}

	public void setSafetyCertificate(SafetyCertificate safetyCertificate) {
		this.safetyCertificate = safetyCertificate;
	}

	public void setShopInfoMap(Map<Object, Object> shopInfoMap) {
		this.shopInfoMap = shopInfoMap;
	}

	public void setCoinTotal(BigDecimal coinTotal) {
		this.coinTotal = coinTotal;
	}

	public void setIsAllTransactionFlag(String isAllTransactionFlag) {
		this.isAllTransactionFlag = isAllTransactionFlag;
	}

	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}

	public void setLineOfCredit(String lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public void setLineOfCreditTal(String lineOfCreditTal) {
		this.lineOfCreditTal = lineOfCreditTal;
	}

	public void setLineofcreditItemService(
			ILineofcreditItemService lineofcreditItemService) {
		this.lineofcreditItemService = lineofcreditItemService;
	}

	public void setBuyersRecordService(IBuyersRecordService buyersRecordService) {
		this.buyersRecordService = buyersRecordService;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}

	public void setTotalOrdersNo(String totalOrdersNo) {
		this.totalOrdersNo = totalOrdersNo;
	}

	public void setOrdersNoAll(String ordersNoAll) {
		this.ordersNoAll = ordersNoAll;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setShopInfoCompanyName(String shopInfoCompanyName) {
		this.shopInfoCompanyName = shopInfoCompanyName;
	}

	public void setAddressisok(String addressisok) {
		this.addressisok = addressisok;
	}

	public void setCustomerDiscountCouponID(String customerDiscountCouponID) {
		this.customerDiscountCouponID = customerDiscountCouponID;
	}

	public void setCustomerdiscountcouponService(
			ICustomerdiscountcouponService customerdiscountcouponService) {
		this.customerdiscountcouponService = customerdiscountcouponService;
	}

	public void setOrdersListMap(List<Map> ordersListMap) {
		this.ordersListMap = ordersListMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IShoppingCartOrderService getShoppingCartOrderService() {
		return shoppingCartOrderService;
	}

	public ICustomerAcceptAddressService getCustomerAcceptAddressService() {
		return customerAcceptAddressService;
	}

	public IOrdersOPLogService getOrdersOPLogService() {
		return ordersOPLogService;
	}

	public IVirtualCoinService getVirtualCoinService() {
		return virtualCoinService;
	}

	public IMallCoinService getMallCoinService() {
		return mallCoinService;
	}

	public IShoppingCartService getShoppingCartService() {
		return shoppingCartService;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public List<Map> getCartMap() {
		return cartMap;
	}

	public String getIds() {
		return ids;
	}

	public CustomerAcceptAddress getCustAddress() {
		return custAddress;
	}

	public String getShopCartIds() {
		return shopCartIds;
	}

	public String getOrderJson() {
		return orderJson;
	}

	public String getDiscount() {
		return discount;
	}

	public String getOrdersNo() {
		return ordersNo;
	}

	public IOrdersService getOrdersService() {
		return ordersService;
	}

	public List<CustomerAcceptAddress> getCustomerAcceptAddressList() {
		return customerAcceptAddressList;
	}

	public Orders getOrders() {
		return orders;
	}

	public Integer getProductId() {
		return productId;
	}

	public String getShopInfoId() {
		return shopInfoId;
	}

	public List<Map> getToPayList() {
		return toPayList;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public List<Map> getCountrys() {
		return countrys;
	}

	public Map<String, Object> getPayMap() {
		return payMap;
	}

	public IAreaService getAreaService() {
		return areaService;
	}

	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}

	public SafetyCertificate getSafetyCertificate() {
		return safetyCertificate;
	}

	public Map<Object, Object> getShopInfoMap() {
		return shopInfoMap;
	}

	public BigDecimal getCoinTotal() {
		return coinTotal;
	}

	public String getIsAllTransactionFlag() {
		return isAllTransactionFlag;
	}

	public Integer getIsInvoice() {
		return isInvoice;
	}

	public String getLineOfCredit() {
		return lineOfCredit;
	}

	public String getLineOfCreditTal() {
		return lineOfCreditTal;
	}

	public ILineofcreditItemService getLineofcreditItemService() {
		return lineofcreditItemService;
	}

	public IBuyersRecordService getBuyersRecordService() {
		return buyersRecordService;
	}

	public String getNumber() {
		return number;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public String getBuyPriceCustomer() {
		return buyPriceCustomer;
	}

	public String getTotalOrdersNo() {
		return totalOrdersNo;
	}

	public String getOrdersNoAll() {
		return ordersNoAll;
	}

	public String getSubject() {
		return subject;
	}

	public String getShopInfoCompanyName() {
		return shopInfoCompanyName;
	}

	public String getAddressisok() {
		return addressisok;
	}

	public String getCustomerDiscountCouponID() {
		return customerDiscountCouponID;
	}

	public ICustomerdiscountcouponService getCustomerdiscountcouponService() {
		return customerdiscountcouponService;
	}

	public List<Map> getOrdersListMap() {
		return ordersListMap;
	}

	public IShopInfoService getShopInfoService() {
		return shopInfoService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setOrdType(int ordType) {
		this.ordType = ordType;
	}

	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
	}

	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}

}
