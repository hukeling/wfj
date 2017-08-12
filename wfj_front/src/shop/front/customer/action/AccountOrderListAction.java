package shop.front.customer.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ISafetyCertificateService;
import shop.front.customer.pojo.OrderDetailTime;
import shop.front.customer.service.IAccountOrderListService;
import shop.lineofcreditItem.pojo.LineofcreditItem;
import shop.lineofcreditItem.service.ILineofcreditItemService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersOPLog;
import shop.order.pojo.Shipping;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.order.service.IShippingService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.CustomerHaveCoupon;
import shop.store.pojo.ShopInfo;
import shop.store.service.ICustomerHaveCouponService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.express100.bean.Result;
import util.express100.bean.ResultItem;
import util.express100.callback.demo.JacksonHelper;
import util.other.Escape;
import util.other.SerialNumberUtil;
import util.other.Utils;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
import cn.emay.mina.core.write.WriteException;
/** 
 * AccountOrderListAction - 用户中心订单Action类
 */
@SuppressWarnings("serial")
public class AccountOrderListAction extends BaseAction{
	private IOrdersService ordersService;//订单Service
	private String customerId;//用户Id
	@SuppressWarnings("rawtypes")
	private List<Map> ordersListMap = new ArrayList<Map>();//所有订单集合
	@SuppressWarnings("rawtypes")
	private List<Map> ordersList = new ArrayList<Map>();//订单明细
	/**判断查询条件是订单状态还是条件查询**/
	private String flag;
	private String ordersId;//订单Id
	private Integer pageIndex=1;//订单列表的当前页
	private Integer pageSize=10;//分页-每一页显示的个数
	/**---------------------------ss--------------------------------------**/
	/**开始时间**/
	private String startTime;
	/**结束时间**/
	private String endTime;
	/**订单对象**/
	private Orders orders;
	/**商品的service**/
	private IProductInfoService productInfoService;
	/**订单中商品的信息**/
	@SuppressWarnings({"rawtypes" })
	private List<Map> listProduct;
	/**客户已领优惠券service**/
	private ICustomerHaveCouponService customerHaveCouponService ;
	/**客户已领优惠券**/
	private CustomerHaveCoupon custHaveCou;
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**店铺**/
	private ShopInfo shopInfo;
	/**订单操作日志service**/
	private IOrdersOPLogService ordersOPLogService ;
	/**订单日志集合**/
	private List<OrdersOPLog> listOrdersLog;
	/**订单优惠总金额**/
	private BigDecimal discount;
	/**订单商品总数**/
	private Integer totalCount;
	/**用户订单service**/
	private IAccountOrderListService accountOrderListService;
	/**订单详情中的时间**/
	private OrderDetailTime orderDetailTime;
	/**物流信息service**/
	private IShippingService shippingService;
	/**快递信息**/
	private Shipping shipping;
	/**物流信息详细集合*/
	private ArrayList<ResultItem> resultItemList ;
	/**收货人不做编码**/
	private String consignee;
	/**详细地址不做编码**/
	private String address;
	/**查询物流返回信息**/
	private String infos;
	/**支付宝显示的商品描述**/
	private String subject ="您从天海商城购买商品";
	/**安全认证pojo**/
	private SafetyCertificate safetyCertificate;
	/**安全认证Service**/
	private ISafetyCertificateService safetyCertificateService; 
	/**按照总订单号分组显示到前台**/
	private Map<Object,Object> ordersMap;
	/**订单状态*/
	private String ordersState;
	/**结算状态**/
	private String settlementStatus;
	/**会员等级授信额度明细表**/
	private ILineofcreditItemService lineofcreditItemService;
	/**合并付款ids**/
	private String ids;
	/**合并付款总金额**/
	private BigDecimal money = new BigDecimal(0);
	/**总订单号**/
	private String totalOrdersNo = SerialNumberUtil.VirtualCoinNumber();
	private IOrdersListService ordersListService;
	/**区域service**/
	private IAreaService areaService;
	/**同城快递员信息**/
	private Map<String, Object> cityCourierMap;
	/******************************end*******************************************/
	//查看买家未结算订单
	public String toPayOrdersList(){
		Customer customer =(Customer) session.getAttribute("customer");
		String hql = "";
		String params="";
		hql = "select o.ordersId as ordersId,o.ordersNo as ordersNo,o.finalAmount as finalAmount,o.createTime as createTime from "
				+ "Orders o  where o.settlementStatus=1 and o.customerId="+customer.getCustomerId();
		params = "select count(o.ordersId) from Orders o WHERE o.settlementStatus=1 and o.customerId="+customer.getCustomerId();
		if(startTime != null){
			//String dateStart = dateformat.format(startTime);
			hql += " and UNIX_TIMESTAMP(o.createTime) >= UNIX_TIMESTAMP('"+startTime+"')";
			params += " and UNIX_TIMESTAMP(o.createTime) >= UNIX_TIMESTAMP('"+startTime+"')";
		}
		if(endTime != null){
			//String dateEnd = dateformat.format(endTime);
			hql += " and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(o.createTime)";
			params += " and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(o.createTime)";
		}
		int totalRecordCount = ordersService.getMultilistCount(params);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		ordersList = ordersService.findListMapPage(hql+" order by o.createTime desc", pageHelper);
		return SUCCESS;
	}
	//查看用户订单列表(根据会员的Id，设定为2)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String OrderList() throws IOException{
		Customer customer =(Customer) session.getAttribute("customer");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(customer!=null){
			String hql="";
			String params="";
			hql = "select  a.totalOrdersNo as totalOrdersNo, a.ordersId as ordersId, a.buyerName as buyerName," +
					" a.ordersNo as ordersNo,a.consignee as consignee,a.finalAmount as finalAmount, a.useLineOfCredit as useLineOfCredit,a.createTime as createTime," +
					" a.ordersState as ordersState,a.settlementStatus as settlementStatus from Orders a  where a.customerId="+customer.getCustomerId();
			params = "select count(o.ordersId) from Orders o WHERE o.totalOrdersNo is not null and o.customerId="+customer.getCustomerId();
			if(!"Start".equals(startTime)&&!StringUtils.isEmpty(startTime)){
				//对开始时间进行处理
				String startTimeNew=startTime+" 00:00:00";
				hql+=" and UNIX_TIMESTAMP(a.createTime)>=UNIX_TIMESTAMP('"+startTimeNew+"')";
				params+=" and UNIX_TIMESTAMP(o.createTime)>=UNIX_TIMESTAMP('"+startTimeNew+"')";
			}
			if(!StringUtils.isEmpty(endTime)&&!"End".equals(endTime)){
				//对结束时间进行处理
				String endTimeNew =endTime+" 23:59:59";
				hql+=" and UNIX_TIMESTAMP(a.createTime)<=UNIX_TIMESTAMP('"+endTimeNew+"')";
				params+=" and UNIX_TIMESTAMP(o.createTime)<=UNIX_TIMESTAMP('"+endTimeNew+"')";
			}
			if(customer.getType()!=3){
				if(orders!=null&&orders.getBuyerName()!=null&&!"".equals(orders.getBuyerName())){
					hql+=" and a.buyerName like '%"+orders.getBuyerName().trim()+"%'";
					params+=" and o.buyerName like '%"+orders.getBuyerName().trim()+"%'";
				}
			}
			if(orders!=null && orders.getOrdersState()!=null ){
				if(orders.getOrdersState()==0||orders.getOrdersState()==1){
					hql+=" and a.settlementStatus="+orders.getOrdersState()+" and a.ordersState=0";
					params+=" and o.settlementStatus="+orders.getOrdersState()+" and o.ordersState=0";
				}else{
					hql+=" and a.ordersState="+orders.getOrdersState();
					params+=" and o.ordersState="+orders.getOrdersState();
				}
			}
			if(son!=null){
				if(son.getType()==1){
					hql+=" and a.sonaccountId="+son.getSonAccountId();
					params+=" and o.sonaccountId="+son.getSonAccountId();
				}
				if(son.getType()==2){
					if(orders==null || orders.getSettlementStatus()==null ){
						hql+=" and (a.settlementStatus=0 or a.settlementStatus=1) and a.ordersState!=6";
						params+=" and (o.settlementStatus=0 or o.settlementStatus=1) and o.ordersState!=6";
					}
				}
			}
			if(orders!=null &&!"Enter order No".equals(orders.getOrdersNo())&& StringUtils.isNotEmpty(orders.getOrdersNo())){
				hql+=" and (a.ordersNo='"+orders.getOrdersNo().trim()+"' or a.totalOrdersNo='"+orders.getOrdersNo().trim()+"')";
				params+=" and (o.ordersNo='"+orders.getOrdersNo().trim()+"' or o.totalOrdersNo='"+orders.getOrdersNo().trim()+"')";
			}
			int totalRecordCount = ordersService.getMoreTableCount(params);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			ordersListMap = ordersService.findListMapPage(hql+" order by a.createTime desc", pageHelper);
			//将当前的查询出的订单列表按照总订单号拆分显示，已经每个订单中商品的图片
			ordersMap = new LinkedHashMap<Object, Object>();
			for(Map<String,Object> map:ordersListMap){
				List<Object> listMap = new ArrayList<Object>();
				String hql1="select a.productFullName as productName,a.productId as productId, a.logoImage as thumbnail,a.memberPrice as memberPrice  from OrdersList a   where a.ordersId="+map.get("ordersId");
				List<Map> mapImg=ordersService.findListMapByHql(hql1);
				map.put("mapImg", mapImg);
				if(ordersMap!=null&&ordersMap.size()>0){
					if(ordersMap.get(map.get("totalOrdersNo"))!=null){
						listMap =(List<Object>) ordersMap.get(map.get("totalOrdersNo"));
					}
					listMap.add(map);
				}else{
					listMap.add(map);
				}
				ordersMap.put(map.get("totalOrdersNo"), listMap);
			}
		}
		return SUCCESS;
	}
	/*/根据状态查看订单列表
	public String ordersByStatus(){
		Customer customer =(Customer) session.getAttribute("customer");
		if(null!=customer && customer.getCustomerId()!=null && orders!=null && orders.getOrdersState()!=null){
			String hql="SELECT c.productId as productId,a.ordersNo as ordersNo,b.thumbnail as thumbnail,a.consignee as consignee,a.finalAmount as finalAmount,a.createTime as createTime,a.ordersState as ordersState FROM Orders a,ProductImg b ,OrdersList c  WHERE a.ordersId=c.ordersId AND b.productId=c.productId and a.customerId="+customer.getCustomerId()+" AND a.ordersState="+orders.getOrdersState();
			ordersListMap = ordersService.findListMapByHql(hql);
		}
		return SUCCESS;
	}*/
	/**
	 * 根据订单状态查看详情
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String accountOrderDetail(){
		if(orders!=null && orders.getOrdersId()!=null){
			//订单信息（包含收货人信息）
			orders = (Orders) ordersService.getObjectByParams("where o.ordersId="+orders.getOrdersId());
			if(orders!=null){
				String hql = "select a.sku as sku, a.productId as productId, a.count as count ,a.productFullName as productFullName,a.salesPrice as salesPrice,a.marketPrice as marketPrice,a.logoImage as logoImg,a.memberPrice as memberPrice" +
						",a.freightPrice as freightPrice,a.virtualCoinNumber as virtualCoinNumber,a.discount as discount ,a.brandName as brandName,a.brandId as brandId ,a.shopInfoId as shopInfoId,a.shopName as shopName,a.stockUpDate as stockUpDate, b.storeNumber as storeNumber,c.fullName as deliveryAddress" +
						" from OrdersList a, ProductInfo b ,BasicArea c where  a.ordersId="+orders.getOrdersId()+" and a.productId = b.productId and c.areaId = b.deliveryAddressCities";
				listProduct=productInfoService.findListMapByHql(hql);
				List<Map> list = productInfoService.findListMapByHql("select a.productId as productId, sum(a.salesPrice) as totalSalesPrice, sum(a.marketPrice) as totalMarketPrice  , sum(a.count) as totalCount from OrdersList a  where  a.ordersId="+orders.getOrdersId()+" group by a.productId");
				BigDecimal oldDiscount = new BigDecimal(0);
				discount=new BigDecimal(0);
				Integer oldTotalCount=0;
				for(Map map:list){
					BigDecimal totalSalesPrice = new BigDecimal(0);
					BigDecimal totalMarketPrice = new BigDecimal (0);
					if(null!=map.get("totalSalesPrice")&&null!=map.get("totalMarketPrice")){
						totalSalesPrice = new BigDecimal (String.valueOf(map.get("totalSalesPrice")));
						totalMarketPrice = new BigDecimal (String.valueOf(map.get("totalMarketPrice")));
						oldDiscount=totalSalesPrice.subtract(totalMarketPrice);//减法
					}else{
						oldDiscount=new BigDecimal(0);
					}
					if(null!=map.get("totalCount")){
						oldTotalCount = Integer.parseInt(String.valueOf(map.get("totalCount")));
					}else{
						oldTotalCount = 0;
					}
					if(totalCount!=null){
						totalCount=oldTotalCount+totalCount;
					}else{
						totalCount=oldTotalCount;
					}
					oldDiscount = oldDiscount.multiply(new BigDecimal(oldTotalCount));
					discount=discount.add(oldDiscount);
				}
				if(listProduct!=null && listProduct.size()>0){
					BigDecimal totalMemberPrice=new BigDecimal(0);
					for(Map map:listProduct){
						Object object = map.get("memberPrice");
						String count = String.valueOf(map.get("count"));
						
						totalMemberPrice = totalMemberPrice.add(new BigDecimal(String.valueOf(object)).multiply(new BigDecimal(count)));
					}
					request.setAttribute("totalMemberPrice", totalMemberPrice);
				}
				//店铺信息
				shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+orders.getShopInfoId());
				//订单日志信息
				listOrdersLog = ordersOPLogService.findObjects(" where o.ordersId="+orders.getOrdersId()+"order by o.operatorTime"); 
				//获取明细中的几个时间
				orderDetailTime = new OrderDetailTime();
				for(OrdersOPLog ordersLog:listOrdersLog){
					if(1==ordersLog.getOrdersOperateState()){
						orderDetailTime.setCreateTime(ordersLog.getOperatorTime());
					}else if(2==ordersLog.getOrdersOperateState()){
						orderDetailTime.setPayTime(ordersLog.getOperatorTime());
					}else if(4==ordersLog.getOrdersOperateState()){
						orderDetailTime.setDeliveryTime(ordersLog.getOperatorTime());
					}else if(5==ordersLog.getOrdersOperateState()){
						orderDetailTime.setReceivingTime(ordersLog.getOperatorTime());
					}
				}
				//查找物流信息
				if(orders.getOrdersState()==4||orders.getOrdersState()==5||orders.getOrdersState()==9){
					shipping = (Shipping) shippingService.getObjectByParams(" where o.ordersId="+orders.getOrdersId());
					//如果快递信息不为空则取出
					if(Utils.objectIsNotEmpty(shipping)&&Utils.objectIsNotEmpty(shipping.getExpressInfo())){
						//不是同城快递
						if(!(shipping.getCode()).equals("tongcheng")){
							Result result = JacksonHelper.fromJSON(shipping.getExpressInfo(),Result.class);
							resultItemList = result.getData();
							Collections.reverse(resultItemList);//颠倒顺序
						//是同城快递
						}else if((shipping.getCode()).equals("tongcheng")){
							String express = shipping.getExpressInfo();
							//最外层解析  
							JSONObject json = JSONObject.fromObject(express);
							cityCourierMap = (Map<String, Object>) JSONObject.toBean(json, Map.class);
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 从我的订单中去支付方法
	 * @return
	 */
	public String orderToPay() throws Exception{
			Customer customer = (Customer) session.getAttribute("customer");
			//商品信息
			String hql = "select a.productId as productId, a.count as count ,a.productFullName as productFullName,a.salesPrice as salesPrice,a.logoImage as logoImg," +
					"a.freightPrice as freightPrice,a.brandName as brandName,a.brandId as brandId ,a.shopInfoId as shopInfoId,a.shopName as shopName,a.stockUpDate as stockUpDate" +
					" from OrdersList a where a.customerId="+customer.getCustomerId()+" ";
			if(orders!=null && orders.getOrdersId()!=null){//单个支付
				//订单信息（包含收货人信息）
				orders = (Orders) ordersService.getObjectByParams("where o.ordersId="+orders.getOrdersId());
				consignee = orders.getConsignee();
				address = orders.getAddress();
				subject = Escape.escape(subject);
				orders.setAddress(Escape.escape(orders.getAddress()));
				orders.setConsignee(Escape.escape(orders.getConsignee()));
				orders.setTotalOrdersNo(orders.getOrdersNo());
				hql+=" and a.ordersId="+orders.getOrdersId();
				listProduct=productInfoService.findListMapByHql(hql);
				return SUCCESS;
			}else{//合并支付
				
				Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
				if(customer!=null){
					String hql2="";
					hql2 = "select  a.totalOrdersNo as totalOrdersNo, a.ordersId as ordersId, a.buyerName as buyerName," +
							" a.ordersNo as ordersNo,a.consignee as consignee,a.finalAmount as finalAmount, a.useLineOfCredit as useLineOfCredit,a.createTime as createTime," +
							" a.ordersState as ordersState,a.settlementStatus as settlementStatus from Orders a  where a.customerId="+customer.getCustomerId()+" and a.ordersId in ( "+ids+" ) ";
					if(son!=null){
						if(son.getType()==1){
							hql2+=" and a.sonaccountId="+son.getSonAccountId();
						}
						if(son.getType()==2){
							if(orders==null || orders.getSettlementStatus()==null ){
								hql2+=" and (a.settlementStatus=0 or a.settlementStatus=1) and a.ordersState!=6";
							}
						}
					}
					ordersListMap = ordersService.findListMapByHql(hql2+" order by a.createTime desc");
					//将当前的查询出的订单列表按照总订单号拆分显示，已经每个订单中商品的图片
					ordersMap = new LinkedHashMap<Object, Object>();
					if(ordersListMap!=null&&ordersListMap.size()>0){
						BigDecimal totalFinalMoney=new BigDecimal(0);//合并付款总金额
						String ordersIds="";//订单ids字符串
						for(Map<String,Object> map:ordersListMap){
							//总金额追加操作
							totalFinalMoney=totalFinalMoney.add(new BigDecimal(String.valueOf(map.get("finalAmount"))));
							//追加订单id
							ordersIds+=String.valueOf(map.get("ordersId"))+",";
							List<Object> listMap = new ArrayList<Object>();
							String hql1="select a.productFullName as productName,a.productId as productId, a.logoImage as thumbnail  from OrdersList a   where a.ordersId="+map.get("ordersId");
							List<Map> mapImg=ordersService.findListMapByHql(hql1);
							map.put("mapImg", mapImg);
							if(ordersMap!=null&&ordersMap.size()>0){
								if(ordersMap.get(map.get("totalOrdersNo"))!=null){
									listMap =(List<Object>) ordersMap.get(map.get("totalOrdersNo"));
								}
								listMap.add(map);
							}else{
								listMap.add(map);
							}
							ordersMap.put(map.get("totalOrdersNo"), listMap);
						}
						request.setAttribute("totalFinalMoney", totalFinalMoney);
						if(!"".equals(ordersIds)){
							ordersIds=ordersIds.substring(0,ordersIds.lastIndexOf(","));
						}
						request.setAttribute("ordersIds", ordersIds);
					}
				}
				return "topay";
			}
	}
	/**
	 * 获取下单人的安全认证信息
	 * @param customerId 当前登录用户
	 * @return安全认证信息
	 */
	public SafetyCertificate  getSafety(Integer customerId){
		SafetyCertificate safetyCertificate = (SafetyCertificate) safetyCertificateService.getObjectByParams(" where o.customerId="+customerId);
		return safetyCertificate;
	}
	/**
	 * 未付款之前取消订单
	 * @return
	 * @throws Exception 
	 */
	public String cancelOrder() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo =(ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(orders!=null && orders.getOrdersId()!=null && customer!=null){
			accountOrderListService.saveOrUpdateCancelOrder(orders, customer,shopInfo,son);
			if(customer.getType()!=3){
				quxiaoOrders(orders,customer);
			}
		}
		return SUCCESS;
	}
	/***
	 * 用户确认订单
	 * @return
	 */
	public String confirmOrder(){
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo =(ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(customer!=null && orders!=null && orders.getOrdersId()!=null){
			accountOrderListService.saveConfirmOrder(orders.getOrdersId(), customer,shopInfo,son);
		}
		return SUCCESS;
	}
	/**
	 * 订单列表中的导出
	 * @throws IOException 
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", })
	public void importExcelCom() throws Exception{
		Customer customer = (Customer)session.getAttribute("customer");
		Sonaccount sonaccount = (Sonaccount)session.getAttribute("sonaccount");
		List<Map> list = null;
		String hql= null;
		if(customer!=null){
			if(customer.getType()==1||customer.getType()==3){
				/**企业导出:
				 * 1.序号 2.订单编号 3.下单时间 4.订单状态 5.付款状态 6.采购员 7.收货人 8.发货地点 
				 * 9.店铺 10.订货号 11.商品编码 12.商品条码 13.商品名称
				 * 15.数量 16.金额小计 17.订单运费**/
				if(sonaccount!=null){
					hql = "select o.ordersNo as ordersNo, o.createTime as createTime, o.ordersState as ordersState, o.settlementStatus as settlementStatus, o.buyerName as buyerName, o.consignee as consignee, b.deliveryAddress as deliveryAddress, c.shopName as shopName, "
						+ "d.sku as sku, b.productCode as productCode, b.barCode as barCode, b.productFullName as productName, d.count as count, o.totalAmount as totalAmount, "
						+ "o.freight as freight from Orders o, ProductInfo b, ShopInfo c, OrdersList d "
						+ "where o.ordersId in("+ids+")"+" and o.ordersNo = d.ordersNo and o.shopInfoId = b.shopInfoId and b.shopInfoId = c.shopInfoId and o.shopInfoId = c.shopInfoId and d.productId = b.productId";
					 if(sonaccount.getType()==1){
						 hql+=" and o.sonaccountId = "+sonaccount.getSonAccountId();
					 }
				}else{
					hql = "select o.ordersNo AS ordersNo, o.createTime AS createTime, o.ordersState AS ordersState, o.settlementStatus as settlementStatus, o.buyerName AS buyerName,o.consignee as consignee, b.deliveryAddress as deliveryAddress, c.shopName as shopName, d.sku as sku, "
						+ "b.productCode as productCode, b.barCode as barCode, b.productFullName as productName, d.count as count, o.totalAmount as totalAmount, o.freight as freight "
						+ "from Orders o, ProductInfo b, ShopInfo c, OrdersList d "
						+ "where o.customerId = "+customer.getCustomerId()+" and o.ordersId in("+ids+")"+" and o.ordersNo = d.ordersNo and o.shopInfoId = b.shopInfoId and b.shopInfoId = c.shopInfoId and o.shopInfoId = c.shopInfoId and d.productId = b.productId";
				}
				//数据listMap
				list = ordersService.findListMapByHql(hql);
				if(list!=null&&list.size()>0){
					for(Map m :list){
						//操作订单日志相关信息 为了查询出一个订单中 多个商品 对应的SKU订货号 及每个商品的价钱数据
						String ordersNo = String.valueOf(m.get("ordersNo"));
						String sku = "";
						String productCode="";
						String barCode="";
						String productFullName="";
						String count="";
						List<Map> ordersListMap = ordersListService.findListMapByHql("select o.sku as sku ,o.productFullName as productFullName,o.count as count,o.productCode as productCode,o.barCode as barCode  from OrdersList o where o.ordersNo='"+ordersNo+"'");
						if(ordersListMap!=null&&ordersListMap.size()>0){
							if(ordersListMap.size()==1){
								sku = String.valueOf(ordersListMap.get(0).get("sku"));
								productCode = String.valueOf(ordersListMap.get(0).get("productCode"));
								barCode = String.valueOf(ordersListMap.get(0).get("barCode"));
								productFullName = String.valueOf(ordersListMap.get(0).get("productFullName"));
								count = String.valueOf(ordersListMap.get(0).get("count"));
							}else{
								for(Map p:ordersListMap){
									sku += String.valueOf(p.get("sku"))+"\n";
									productCode += String.valueOf(p.get("productCode"))+"\n";
									barCode += String.valueOf(p.get("barCode"))+"\n";
									productFullName += String.valueOf(p.get("productFullName"))+"\n";
									count += String.valueOf(p.get("count"))+"\n";
								}
							}
						}
						m.put("sku", sku);
						m.put("productCode", productCode);
						m.put("barCode", barCode);
						m.put("productFullName", productFullName);
						m.put("count", count);
						//查询订单物流信息
						/*Shipping shipping = (Shipping) shippingService.getObjectByParams(" where o.ordersId="+String.valueOf(m.get("ordersId")));
						if(shipping!=null){
							m.put("shipping", shipping.getDeliveryCorpName());//向map中添加物流公司名称
						}else{
							m.put("shipping", "");//向map中添加物流公司名称
						}*/
					}
				}
			}
		}
		boolean b = madeExl(list);
		String filePathXLS = null;
		String downloadFileName = null;
		if(customer.getType()==1&&sonaccount==null){
			filePathXLS="excel/QiYe_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls";
			downloadFileName = "QiYe_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls";
		}else if(sonaccount!=null&&sonaccount.getType()==1){
			filePathXLS="excel/CaiGouYuan_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls";
			downloadFileName = "CaiGouYuan_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls";
		}else if(sonaccount!=null&&sonaccount.getType()==2){
			filePathXLS="excel/CaiWu_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls";
			downloadFileName = "CaiWu_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls";
		}else{
			filePathXLS="excel/GeRen_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls";
			downloadFileName = "GeRen_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls";
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("filePathXLS",filePathXLS);
		jo.accumulate("downloadFileName",downloadFileName);
		jo.accumulate("isSuccess", b + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 企业订单列表导出:先保存在服务器,在下载
	 * @param list   数据集合
	 * @return
	 * @throws IOException
	 * @throws jxl.write.WriteException 
	 */
	@SuppressWarnings("rawtypes")
	public boolean madeExl(List<Map> list)throws IOException, jxl.write.WriteException{
		Customer customer = (Customer)session.getAttribute("customer");
		Sonaccount sonaccount = (Sonaccount)session.getAttribute("sonaccount");
		boolean b=true;
		try {
			File toFile = null;
			String url = (String) fileUrlConfig.get("fileUploadRoot");
			File file = new File(url+"/excel");
			file.mkdirs();
			if(customer.getType()==1&&sonaccount==null){
				toFile=new File(url+"/excel/QiYe_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls");
			}else if(sonaccount!=null&&sonaccount.getType()==1){
				toFile=new File(url+"/excel/CaiGouYuan_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls"); 
			}else if(sonaccount!=null&&sonaccount.getType()==2){
				toFile=new File(url+"/excel/CaiWu_"+sonaccount.getEmail().substring(0, sonaccount.getEmail().lastIndexOf("@"))+".xls"); 
			}else{
				toFile=new File(url+"/excel/GeRen_"+customer.getEmail().substring(0,customer.getEmail().lastIndexOf("@"))+".xls");
			}
			if(!toFile.exists()){ 
				toFile.createNewFile(); 
			}// 创建目标文件
			WritableWorkbook wbook = Workbook.createWorkbook(toFile);// 创建一个工作表 第一个工作区
			WritableSheet wsheet = wbook.createSheet("SHOPJSP:企业订单列表", 0);
			WritableFont wf3 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
	        WritableCellFormat wcfmt3 = new WritableCellFormat(wf3);
	        wcfmt3.setWrap(true);// 自动换行
	        wcfmt3.setVerticalAlignment(VerticalAlignment.TOP);//顶端对齐
	        wcfmt3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //设置边框
	        wcfmt3.setAlignment(jxl.format.Alignment.CENTRE);
			biaoge(wsheet);
			int i=1;//拿list中的数据
			if(list!=null&&list.size()>0){
				for(Map m:list){
					Label a = new Label(0, i+1, String.valueOf(i), wcfmt3);//1序号
					wsheet.addCell(a);
					Label ordersNo = new Label(1, i+1, String.valueOf(m.get("ordersNo")), wcfmt3);//2订单编号
					wsheet.addCell(ordersNo);
					Label createTime = new Label(2, i+1, String.valueOf(m.get("createTime")), wcfmt3);//3下单时间
					wsheet.addCell(createTime);
					if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(0))==0){//4订单状态
						Label ordersState = new Label(3, i+1, "生成订单", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(1))==0){
						Label ordersState = new Label(3, i+1, "付款成功", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(3))==0){
						Label ordersState = new Label(3, i+1, "正在配货", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(4))==0){
						Label ordersState = new Label(3, i+1, "已经发货", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(5))==0){
						Label ordersState = new Label(3, i+1, "已收货", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(6))==0){
						Label ordersState = new Label(3, i+1, "订单取消", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(7))==0){
						Label ordersState = new Label(3, i+1, "问题订单", wcfmt3);
						wsheet.addCell(ordersState);
					}else if(new BigDecimal(String.valueOf(m.get("ordersState"))).compareTo(new BigDecimal(9))==0){
						Label ordersState = new Label(3, i+1, "已评价", wcfmt3);
						wsheet.addCell(ordersState);
					}
					if(m.get("settlementStatus")!=null){
						if(new BigDecimal(String.valueOf(m.get("settlementStatus"))).compareTo(new BigDecimal(0))==0){//5付款状态
							Label settlementStatus = new Label(4, i+1, "未付款", wcfmt3);
							wsheet.addCell(settlementStatus);
						}else if(new BigDecimal(String.valueOf(m.get("settlementStatus"))).compareTo(new BigDecimal(1))==0){
							Label settlementStatus = new Label(4, i+1, "已付款", wcfmt3);
							wsheet.addCell(settlementStatus);
						}
					}else{
						Label settlementStatus = new Label(4, i+1, "", wcfmt3);
						wsheet.addCell(settlementStatus);
					}
					Label buyerName;
					if(StringUtils.isNotEmpty(String.valueOf(m.get("buyerName")))&&!String.valueOf(m.get("buyerName")).equals("null")){
						buyerName = new Label(5, i+1,String.valueOf(m.get("buyerName")) , wcfmt3);//6采购员
					}else{
						buyerName = new Label(5, i+1,String.valueOf(shopConfig.get("buyerName")) , wcfmt3);//6采购员
					}
					wsheet.addCell(buyerName);
					Label consignee = new Label(6, i+1, String.valueOf(m.get("consignee")), wcfmt3);//7收货人
					wsheet.addCell(consignee);
/*					Label deliveryAddress = new Label(7, i+1, String.valueOf(m.get("deliveryAddress")), wcfmt3);//8发货地点
					wsheet.addCell(deliveryAddress);*/
					//取商品的发货地址
					BasicArea adress=(BasicArea)areaService.getObjectById(m.get("deliveryAddressCities")+"");
					Label deliveryAddress = new Label(7, i+1, adress.getFullName(), wcfmt3);
					wsheet.addCell(deliveryAddress);
					Label shopName = new Label(8, i+1, String.valueOf(m.get("shopName")), wcfmt3);//9店铺
					wsheet.addCell(shopName);
					Label sku = new Label(9, i+1, String.valueOf(m.get("sku")), wcfmt3);//10订货号
					wsheet.addCell(sku);
					Label productCode = new Label(10, i+1, String.valueOf(m.get("productCode")), wcfmt3);//
					wsheet.addCell(productCode);
					Label barCode = new Label(11, i+1, String.valueOf(m.get("barCode")), wcfmt3);
					wsheet.addCell(barCode);
					Label productName = new Label(12, i+1, String.valueOf(m.get("productName")), wcfmt3);
					wsheet.addCell(productName);
					Label count = new Label(13, i+1, String.valueOf(m.get("count")), wcfmt3);
					wsheet.addCell(count);
					Label totalAmount = new Label(14, i+1, String.valueOf(m.get("totalAmount")), wcfmt3);
					wsheet.addCell(totalAmount);
					Label freight = new Label(15, i+1, String.valueOf(m.get("freight")), wcfmt3);
					wsheet.addCell(freight);
					i++;
				}
			}else{
				Label cwqd = new Label(1, i+1, "错误清单", wcfmt3);//1序号
				wsheet.addCell(cwqd);
			}
			wbook.write();// 写入文件
			wbook.close();
		} catch (WriteException e) {
			b=false;
			e.printStackTrace();
		} 
		return b;
	}
	/**
	 * 订单列表取消订单
	 * @return
	 */
	public void quxiaoOrders(Orders orders,Customer customer){
		//取消订单,把冻结额度在加回去
		LineofcreditItem li_1 = (LineofcreditItem) lineofcreditItemService.getObjectByParams("where o.customerId="+customer.getCustomerId()+" order by o.operatorTime desc,o.lineOfCreditId desc");
		//根据订单号查询一个记录
		LineofcreditItem li2 = (LineofcreditItem)lineofcreditItemService.getObjectByParams("where o.ordersId="+orders.getOrdersId());
		if(li_1!=null){
			LineofcreditItem li = new LineofcreditItem();//额度明细
			li.setCustomerId(li_1.getCustomerId());
			li.setOrdersId(li_1.getOrdersId());//订单ID
			li.setOrdersNo(li_1.getOrdersNo());//订单号
			li.setSerialNumber(li_1.getSerialNumber());//随机流水号
			li.setType(1);//子会员类型
			li.setTransactionNumber(new BigDecimal(0));//使用额度值
			BigDecimal frozenNumber = li2.getFrozenNumber();//冻结额度数量
			li.setTransactionNumber(new BigDecimal(0));//已使用额度
			li.setRemainingNumber(frozenNumber.add(li_1.getRemainingNumber()));//余额
			li.setFrozenNumber(new BigDecimal(0));//冻结额度数量
			li.setTradeTime(li_1.getTradeTime());
			li.setOperatorTime(new Date());//操作时间
			li.setRemarks("取消订单解冻额度： "+frozenNumber+"元");
			lineofcreditItemService.saveOrUpdateObject(li);
		}
	}
	/**
	 * 设置表格样式
	 * @return
	 * @throws WriteException 
	 * @throws jxl.write.WriteException 
	 */
	public void biaoge(WritableSheet wsheet) throws WriteException, jxl.write.WriteException{
		//设置 行、 列的 宽度 、高度
		wsheet.setColumnView(0, 5); // 设置列的宽度---序号
		wsheet.setColumnView(1, 28); // 设置列的宽度---订单编号
		wsheet.setColumnView(2, 20); // 设置列的宽度---下单时间
		wsheet.setColumnView(3, 12); // 设置列的宽度---订单状态
		wsheet.setColumnView(4, 12); // 设置列的宽度---付款状态
		wsheet.setColumnView(5, 12); // 设置列的宽度---采购员
		wsheet.setColumnView(6, 12); // 设置列的宽度---发货人
		wsheet.setColumnView(7, 20); // 设置列的宽度---发货地点
		wsheet.setColumnView(8, 20); // 设置列的宽度---店铺
		wsheet.setColumnView(9, 15); // 设置列的宽度---订货号
		wsheet.setColumnView(10, 15); // 设置列的宽度---商品编码
		wsheet.setColumnView(11,15); // 设置列的宽度---商品条码
		wsheet.setColumnView(12,70); // 设置列的宽度---商品名称
		wsheet.setColumnView(13,10); // 设置列的宽度---数量
		wsheet.setColumnView(14,15); // 设置列的宽度---金额小计
		wsheet.setColumnView(15,15); // 设置列的宽度---订单运费
		//让 第一列居中
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 20, WritableFont.NO_BOLD, false);
        WritableCellFormat wcfmt = new WritableCellFormat(wf);
        wcfmt.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //给title设置边框
        wcfmt.setAlignment(jxl.format.Alignment.CENTRE);
        wcfmt.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
        Label lable1 = new Label(0,0,"SHOPJSP:企业订单列表",wcfmt);//添加 Lable对象  (列、行)
        wsheet.addCell(lable1);
		wsheet.mergeCells(0, 0, 15, 0);//合并单元格（列、行）
        //列头
        WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);
        WritableCellFormat wcfmt2 = new WritableCellFormat(wf2);
        wcfmt2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //给title设置边框
        wcfmt2.setAlignment(jxl.format.Alignment.CENTRE);
        wcfmt2.setBackground(jxl.format.Colour.GRAY_25);// 设置单元格的背景颜色
		String[] title = {"序号","订单编号", "下单时间","订单状态", "付款状态", "采购员", "发货人","发货地点 ", "店铺","订货号", "商品编码", "商品条码", ".商品名称", "数量", "金额小计", "订单运费"};
		for (int m = 0; m < title.length; m++) {		// 设置表头
			Label excelTitle = new Label(m, 1, title[m], wcfmt2);// 一列列的打印表头 按照我们规定的格式
			wsheet.addCell(excelTitle);// 把标头加到我们的工作区
		}
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getOrdersListMap() {
		return ordersListMap;
	}
	@SuppressWarnings("rawtypes")
	public void setOrdersListMap(List<Map> ordersListMap) {
		this.ordersListMap = ordersListMap;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getOrdersList() {
		return ordersList;
	}
	@SuppressWarnings("rawtypes")
	public void setOrdersList(List<Map> ordersList) {
		this.ordersList = ordersList;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getListProduct() {
		return listProduct;
	}
	@SuppressWarnings("rawtypes")
	public void setListProduct(List<Map> listProduct) {
		this.listProduct = listProduct;
	}
	public CustomerHaveCoupon getCustHaveCou() {
		return custHaveCou;
	}
	public void setCustHaveCou(CustomerHaveCoupon custHaveCou) {
		this.custHaveCou = custHaveCou;
	}
	public void setCustomerHaveCouponService(
			ICustomerHaveCouponService customerHaveCouponService) {
		this.customerHaveCouponService = customerHaveCouponService;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public List<OrdersOPLog> getListOrdersLog() {
		return listOrdersLog;
	}
	public void setListOrdersLog(List<OrdersOPLog> listOrdersLog) {
		this.listOrdersLog = listOrdersLog;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public void setAccountOrderListService(
			IAccountOrderListService accountOrderListService) {
		this.accountOrderListService = accountOrderListService;
	}
	public OrderDetailTime getOrderDetailTime() {
		return orderDetailTime;
	}
	public void setOrderDetailTime(OrderDetailTime orderDetailTime) {
		this.orderDetailTime = orderDetailTime;
	}
	public void setShippingService(IShippingService shippingService) {
		this.shippingService = shippingService;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public SafetyCertificate getSafetyCertificate() {
		return safetyCertificate;
	}
	public void setSafetyCertificate(SafetyCertificate safetyCertificate) {
		this.safetyCertificate = safetyCertificate;
	}
	public void setSafetyCertificateService(
			ISafetyCertificateService safetyCertificateService) {
		this.safetyCertificateService = safetyCertificateService;
	}
	public Map<Object, Object> getOrdersMap() {
		return ordersMap;
	}
	public void setOrdersMap(Map<Object, Object> ordersMap) {
		this.ordersMap = ordersMap;
	}
	public String getOrdersState() {
		return ordersState;
	}
	public void setOrdersState(String ordersState) {
		this.ordersState = ordersState;
	}
	public void setLineofcreditItemService(
			ILineofcreditItemService lineofcreditItemService) {
		this.lineofcreditItemService = lineofcreditItemService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getTotalOrdersNo() {
		return totalOrdersNo;
	}
	public void setTotalOrdersNo(String totalOrdersNo) {
		this.totalOrdersNo = totalOrdersNo;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public ArrayList<ResultItem> getResultItemList() {
		return resultItemList;
	}
	public void setResultItemList(ArrayList<ResultItem> resultItemList) {
		this.resultItemList = resultItemList;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public Map<String, Object> getCityCourierMap() {
		return cityCourierMap;
	}
	public void setCityCourierMap(Map<String, Object> cityCourierMap) {
		this.cityCourierMap = cityCourierMap;
	}
}
