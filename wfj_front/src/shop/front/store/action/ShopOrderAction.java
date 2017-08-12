package shop.front.store.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.cityCourier.pojo.CityCourier;
import shop.cityCourier.service.ICityCourierService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerService;
import shop.customer.service.IEvaluateGoodsService;
import shop.front.customer.pojo.OrderDetailTime;
import shop.front.store.service.IShopOrdersService;
import shop.logistics.pojo.Logistics;
import shop.logistics.service.ILogisticsService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.OrdersOPLog;
import shop.order.pojo.Shipping;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.order.service.IShippingService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.CustomerHaveCoupon;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopSettlementDetail;
import shop.store.service.ICustomerHaveCouponService;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopSettlementDetailService;
import util.action.BaseAction;
import util.express100.Express100Thred;
import util.express100.bean.Result;
import util.express100.bean.ResultItem;
import util.express100.bean.TaskRequest;
import util.express100.callback.demo.JacksonHelper;
import util.express100.postOrder.demo.HttpRequest;
import util.other.Utils;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/** 
 * ShopOrderAction - 店铺订单Action类
 */
@SuppressWarnings({ "serial", "unused" })
public class ShopOrderAction extends BaseAction{
	private IShopInfoService shopInfoService;//店铺基本信息Service
	private IOrdersService ordersService;//订单基本信息Service
	private ICustomerService customerService;//会员基本信息Service
	/**店铺申请结算明细Service**/
	private IShopSettlementDetailService shopSettlementDetailService;
	@SuppressWarnings({"rawtypes" })
	private List<Map> productInfoList=new ArrayList<Map>();//初始化全部订单
	@SuppressWarnings("rawtypes")
	private List<Map> productInfoLists=new ArrayList<Map>();//初始化未付款订单
	@SuppressWarnings("rawtypes")
	private List<Map> ordersList=new ArrayList<Map>();//查看申请订单明细
	private Customer customer;//会员
	private String shopInfoId;//店铺ID
	private Orders orders;//订单
	private String ordersId;//订单ID
	private String params="";//根据参数排序
	private String flag="";//根据状态查看
	private Integer pageIndex=1;//当前页
	private Integer pageSize=10;//分页-每一页显示的个数
	private ShopInfo shopInfo;
	/**物流信息**/
	private Shipping shipping;
	/**物流信息详细集合*/
	private ArrayList<ResultItem> resultItemList;
	/**物流公司信息*/
	private List<Logistics> logisticsList;
	/**发货详情service**/
	private IShippingService shippingService;
	/**物流公司信息Service*/
	private ILogisticsService logisticsService;
	/**商品订单的Service**/
	private IShopOrdersService shopOrdersService;
	/**订单中商品的信息**/
	@SuppressWarnings("rawtypes")
	private List<Map> listProduct;
	/**订单操作日志service**/
	private IOrdersOPLogService ordersOPLogService ;
	/**订单日志集合**/
	private List<OrdersOPLog> listOrdersLog;
	/**订单优惠总金额**/
	private BigDecimal discount;
	/**订单商品总数**/
	private Integer totalCount;
	/**商品的service**/
	private IProductInfoService productInfoService;
	/**订单详情中的时间**/
	private OrderDetailTime orderDetailTime;
	/**订单详情信息**/
	private List<OrdersList> orderList=new ArrayList<OrdersList>();//查看申请订单明细
	/**查询物流返回信息**/
	private String infos;
	/**评价集合**/
	@SuppressWarnings("rawtypes")
	private List<Map> evaluateGoodsMap;
	/** 评价Service **/
	private IEvaluateGoodsService evaluateGoodsService;
	/**评价**/
	private EvaluateGoods evaluateGoods;
	/**客户已领优惠券service**/
	private ICustomerHaveCouponService customerHaveCouponService;
	/**客户已领优惠券**/
	private CustomerHaveCoupon custHaveCou;
	/**时间查询条件**/
	private String startTime;
	private String endTime;
	private String ids;
	private String settlementId;
	private IOrdersListService ordersListService;
	/**区域service**/
	private IAreaService areaService;
	/**同城快递信息service**/
	private ICityCourierService cityCourierService;
	/**同城快递信息实体**/
	private CityCourier cityCourier;
	/**快递方式**/
	private String logisticsType;
	/**同城快递员信息**/
	private Map<String, Object> cityCourierMap;
	private List<BasicArea> provinceList;
	private List<BasicArea> cityList;
	private String areaId;
	
