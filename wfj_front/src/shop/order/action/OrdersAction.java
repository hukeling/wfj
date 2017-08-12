package shop.order.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersOPLog;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
import basic.pojo.Users;
/**
 * 订单的action操作
 * @author 张攀攀、ss
 *
 */
@SuppressWarnings("serial")
public class OrdersAction extends BaseAction {
	private IOrdersService ordersService;//订单Service
	private IOrdersOPLogService ordersOPLogService;//订单操作日志Service
	private IOrdersListService ordersListService;//订单明细Service
	private ICustomerService customerService;//会员Service
	private Orders orders = new Orders();
	private List<Orders> ordersList = new ArrayList<Orders>();
	private String ids;
	private String ordersId;
	private String params;//查询条件
	private String orderState;//订单状态
	private String isLocked;//锁定状态
	/**订单号**/
	private String ordersNo ;
	/**商品Service**/
	private IProductInfoService productInfoService;
	/*********************end**********************************/
	/**
	 * 异步查询订单商品
	 * By 订单号
	 * **/
	@SuppressWarnings({ "unused", "rawtypes" })
	public void findOrdersProduct(){
		try {
			String hql ="select a.productId as productId,a.productFullName as productFullName,a.salesPrice as salesPrice," +
					"a.freightPrice as freightPrice ,a.logoImage as logoImg from OrdersList a where a.ordersNo='"+ordersNo+"'";
			List<Map> productMap = productInfoService.findListMapByHql(hql);
			JsonConfig jsonConfig = new JsonConfig();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ordersNo",ordersNo);
			map.put("productMap", productMap);
			map.put("visitFileUploadRoot", fileUrlConfig.get("visitFileUploadRoot"));
			JSONObject jo = JSONObject.fromObject(map);// 格式化result
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//订单列表初始化
	public String gotoOrdersPage() throws Exception {
		//查询所有用户
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	/**
	 * 保存或修改订单,订单号是订单生成时间加上6位随机数
	 * @throws Exception
	 */
	public void saveOrUpdateOrders() throws Exception {
		if(orders != null){
			if(orders.getOrdersId()==null || "".equals(orders.getOrdersId())){
				orders.setOrdersState(1);
				orders.setIsLocked(0);
			}
			Date createTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String createTimeStr = sdf.format(createTime);
			orders.setCreateTime(createTime);
			if("".equals(orders.getOrdersId())){
				orders.setOrdersId(null);
			}
//			if("".equals(orders.getUseRedbag())){
//				orders.setUseRedbag(null);
//			}
			Double random = Math.random();
			String num = random.toString().substring(2, 8);
			String ordersNo = createTimeStr+num;
			orders.setOrdersNo(ordersNo);
			orders = (Orders) ordersService.saveOrUpdateObject(orders);
			//添加操作日志
			Users users = (Users) request.getSession().getAttribute("users");
			OrdersOPLog ordersOPLog = new OrdersOPLog();
			ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
			ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
			ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
			//Customer customer = (Customer) session.getAttribute("customer");
			ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
			/*Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
			if(son!=null){
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
			}else{
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			}*/
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			ordersOPLog.setOoperatorSource("1");//操作人来源
			SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdff.format(createTime);
			Date date = sdf.parse(formatTime);
			ordersOPLog.setOperatorTime(date);
			ordersOPLog.setOrdersOperateState(10);
			ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时添加了订单："+orders.getOrdersNo());
			ordersOPLogService.saveOrUpdateObject(ordersOPLog);
		}
	}
	//得到订单对象
	public void getOrdersObject() throws Exception {
		orders = (Orders) ordersService.getObjectByParams(" where o.ordersId='"+ordersId+"'");
		Customer customer = (Customer) customerService.getObjectByParams("where o.customerId="+orders.getCustomerId());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("orders", orders);// total键 存放总记录数，必须的
		jsonMap.put("customer", customer);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除订单，同时删除其他有关该订单的记录
	public void deleteOrders() throws Exception {
		ordersService.deleteObjectsByIds("ordersId",ids);
		//删除订单操作日志表
		ordersOPLogService.deleteObjectsByIds("ordersId", ids);
		//删除订单明细表
		ordersListService.deleteObjectsByIds("ordersId", ids);
	}
	//初始化订单列表数据
	public void ordersList() throws Exception {
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String parameter = request.getParameter("title");
			if(parameter!=null&&!"".equals(parameter.trim())){
				StringBuffer sb=CreateWhereSQLForSelect.appendLike("ordersNo","like",parameter.trim());
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		if(orderState!=null && !"".equals(orderState)){
			if("true".equals(selectFlag)){
				String parameter = request.getParameter("title");
				if(parameter!=null&&!"".equals(parameter.trim())){
					StringBuffer sb=CreateWhereSQLForSelect.appendLike("ordersNo","like",parameter.trim());
					hqlsb=CreateWhereSQLForSelect.createSQL(sb);
					hqlsb.append(" and o.ordersState="+orderState);
				}else{
					hqlsb.append(" where o.ordersState="+orderState);
				}
			}else{
				hqlsb.append(" where o.ordersState="+orderState);
			}
		}
		if(isLocked!=null && !"".equals(isLocked)){
			if("true".equals(selectFlag)){
				String parameter = request.getParameter("title");
				if(parameter!=null&&!"".equals(parameter)){
					StringBuffer sb=CreateWhereSQLForSelect.appendLike("ordersNo","like",request.getParameter("title"));
					hqlsb=CreateWhereSQLForSelect.createSQL(sb);
					hqlsb.append(" and o.isLocked="+isLocked);
				}else{
					hqlsb.append(" where o.isLocked="+isLocked);
				}
			}else{
				hqlsb.append(" where o.isLocked="+isLocked);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" ordersId desc"));
		int totalRecordCount = ordersService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		ordersList=ordersService.findListByPageHelper(null, pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", ordersList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到未处理的订单列表页面
	public String gotoUntreatedOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到等待付款的订单列表页面
	public String gotoUnpaidOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到等待发货的订单列表页面
	public String gotoWaitShipmentsOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到已发货的订单列表页面
	public String gotoAlreadyShipmentsOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到确认收获的订单列表页面
	public String gotoValidationOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到归档订单列表页面
	public String gotoReceivingOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//跳转到作废订单列表页面
	public String gotoCancelOrdersPage(){
		List<Customer> customerList = customerService.findObjects(null);
		session.setAttribute("customerList", customerList);
		return SUCCESS;
	}
	//审核修改订单状态
	public void updateOrdersState() throws ParseException{
		Users users = (Users) request.getSession().getAttribute("users");
		String ordersNo = request.getParameter("h_rordersNo");
		String state = request.getParameter("state");
		String ordersRemark = request.getParameter("ordersRemark");
		state = state.substring(3, 4);
		String stateName="";
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(ordersNo!=null && !"".equals(ordersNo)){
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersNo='"+ordersNo+"'");
			if(ordersRemark!=null && !"".equals(ordersRemark)){
				orders.setOrdersRemark(ordersRemark);
				OrdersOPLog ordersOPLog = new OrdersOPLog();
				ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
				ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
				ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
				/*if(son!=null){
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
				}else{
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
				}*/
				ordersOPLog.setOperatorName(customer.getLoginName());//操作人name
				ordersOPLog.setOoperatorSource("1");//操作人来源
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formatTime = sdf.format(new Date());
				Date date = sdf.parse(formatTime);
				ordersOPLog.setOperatorTime(date);
				ordersOPLog.setOrdersOperateState(2);
				ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时对订单："+orders.getOrdersNo()+",添加了留言："+ordersRemark);
				ordersOPLogService.saveOrUpdateObject(ordersOPLog);
			}
			Integer sendType = orders.getSendType();
			if(sendType.intValue() == 1){
				if("2".equals(state)){
					orders.setOrdersState(3);
				}else{
					orders.setOrdersState(Integer.parseInt(state));
				}
			}else{
				orders.setOrdersState(Integer.parseInt(state));
			}
			orders.setIsLocked(0);
			ordersService.saveOrUpdateObject(orders);
			//添加操作日志
			OrdersOPLog ordersOPLog = new OrdersOPLog();
			ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
			ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
			ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
			/*if(son!=null){
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
			}else{
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			}*/
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			ordersOPLog.setOoperatorSource("1");//操作人来源
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			ordersOPLog.setOperatorTime(date);
			if("2".equals(state)){
				stateName = "订单确认";
				ordersOPLog.setOrdersOperateState(4);
			}else if("3".equals(state)){
				stateName = "收款确认";
				ordersOPLog.setOrdersOperateState(5);
			}else if("4".equals(state)){
				stateName = "正在配货";
				ordersOPLog.setOrdersOperateState(6);
			}else if("5".equals(state)){
				stateName = "发货确认";
				ordersOPLog.setOrdersOperateState(7);
			}else if("6".equals(state)){
				stateName = "收获确认";
				ordersOPLog.setOrdersOperateState(8);
			}else if("7".equals(state)){
				stateName = "订单作废";
				ordersOPLog.setOrdersOperateState(9);
			}
			ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时操作了订单："+orders.getOrdersNo()+","+stateName);
			ordersOPLogService.saveOrUpdateObject(ordersOPLog);
		}
	}
	//判断订单
	public void verdictOrders() throws IOException{
		String isLocked = request.getParameter("isLocked");
		Boolean isSuccess = true;
		if("1".equals(isLocked)){
			Users users = (Users) request.getSession().getAttribute("users");
			List<OrdersOPLog> ordersOPLogList = ordersOPLogService.findObjects(" where o.ordersId='"+ordersId+"' and o.ordersOperateState in(10,11) order by o.operatorTime desc ");
			if(ordersOPLogList != null){
				OrdersOPLog ordersOPLog = ordersOPLogList.get(0);
				Integer ooperatorId = ordersOPLog.getOoperatorId();
				Integer usersId = users.getUsersId();
				if(ooperatorId.intValue() != usersId.intValue()){
					isSuccess = false;
				}else{
					isSuccess = true;
				}
			}
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//锁定订单
	public void lockOrders() throws Exception{
		//Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(ordersId!=null && !"".equals(ordersId)){
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersId='"+ordersId+"'");
			if(orders.getIsLocked() == 0){
				orders.setIsLocked(1);
				ordersService.saveOrUpdateObject(orders);
				//添加操作日志
				Users users = (Users) request.getSession().getAttribute("users");
				OrdersOPLog ordersOPLog = new OrdersOPLog();
				ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
				ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
				ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
				/*if(son!=null){
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
				}else{
					ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
				}*/
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
				ordersOPLog.setOoperatorSource("1");//操作人来源
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formatTime = sdf.format(new Date());
				Date date = sdf.parse(formatTime);
				ordersOPLog.setOperatorTime(date);
				ordersOPLog.setOrdersOperateState(10);
				ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时锁定了订单："+orders.getOrdersNo());
				ordersOPLogService.saveOrUpdateObject(ordersOPLog);
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
	    jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(orders,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到所有被锁定的订单
	public String gotoCompelLockedPage(){
		return SUCCESS;
	}
	//解锁
	public void relieveLockedState() throws ParseException, IOException{
		Boolean isSuccess = true;
		String h_rordersNo = request.getParameter("h_rordersNo");
		//Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		if(h_rordersNo!=null && !"".equals(h_rordersNo)){
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersNo='"+h_rordersNo+"'");
			orders.setIsLocked(0);
			ordersService.saveOrUpdateObject(orders);
			//添加操作日志
			Users users = (Users) request.getSession().getAttribute("users");
			OrdersOPLog ordersOPLog = new OrdersOPLog();
			ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
			ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
			ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
			/*if(son!=null){
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
			}else{
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			}*/
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			ordersOPLog.setOoperatorSource("1");//操作人来源
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			ordersOPLog.setOperatorTime(date);
			ordersOPLog.setOrdersOperateState(11);
			ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时解除订单："+orders.getOrdersNo()+"的锁定");
			ordersOPLogService.saveOrUpdateObject(ordersOPLog);
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//修改订单价格
	public void updatePrice() throws IOException, ParseException{
		//Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		Boolean isSuccess = true;
		String ordersId = request.getParameter("ordersId");
		String disproductSumNumber = request.getParameter("disproductSumNumber");//新的总金额
		String dispayNumber = request.getParameter("dispayNumber");//新的应付金额
		String hisproductSumNumber = request.getParameter("hisproductSumNumber");//历史总金额
		String hispayNumber = request.getParameter("hispayNumber");//历史应付金额
		if(ordersId!=null && !"".equals(ordersId)){
			orders = (Orders) ordersService.getObjectByParams(" where o.ordersId='"+ordersId+"'");
			if(disproductSumNumber!=null && !"".equals(disproductSumNumber)){
//				orders.setProductSumNumber(Double.parseDouble(disproductSumNumber));
			}
			if(dispayNumber!=null && !"".equals(dispayNumber)){
//				orders.setPayNumber(Double.parseDouble(dispayNumber));
			}
			orders = (Orders) ordersService.saveOrUpdateObject(orders);
			//添加操作日志
			Users users = (Users) request.getSession().getAttribute("users");
			OrdersOPLog ordersOPLog = new OrdersOPLog();
			ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
			ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
			ordersOPLog.setOoperatorId(users.getUsersId());//操作人ID
			/*if(son!=null){
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
			}else{
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			}*/
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			ordersOPLog.setOoperatorSource("1");//操作人来源
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			ordersOPLog.setOperatorTime(date);
			ordersOPLog.setOrdersOperateState(3);
			ordersOPLog.setComments(users.getUserName()+"在"+formatTime+"时修改了订单："+orders.getOrdersNo()+"的价格，历史总金额和应付金额为："+hisproductSumNumber+"、"+hispayNumber+",修改后的总金额和应付金额为："+disproductSumNumber+"、"+dispayNumber);
			ordersOPLogService.saveOrUpdateObject(ordersOPLog);
			if(orders.getOrdersId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", isSuccess + "");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public List<Orders> getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
}