	/*******************************END******************************/
	//申请结算记录
	public String shopApplyRecord(){
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		String hql = "select s.settlementId as settlementId,s.totalCost as totalCost,s.totalCost*s.commissionProportion*0.01 as commission,s.status as status,s.paymentInfo as paymentInfo,s.createDate as createDate from ShopSettlementDetail s where s.shopInfoId="+shopInfo.getShopInfoId();
		String coutHql = " where o.shopInfoId="+shopInfo.getShopInfoId();
		if(Utils.objectIsNotEmpty(startTime)){
			hql += " and UNIX_TIMESTAMP(s.createDate) >= UNIX_TIMESTAMP('"+startTime+"')";
			coutHql += " and UNIX_TIMESTAMP(o.createDate) >= UNIX_TIMESTAMP('"+startTime+"')";
		}
		if(Utils.objectIsNotEmpty(endTime)){
			hql += " and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(s.createDate)";
			coutHql += " and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(o.createDate)";
		}
		int totalRecordCount = shopSettlementDetailService.getCount(coutHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		productInfoList=shopSettlementDetailService.findListMapPage(hql+" order by s.createDate desc",pageHelper);
		return SUCCESS;
	}
	//查看申请记录
	public String settlementDetail(){
		ShopSettlementDetail shopSettlementDetail = (ShopSettlementDetail) shopSettlementDetailService.getObjectByParams(" where o.settlementId="+settlementId);
		//获取佣金结算比例
		BigDecimal commissionProportion = shopSettlementDetail.getCommissionProportion();
		BigDecimal multiply = commissionProportion.multiply(new BigDecimal(0.01));
		String hql = "select o.ordersId as ordersId,o.ordersNo as ordersNo,o.finalAmount as finalAmount,o.finalAmount*"+multiply+" as yj,o.finalAmount-o.finalAmount*"+multiply+" as js from Orders o where o.ordersId in("+shopSettlementDetail.getOrdersIds()+")";
		int totalRecordCount = ordersService.getCount(" where o.ordersId in("+shopSettlementDetail.getOrdersIds()+")");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		ordersList = ordersService.findListMapPage(hql,pageHelper);
		return SUCCESS;
	}
	//卖家向平台申请结算
	@SuppressWarnings("unchecked")
	public void updateOrdersState() throws IOException{
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		//定义一个jo用于异步传输信息
		JSONObject jo = new JSONObject();
//		判断操作人 今天是否已经结算过
		//计算日期
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = fm.format(new Date());
		String startTime = dateTime+" 00:00:00";
		String endTime = dateTime+" 23:59:59";
		Integer count = shopSettlementDetailService.getCount(" where UNIX_TIMESTAMP(o.createDate) >=UNIX_TIMESTAMP('"+startTime+"') and UNIX_TIMESTAMP(o.createDate)<=UNIX_TIMESTAMP('"+endTime+"') and o.shopInfoId="+shopInfo.getShopInfoId());
		if(count>0){//大于0说明今天已申请过结算 提示一条信息 无法申请结算
			jo.accumulate("apply", false);//申请结算的结果
		}else{//可以申请  
			//申请
			//佣金比例 ，从shopInfo中读取
			String commission = shopInfo.getCommission();
			String commissionProportionSYS = String.valueOf(fileUrlConfig.get("commissionProportion"));
			BigDecimal commissionProportion = new BigDecimal(commissionProportionSYS); 
			if(commission != null){
				commissionProportion = new BigDecimal(commission); 
			}
			List<Orders> ordersList = ordersService.findObjects(null, "where o.ordersId in ("+ids+")");
			BigDecimal sum=new BigDecimal(0);
			for(Orders orders:ordersList){
				orders.setSettlementStatusForSellers(1);//申请结算
				ordersService.saveOrUpdateObject(orders);
				if(orders.getFinalAmount()!=null){
					sum = sum.add(orders.getFinalAmount());
				}
			}
			ShopSettlementDetail shopSettlementDetail = new ShopSettlementDetail();
			shopSettlementDetail.setShopInfoId(shopInfo.getShopInfoId());
			shopSettlementDetail.setShopName(shopInfo.getShopName());
			shopSettlementDetail.setCustomerId(shopInfo.getCustomerId());
			shopSettlementDetail.setCreateDate(new Date());
			shopSettlementDetail.setCompanyName(shopInfo.getCompanyName());
			shopSettlementDetail.setTotalCost(sum);
			shopSettlementDetail.setStatus(1);
			shopSettlementDetail.setOrdersIds(ids);
			shopSettlementDetail.setCommissionProportion(commissionProportion);
			Object saveOrUpdateObject = shopSettlementDetailService.saveOrUpdateObject(shopSettlementDetail);
			if(saveOrUpdateObject!=null){
				jo.accumulate("apply", true);//申请结算的结果
			}else{
				jo.accumulate("apply", false);//申请结算的结果
			}
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//卖家查看申请结算列表
	public String shopApplyOrderList(){
		return SUCCESS;
	}
	//查看店铺未结算订单列表
	public String shopNoPayOrderList() throws ParseException{
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		if(null !=shopInfo && shopInfo.getShopInfoId()!=null){
			StringBuffer coutHql = new StringBuffer("select count(a.ordersId) from Orders a,ShopInfo b ,OrdersOPLog c WHERE b.shopInfoId="+shopInfo.getShopInfoId()+" and a.settlementStatusForSellers=0 and a.settlementStatus=1 and a.shopInfoId=b.shopInfoId and c.ordersId=a.ordersId and  (c.ordersOperateState=5 or c.ordersOperateState=9) ");
			String hql="SELECT a.ordersId as ordersId,c.operatorTime as operatorTime ,a.ordersNo as ordersNo,a.createTime as createTime,a.finalAmount as finalAmount,a.ordersState as ordersState FROM "
					+ "Orders a,ShopInfo b ,OrdersOPLog c WHERE b.shopInfoId="+shopInfo.getShopInfoId()+" and a.settlementStatusForSellers=0 and a.settlementStatus=1 and  a.shopInfoId=b.shopInfoId and c.ordersId=a.ordersId and (c.ordersOperateState=5 or c.ordersOperateState=9) ";
			
			//计算查询日期 提前15天订单
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar cd = Calendar.getInstance();
			cd.setTime(new Date());
			cd.add(Calendar.DATE,-15);
			String nowDate =fm.format(cd.getTime());
			nowDate = nowDate.substring(0, 10)+" 23:59:59";
			
			if(Utils.objectIsNotEmpty(startTime)){
				hql += " and UNIX_TIMESTAMP(c.operatorTime) >= UNIX_TIMESTAMP('"+startTime+"')";
				coutHql.append(" and UNIX_TIMESTAMP(c.operatorTime) >= UNIX_TIMESTAMP('"+startTime+"')");
			}
			if(Utils.objectIsNotEmpty(endTime)){
				hql += " and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(c.operatorTime)";
				coutHql.append(" and UNIX_TIMESTAMP('"+endTime+"') >= UNIX_TIMESTAMP(c.operatorTime)");
			}else{
				hql += " and UNIX_TIMESTAMP('"+nowDate+"') >= UNIX_TIMESTAMP(c.operatorTime)";
				coutHql.append(" and UNIX_TIMESTAMP('"+nowDate+"') >= UNIX_TIMESTAMP(c.operatorTime)");
			}
			int totalRecordCount = shopInfoService.getMultilistCount(coutHql.toString());
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			productInfoList=shopInfoService.findListMapPage(hql.toString()+" group by a.ordersId",pageHelper);//初始化未付款订单
		}
		return SUCCESS;
	}
	//查看店铺订单列表(根据店铺的Id，设定为1)
	public String shopOrderList(){
		try{
			//获取省
			provinceList=areaService.findObjects(" where o.parentId=0");
			ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
			if(null !=shopInfo && shopInfo.getShopInfoId()!=null){
				StringBuffer coutHql = new StringBuffer("SELECT count(a.ordersId) FROM Orders a,ShopInfo b  WHERE a.shopInfoId=b.shopInfoId and b.shopInfoId="+shopInfo.getShopInfoId());
				String hql="SELECT a.customerId as customerId, a.ordersId as ordersId, a.ordersNo as ordersNo,a.createTime as createTime,c.loginName as customerName,a.finalAmount as finalAmount," +
						" a.sendType as sendType ,a.ordersState as ordersState , a.sonaccountId as sonaccountId ,a.settlementStatus as settlementStatus,c.type as type FROM Orders a,ShopInfo b,Customer c  WHERE a.shopInfoId=b.shopInfoId and a.customerId=c.customerId and b.shopInfoId="+shopInfo.getShopInfoId();
				if(orders!=null && orders.getOrdersState()!=null){//根据状态查找订单
					if(orders.getOrdersState()==0||orders.getOrdersState()==1){
						coutHql.append(" and a.settlementStatus="+orders.getOrdersState()+" and a.ordersState=0");
						hql+=" and a.settlementStatus="+orders.getOrdersState()+" and a.ordersState=0";
					}else if(orders.getOrdersState()==99){
						coutHql.append(" and a.ordersState=0");
						hql+=" and a.ordersState=0";
					}else{
						coutHql.append(" and a.ordersState="+orders.getOrdersState());
						hql+=" and a.ordersState="+orders.getOrdersState();
					}
				 }
				if(orders!=null && StringUtils.isNotEmpty(orders.getOrdersNo())){
					 coutHql.append(" and a.ordersNo='"+orders.getOrdersNo().trim()+"' or a.totalOrdersNo='"+orders.getOrdersNo().trim()+"'");
					 hql+=" and a.ordersNo='"+orders.getOrdersNo().trim()+"' or a.totalOrdersNo='"+orders.getOrdersNo().trim()+"'";
				 }
				if(!StringUtils.isEmpty(params)){//根据条件排序订单
					hql+=" order by a."+params+" desc";
				}else{//默认排序
					params="createTime";
					hql+=" order by a.createTime desc";
				}
				int totalRecordCount = shopInfoService.getMultilistCount(coutHql.toString());
				pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
				productInfoList=shopInfoService.findListMapPage(hql.toString(),pageHelper);//初始化全部订单列表
//				String hql1="SELECT a.ordersNo as ordersNo,a.createTime as createTime,b.customerName as customerName,a.finalAmount as finalAmount,a.ordersState as ordersState FROM Orders a,ShopInfo b  WHERE b.shopInfoId="+shopInfoId+" AND a.ordersState=1 and  a.shopInfoId=b.shopInfoId";
//				productInfoLists=shopInfoService.findListMapByHql(hql1);//初始化未付款订单
				this.logisticsList = logisticsService.findObjects(null, " where 1=1 order by o.logisticsId desc");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//根据订单号查看一条记录
	public String getOrdersById(){
		//获取省
		provinceList=areaService.findObjects(" where o.parentId=0");
		ShopInfo shopInfo=(ShopInfo) session.getAttribute("shopInfo");
		if(null !=shopInfo && shopInfo.getShopInfoId()!=null && orders!=null && orders.getOrdersNo()!=null ){
			String hql="SELECT a.customerId as customerId, a.ordersId as ordersId,a.ordersNo as ordersNo,a.createTime as createTime,c.loginName as customerName,a.finalAmount as finalAmount,a.ordersState as ordersState FROM Orders a,ShopInfo b,Customer c  WHERE a.shopInfoId=b.shopInfoId and c.customerId=a.customerId and  b.shopInfoId="+shopInfo.getShopInfoId()+" AND a.ordersNo='"+orders.getOrdersNo().trim()+"'";
			productInfoList=shopInfoService.findListMapByHql(hql);
		}
		return SUCCESS;
	}
	/**
	 * 店铺订单详情
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String shopOrderInfo(){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		if(orders!=null && orders.getOrdersId()!=null &&shopInfo!=null){
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersId="+orders.getOrdersId());
			Customer customerForOrdersInfo = (Customer) customerService.getObjectByParams(" where o.customerId="+orders.getCustomerId());
			request.setAttribute("customerForOrdersInfo", customerForOrdersInfo);
			//商品信息
			String hql = "select a.productId as productId, a.productCode as productCode, a.count as count ,a.productFullName as productFullName,a.salesPrice as salesPrice,a.marketPrice as marketPrice,a.logoImage as logoImg,a.memberPrice as memberPrice," +
					"a.freightPrice as freightPrice,a.virtualCoinNumber as virtualCoinNumber,a.virtualCoin as virtualCoin,a.brandName as brandName,a.brandId as brandId ,a.shopInfoId as shopInfoId,a.shopName as shopName,a.stockUpDate as stockUpDate, b.storeNumber as storeNumber" +
					" from OrdersList a, ProductInfo b where  a.shopInfoId = "+shopInfo.getShopInfoId()+" and a.ordersId="+orders.getOrdersId()+" and a.productId = b.productId";
			listProduct=productInfoService.findListMapByHql(hql);
			List<Map> list = productInfoService.findListMapByHql("select a.productId as productId, sum(a.salesPrice) as totalSalesPrice,sum(a.marketPrice) as totalMarketPrice  , sum(a.count) as totalCount from OrdersList a  where a.ordersId="+orders.getOrdersId()+" group by a.productId");
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
				discount = discount.add(oldDiscount);
			}
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
			//查看物流公司和物流号
			if(orders.getOrdersState()==4||orders.getOrdersState()==5 || orders.getOrdersState()==9){
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
						JSONObject json = JSONObject.fromObject(express);
						cityCourierMap = (Map<String, Object>) JSONObject.toBean(json, Map.class);
					}
				}
			}
			//是否使用优惠券，使用的话查找优惠券信息
			if(orders.getIsUseCoupon()!=null && orders.getIsUseCoupon()==1 && orders.getDiscountCouponId()!=null){//1使用，0不使用
				custHaveCou = (CustomerHaveCoupon) customerHaveCouponService.getObjectByParams("where o.discountCouponID="+orders.getDiscountCouponId()+" and o.customerId="+orders.getCustomerId()+" and o.shopInfoId="+orders.getShopInfoId());
			}
		}
		return SUCCESS;
	}
	/**
	 * 店铺订单信息打印
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String shopOrderPrint(){
		
		//取得当前店铺信息的对象
		shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		//如果订单实体不为空，且有订单ID，且店铺信息对象也不为空，可进行下一步操作
		if(orders!=null && orders.getOrdersId()!=null &&shopInfo!=null){
			//根据订单ID查询订单
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersId="+orders.getOrdersId());
			//根据订单中的购买人ID查询购买人信息
			customer = (Customer) customerService.getObjectByParams(" where o.customerId="+orders.getCustomerId());
			//商品信息
			String hql = "select a.productId as productId, a.productCode as productCode, a.count as count ,a.productFullName as productFullName,a.salesPrice as salesPrice,a.logoImage as logoImg,a.memberPrice as memberPrice," +
					"a.freightPrice as freightPrice,a.brandName as brandName,a.brandId as brandId ,a.shopInfoId as shopInfoId,a.shopName as shopName,a.stockUpDate as stockUpDate, b.storeNumber as storeNumber" +
					" from OrdersList a, ProductInfo b where  a.shopInfoId = "+shopInfo.getShopInfoId()+" and a.ordersId="+orders.getOrdersId()+" and a.productId = b.productId";
			//得到商品list，根据当前登录店铺及订单中的商品ID查询商品（查询当前店铺下的指定订单中的商品）
			listProduct=productInfoService.findListMapByHql(hql);
			//根据订单ID查询订单List
			List<Map> list = productInfoService.findListMapByHql("select a.productId as productId, sum(a.salesPrice) as totalSalesPrice,sum(a.marketPrice) as totalMarketPrice  , sum(a.count) as totalCount from OrdersList a  where a.ordersId="+orders.getOrdersId()+" group by a.productId");
			orderList = ordersListService.findObjects(" where o.ordersId="+orders.getOrdersId());
			//销售价格与市场价格的差价
			BigDecimal oldDiscount = new BigDecimal(0);
			//订单优惠总金额
			discount=new BigDecimal(0);
			//订单中的总销售数量
			Integer oldTotalCount=0;
			
			for(Map map:list){
				//销售价格
				BigDecimal totalSalesPrice = new BigDecimal(0);
				//市场价格
				BigDecimal totalMarketPrice = new BigDecimal (0);
				
				if(null!=map.get("totalSalesPrice")&&null!=map.get("totalMarketPrice")){
					totalSalesPrice = new BigDecimal (String.valueOf(map.get("totalSalesPrice")));
					totalMarketPrice = new BigDecimal (String.valueOf(map.get("totalMarketPrice")));
					//销售价格与市场价格的差价
					oldDiscount=totalSalesPrice.subtract(totalMarketPrice);//减法
				}else{
					oldDiscount=new BigDecimal(0);
				}
				if(null!=map.get("totalCount")){
					//商品总数量
					oldTotalCount = Integer.parseInt(String.valueOf(map.get("totalCount")));
				}else{
					oldTotalCount = 0;
				}
				if(totalCount!=null){
					//销售数量叠加
					totalCount=oldTotalCount+totalCount;
				}else{
					totalCount=oldTotalCount;
				}
				oldDiscount = oldDiscount.multiply(new BigDecimal(oldTotalCount));//乘法，数量*差价=优惠价
				discount = discount.add(oldDiscount);//订单优惠总金额
			}
			OrdersOPLog ordersOPLog  = (OrdersOPLog) ordersOPLogService.getObjectByParams( "where o.ordersId="+orders.getOrdersId()+"and o.ordersOperateState = 2");
			request.setAttribute("ordersOPLog", ordersOPLog);
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
			//查看物流公司和物流号
			if(orders.getOrdersState()==4||orders.getOrdersState()==5 || orders.getOrdersState()==9){
				shipping = (Shipping) shippingService.getObjectByParams(" where o.ordersId="+orders.getOrdersId());
				//如果快递信息不为空则取出
//				if(Utils.objectIsNotEmpty(shipping)&&Utils.objectIsNotEmpty(shipping.getExpressInfo())){
//					Result result = JacksonHelper.fromJSON(shipping.getExpressInfo(),Result.class);
//					resultItemList = result.getData();
//				}
			}
			//是否使用优惠券，使用的话查找优惠券信息
			if(orders.getIsUseCoupon()!=null && orders.getIsUseCoupon()==1 && orders.getDiscountCouponId()!=null){//1使用，0不使用
				custHaveCou = (CustomerHaveCoupon) customerHaveCouponService.getObjectByParams("where o.discountCouponID="+orders.getDiscountCouponId()+" and o.customerId="+orders.getCustomerId()+" and o.shopInfoId="+orders.getShopInfoId());
			}
		}
		return SUCCESS;
	}
	/**
	 * 更改订单正在配送状态
	 * @return
	 */
	public String changgeOrdersState(){
		String loginHome = request.getParameter("loginHome");
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(shopInfo!=null&&customer!=null && orders!=null && orders.getOrdersId()!=null){
			orders =shopOrdersService.saveOrUpdateChanggeOrdersState(orders, customer,shopInfo,son);
		}
		if(Utils.objectIsNotEmpty(loginHome)){
			return "loginHome";
		}else{
			return SUCCESS;
		}
	}
	/**
	 * 更改订单已发货状态
	 * @return
	 */
	public String changeShipments(){
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount) session.getAttribute("sonaccount");
		if(logisticsType=="1"||"1".equals(logisticsType)){
			if(shopInfo!=null&&customer!=null && orders!=null && orders.getOrdersId()!=null && shipping!=null ){
				Logistics logistics = (Logistics) logisticsService.getObjectByParams("where o.code='"+shipping.getCode()+"'");
				shipping.setDeliveryUrl(logistics.getDeliveryUrl());
				shipping.setDeliveryCorpName(logistics.getDeliveryCorpName());
				orders=shopOrdersService.saveOrUpdateChangeShipments(orders, shipping,customer,shopInfo,son);
				try {
					TaskRequest req = new TaskRequest();
					//本网站所在路径
					String callbackurl =  (String) fileUrlConfig.get("express100_callbackurl");
					req.getParameters().put("callbackurl",callbackurl);//回调地址
					//与快递公司签约key
					String key = (String) fileUrlConfig.get("express100_key");
					req.setKey(key);//与快递公司签约key
					req.setTo(orders.getAddress());//收货人地址
					req.setCompany(shipping.getCode());//快递公司
					req.setNumber(shipping.getDeliverySn());//快递公司代号
					
					HashMap<String, String> p = new HashMap<String, String>(); 
					p.put("schema", "json");
					p.put("param", JacksonHelper.toJSON(req));
					//System.out.println("发送信息："+JacksonHelper.toJSON(req));
					String ret;
					//请求快递100时的编码
					String express100Encoding =  (String) fileUrlConfig.get("express100_encoding");
					//订阅快递100时的路径
					String express100Path =  (String) fileUrlConfig.get("express100_path");
					ret = HttpRequest.postData(express100Path, p, express100Encoding);
					//重新订阅时的时间，单位秒
					int time = Integer.parseInt((String)fileUrlConfig.get("express100_time"));
					//请求失败默认重新请求次数
					int number = Integer.parseInt((String)fileUrlConfig.get("express100_number"));
					//请求订阅快递100
					Express100Thred express100Thred = new Express100Thred(ret,time,number);
					Thread express100ThredStart = new Thread(express100Thred);
					express100ThredStart.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			if(shopInfo!=null&&customer!=null && orders!=null && orders.getOrdersId()!=null && cityCourier!=null ){
				CityCourier cityCourierTemp = (CityCourier) cityCourierService.getObjectByParams("where o.cityCourierId='"+cityCourier.getCityCourierId()+"'");
				orders=shopOrdersService.saveOrUpdateChangeShipments(orders, cityCourierTemp,customer,shopInfo,son);
			}
		}
		return SUCCESS;
	}
	/**
	 * 店铺查看评价商品
	 * @return
	 */
	public String shopEvaluationList(){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		Customer customer = (Customer)session.getAttribute("customer");
		if(null!=customer&& null!=shopInfo){
			StringBuffer hql = new StringBuffer();
			String countHql="select count(a.evaluateId) from EvaluateGoods a ,Orders b  where a.ordersId=b.ordersId  and a.shopInfoId="+shopInfo.getShopInfoId();
			hql.append("select a.isAnonymous as isAnonymous,a.evaluateId as evaluateId,a.appraiserName as appraiserName,"
					+ " a.level as level,a.content as content ,a.createTime as createTime,a.productFullName as productFullName,"
					+ " a.productId as productId,b.ordersId as ordersId,b.ordersNo as ordersNo,"
					+ " a.logoImg as logoImg from EvaluateGoods a ,Orders b  where a.ordersId=b.ordersId "
					+ " and a.shopInfoId="+shopInfo.getShopInfoId());
			if(evaluateGoods!=null && evaluateGoods.getLevel()!=null){
				hql.append(" and a.level="+ evaluateGoods.getLevel());
				countHql+=" and a.level="+ evaluateGoods.getLevel();
			}
			int totalRecordCount = evaluateGoodsService.getMultilistCount(countHql);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			evaluateGoodsMap = evaluateGoodsService.findListMapPage(hql.toString()+" order by a.createTime desc", pageHelper);
		}
		return SUCCESS;
	}
	/**
	 * 订单列表中的导出
	 * @throws IOException 
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", })
	public void importExcelDP() throws Exception{
		ShopInfo shopInfo = (ShopInfo)session.getAttribute("shopInfo");
		List<Map> list = null;
		String hql= null;
		if(shopInfo!=null){
			hql="select o.ordersNo as ordersNo, o.createTime as createTime, o.ordersState as ordersState, o.settlementStatus as settlementStatus, "
					+ " o.consignee as consignee, b.deliveryAddress as deliveryAddress,o.ordersId as ordersId, o.sendType as sendType, d.sku as sku, b.productCode as productCode,"
					+ " b.barCode as barCode,d.marketPrice as marketPrice, d.salesPrice as salesPrice, d.count as count, o.totalAmount as totalAmount, o.freight as freight "
					+ " from Orders o, ProductInfo b, ShopInfo c, OrdersList d  "
					+ " where o.shopInfoId ="+shopInfo.getShopInfoId()+" and o.ordersId in("+ids+") and o.ordersId = d.ordersId and o.shopInfoId = b.shopInfoId and b.shopInfoId = c.shopInfoId and d.productId = b.productId";
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
					String marketPrice="";
					String salesPrice="";
					String memberPrice="";
					String count="";
					List<Map> ordersListMap = ordersListService.findListMapByHql("select o.sku as sku ,o.productFullName as productFullName,o.marketPrice as marketPrice,o.count as count,o.salesPrice as salesPrice,o.productCode as productCode,o.barCode as barCode ,o.memberPrice as memberPrice from OrdersList o where o.ordersNo='"+ordersNo+"'");
					if(ordersListMap!=null&&ordersListMap.size()>0){
						if(ordersListMap.size()==1){
							sku = String.valueOf(ordersListMap.get(0).get("sku"));
							productCode = String.valueOf(ordersListMap.get(0).get("productCode"));
							barCode = String.valueOf(ordersListMap.get(0).get("barCode"));
							productFullName = String.valueOf(ordersListMap.get(0).get("productFullName"));
							marketPrice = String.valueOf(ordersListMap.get(0).get("marketPrice"));
							salesPrice = String.valueOf(ordersListMap.get(0).get("salesPrice"));
							memberPrice = String.valueOf(ordersListMap.get(0).get("memberPrice"));
							count = String.valueOf(ordersListMap.get(0).get("count"));
						}else{
							for(Map p:ordersListMap){
								sku += String.valueOf(p.get("sku"))+"\n";
								productCode += String.valueOf(p.get("productCode"))+"\n";
								barCode += String.valueOf(p.get("barCode"))+"\n";
								productFullName += String.valueOf(p.get("productFullName"))+"\n";
								marketPrice += String.valueOf(p.get("marketPrice"))+"\n";
								salesPrice += String.valueOf(p.get("salesPrice"))+"\n";
								memberPrice += String.valueOf(p.get("memberPrice"))+"\n";
								count += String.valueOf(p.get("count"))+"\n";
							}
						}
					}
					m.put("sku", sku);
					m.put("productCode", productCode);
					m.put("barCode", barCode);
					m.put("productFullName", productFullName);
					m.put("marketPrice", marketPrice);
					m.put("salesPrice", salesPrice);
					m.put("memberPrice", memberPrice);
					m.put("count", count);
					//查询订单物流信息
					Shipping shipping = (Shipping) shippingService.getObjectByParams(" where o.ordersId="+String.valueOf(m.get("ordersId")));
					if(shipping!=null){
						m.put("shipping", shipping.getDeliveryCorpName());//向map中添加物流公司名称
					}else{
						m.put("shipping", "");//向map中添加物流公司名称
					}
				}
			}
		}
		Boolean b = madeExl(list);
		//相对路径[服务器]
		String filePathXLS ="excel/DianPu_"+shopInfo.getCustomerName().substring(0, shopInfo.getCustomerName().lastIndexOf("_"))+".xls";
		//文件名
		String downloadFileName = "DianPu_"+shopInfo.getCustomerName().substring(0, shopInfo.getCustomerName().lastIndexOf("_"))+".xls";
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
	 * 店铺订单列表导出:先保存在服务器,在下载
	 * @param list   数据集合
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 */
	@SuppressWarnings("rawtypes")
	public boolean madeExl(List<Map> list)throws IOException, RowsExceededException{
		ShopInfo shopInfo = (ShopInfo)session.getAttribute("shopInfo");
		boolean b=true;
		try {
			String url =(String) fileUrlConfig.get("fileUploadRoot");
			File file = new File(url+"/excel");
			file.mkdirs();
			File toFile = new File(url+"/excel/DianPu_"+shopInfo.getCustomerName().substring(0, shopInfo.getCustomerName().lastIndexOf("_"))+".xls");
			if(!toFile.exists()){ 
				toFile.createNewFile();}// 创建目标文件
			WritableWorkbook wbook = Workbook.createWorkbook(toFile);
			// 创建一个工作表 第一个工作区
			WritableSheet wsheet = wbook.createSheet("SHOPJSP:店铺订单列表", 0);
			WritableFont wf3 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);//设置字体
	        WritableCellFormat wcfmt3 = new WritableCellFormat(wf3);
	        wcfmt3.setWrap(true);// 自动换行
	        wcfmt3.setVerticalAlignment(VerticalAlignment.TOP);//顶端对齐
	        wcfmt3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //设置边框
	        wcfmt3.setAlignment(jxl.format.Alignment.CENTRE);
			biaoge(wsheet);
			int i=1;//拿list中的数据[因为有大表头,所以从2开始生成数据]
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
					Label buyerName = new Label(5, i+1, String.valueOf(m.get("consignee")), wcfmt3);//6收货人
					wsheet.addCell(buyerName);
					//取商品的发货地址
					 BasicArea adress=(BasicArea)areaService.getObjectById(m.get("deliveryAddressCities")+"");
					 Label deliveryAddress = new Label(6, i+1, adress.getFullName(), wcfmt3);
					 wsheet.addCell(deliveryAddress);
					//订单
					Label sendType;
					if(StringUtils.isNotEmpty(String.valueOf(m.get("shipping")))){
						sendType = new Label(7, i+1, String.valueOf(m.get("shipping")), wcfmt3);
					}else{
						sendType = new Label(7, i+1, "", wcfmt3);
					}
					wsheet.addCell(sendType);
					Label sku = new Label(8, i+1, String.valueOf(m.get("sku")), wcfmt3);//10订货号
					wsheet.addCell(sku);
					Label productCode = new Label(9, i+1, String.valueOf(m.get("productCode")), wcfmt3);//
					wsheet.addCell(productCode);
					Label barCode = new Label(10, i+1, String.valueOf(m.get("barCode")), wcfmt3);
					wsheet.addCell(barCode);
					Label productName = new Label(11, i+1, String.valueOf(m.get("productFullName")), wcfmt3);
					wsheet.addCell(productName);
					Label marketPrice = new Label(12, i+1, String.valueOf(m.get("marketPrice")), wcfmt3);
					wsheet.addCell(marketPrice);
					Label salesPrice = new Label(13, i+1, String.valueOf(m.get("salesPrice")), wcfmt3);
					wsheet.addCell(salesPrice);
					Label memberPrice = new Label(14, i+1, String.valueOf(m.get("memberPrice")), wcfmt3);
					wsheet.addCell(memberPrice);
					Label count = new Label(15, i+1, String.valueOf(m.get("count")), wcfmt3);
					wsheet.addCell(count);
					Label totalAmount = new Label(16, i+1, String.valueOf(m.get("totalAmount")), wcfmt3);
					wsheet.addCell(totalAmount);
					Label freight = new Label(17, i+1, String.valueOf(m.get("freight")), wcfmt3);
					wsheet.addCell(freight);
					i++;
				}
			}else{
				Label cwqd = new Label(1, i+1, "错误清单", wcfmt3);//错误
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
	 * 设置表格样式
	 * @return
	 * @throws WriteException 
	 */
	public void biaoge(WritableSheet wsheet) throws WriteException{
		//设置 行、 列的 宽度 、高度
		wsheet.setColumnView(0, 5); // 设置列的宽度---序号
		wsheet.setColumnView(1, 28); // 设置列的宽度---订单编号
		wsheet.setColumnView(2, 20); // 设置列的宽度---下单时间
		wsheet.setColumnView(3, 12); // 设置列的宽度---订单状态
		wsheet.setColumnView(4, 12); // 设置列的宽度---付款状态
		wsheet.setColumnView(5, 12); // 设置列的宽度---发货人
		wsheet.setColumnView(6, 30); // 设置列的宽度---发货地点
		wsheet.setColumnView(7, 15); // 设置列的宽度---物流提供商
		wsheet.setColumnView(8, 15); // 设置列的宽度---订货号
		wsheet.setColumnView(9, 20); // 设置列的宽度---商品编码
		wsheet.setColumnView(10, 20); // 设置列的宽度---商品条码
		wsheet.setColumnView(11,50); // 设置列的宽度---商品名称
		wsheet.setColumnView(12,15); // 设置列的宽度---单价
		wsheet.setColumnView(13,15); // 设置列的宽度---结算单价
		wsheet.setColumnView(14,15); // 设置列的宽度---结算单价
		wsheet.setColumnView(15,12); // 设置列的宽度---数量
		wsheet.setColumnView(16,15); // 设置列的宽度---金额小计
		wsheet.setColumnView(17,15); // 设置列的宽度---订单运费
		//让 第一列居中
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 20, WritableFont.NO_BOLD, false);
        WritableCellFormat wcfmt = new WritableCellFormat(wf);
        wcfmt.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //给title设置边框
        wcfmt.setAlignment(jxl.format.Alignment.CENTRE);
        wcfmt.setBackground(jxl.format.Colour.BLACK); // 设置单元格的背景颜色
        Label lable1 = new Label(0,0,"SHOPJSP:企业订单列表",wcfmt);//添加 Lable对象  (列、行)
        wsheet.addCell(lable1);
		wsheet.mergeCells(0, 0, 17, 0);//合并单元格（列、行）
		 //列头
		WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);
        WritableCellFormat wcfmt2 = new WritableCellFormat(wf2);
        wcfmt2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); //给title设置边框
        wcfmt2.setAlignment(jxl.format.Alignment.CENTRE);
        wcfmt2.setBackground(jxl.format.Colour.GRAY_25);// 设置单元格的背景颜色
		String[] title = {"序号","订单编号", "下单时间","订单状态", "付款状态", "发货人", "发货地点","物流提供商","订货号", "商品编码", "商品条码", ".商品名称","原价","售价","成交价格","数量", "金额小计", "订单运费"};
		for (int m = 0; m < title.length; m++) {		// 设置表头
			Label excelTitle = new Label(m, 1, title[m], wcfmt2);// 一列列的打印表头 按照我们规定的格式
			wsheet.addCell(excelTitle);// 把标头加到我们的工作区
		}
	}
	//获取市
	public void findCityList() throws IOException{
		cityList=areaService.findObjects(" where o.parentId="+areaId);
		JSONArray jo = JSONArray.fromObject(cityList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getProductInfoList() {
		return productInfoList;
	}
	@SuppressWarnings("rawtypes")
	public void setProductInfoList(List<Map> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getProductInfoLists() {
		return productInfoLists;
	}
	@SuppressWarnings("rawtypes")
	public void setProductInfoLists(List<Map> productInfoLists) {
		this.productInfoLists = productInfoLists;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public void setShopOrdersService(IShopOrdersService shopOrdersService) {
		this.shopOrdersService = shopOrdersService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getListProduct() {
		return listProduct;
	}
	@SuppressWarnings("rawtypes")
	public void setListProduct(List<Map> listProduct) {
		this.listProduct = listProduct;
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
	public OrderDetailTime getOrderDetailTime() {
		return orderDetailTime;
	}
	public void setOrderDetailTime(OrderDetailTime orderDetailTime) {
		this.orderDetailTime = orderDetailTime;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
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
	@SuppressWarnings("rawtypes")
	public List<Map> getEvaluateGoodsMap() {
		return evaluateGoodsMap;
	}
	@SuppressWarnings("rawtypes")
	public void setEvaluateGoodsMap(List<Map> evaluateGoodsMap) {
		this.evaluateGoodsMap = evaluateGoodsMap;
	}
	public EvaluateGoods getEvaluateGoods() {
		return evaluateGoods;
	}
	public void setEvaluateGoods(EvaluateGoods evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setShopSettlementDetailService(
			IShopSettlementDetailService shopSettlementDetailService) {
		this.shopSettlementDetailService = shopSettlementDetailService;
	}
	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getOrdersList() {
		return ordersList;
	}
	@SuppressWarnings("rawtypes")
	public void setOrdersList(List<Map> ordersList) {
		this.ordersList = ordersList;
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
	public List<Logistics> getLogisticsList() {
		return logisticsList;
	}
	public void setLogisticsList(List<Logistics> logisticsList) {
		this.logisticsList = logisticsList;
	}
	public void setLogisticsService(ILogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public List<OrdersList> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrdersList> orderList) {
		this.orderList = orderList;
	}
	public CityCourier getCityCourier() {
		return cityCourier;
	}
	public void setCityCourier(CityCourier cityCourier) {
		this.cityCourier = cityCourier;
	}
	public String getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}
	public void setCityCourierService(ICityCourierService cityCourierService) {
		this.cityCourierService = cityCourierService;
	}
	public Map<String, Object> getCityCourierMap() {
		return cityCourierMap;
	}
	public void setCityCourierMap(Map<String, Object> cityCourierMap) {
		this.cityCourierMap = cityCourierMap;
	}
	public List<BasicArea> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<BasicArea> provinceList) {
		this.provinceList = provinceList;
	}
	public List<BasicArea> getCityList() {
		return cityList;
	}
	public void setCityList(List<BasicArea> cityList) {
		this.cityList = cityList;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
