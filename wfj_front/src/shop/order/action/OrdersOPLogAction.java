package shop.order.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Sonaccount;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersOPLog;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import basic.pojo.Users;
import basic.service.IKeyBookService;
/**
 * 订单操作日志action操作类
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class OrdersOPLogAction extends BaseAction {
	private IOrdersOPLogService ordersOPLogService;
	private IOrdersService ordersService;
	private IKeyBookService keyBookService;
	private List<Orders> ordersList;
	private List<OrdersOPLog> ordersOPLogList;
	private OrdersOPLog ordersOPLog;
	private String ordersOPLogId;
	private String ids;
	private String params;
	/**订单号**/
	private String ordersNo;
	//删除订单日志
	public void deleteOrdersOPLog(){
		ordersOPLogService.deleteObjectsByIds("ordersOPLogId", ids);
	}
	//获得订单日志对象
	@SuppressWarnings("unchecked")
	public void getOrdersOPLogObject() throws IOException{
		List<OrdersOPLog> ordersOPLogList = ordersOPLogService.findObjects(" where o.ordersNo='"+ordersNo+"' and o.ordersOperateState<=7");
		JsonConfig jsonConfig = new JsonConfig();
		Map<String,Object> map = new HashMap<String,Object>();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		ordersOPLogList = JSONArray.fromObject(ordersOPLogList,jsonConfig);
		map.put("ordersOPLogList", ordersOPLogList);
		map.put("ordersNo", ordersNo);
		JSONObject jo = JSONObject.fromObject(map,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//保存或修改订单日志
	public void saveOrUpdateOrdersOPLog() throws ParseException{
		//Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		Users users = (Users) request.getSession().getAttribute("users");
		if(ordersOPLog != null){
			Orders orders = (Orders) ordersService.getObjectByParams(" where o.ordersId='"+ordersOPLog.getOrdersId()+"'");
			if(orders!=null){
				ordersOPLog.setOrdersNo(orders.getOrdersNo());
			}
			ordersOPLog.setOoperatorId(users.getUsersId());
			/*if(son!=null){
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName()+" : "+son.getNickname());//操作人name
			}else{
				ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			}*/
			ordersOPLog.setOperatorName(shopInfo2.getCompanyName());//操作人name
			ordersOPLog.setOoperatorSource("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			ordersOPLog.setOperatorTime(date);
			if(ordersOPLog.getOrdersMsg()!=null && !"".equals(ordersOPLog.getOrdersMsg())){
				ordersOPLog.setOrdersOperateState(2);
			}
			ordersOPLogService.saveOrUpdateObject(ordersOPLog);
		}
	}
	//订单日志初始化列表
	@SuppressWarnings({"rawtypes", "unchecked" })
	public void ordersOPLogList() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		String countHql="select count(a.ordersOPLogId) from OrdersOPLog a , Orders b where a.ordersId=b.ordersId";
		hqlsb.append(" select b.ordersNo as ordersNo,b.ordersState as ordersState,b.createTime as createTime,c.loginName as loginName from OrdersOPLog a , Orders b,Customer c where a.ordersId=b.ordersId and b.customerId=c.customerId ");
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String parameter = request.getParameter("title");
			if(StringUtils.isNotEmpty(parameter)){
				hqlsb.append(" and a.ordersNo like'%"+parameter+"%'");
				countHql+=" and a.ordersNo like'%"+parameter+"%'";
			}
		}
		int totalRecordCount = ordersOPLogService.getMoreTableCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> ordersOPLogList = ordersOPLogService.findListMapPage(hqlsb.toString(),pageHelper);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Map map:ordersOPLogList){
			map.put("createTime", formatter.format(map.get("createTime")));
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", ordersOPLogList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//订单操作日志初始化页面
	public String gotoOrdersOPLogPage(){
		return SUCCESS;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public List<Orders> getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}
	public List<OrdersOPLog> getOrdersOPLogList() {
		return ordersOPLogList;
	}
	public void setOrdersOPLogList(List<OrdersOPLog> ordersOPLogList) {
		this.ordersOPLogList = ordersOPLogList;
	}
	public OrdersOPLog getOrdersOPLog() {
		return ordersOPLog;
	}
	public void setOrdersOPLog(OrdersOPLog ordersOPLog) {
		this.ordersOPLog = ordersOPLog;
	}
	public String getOrdersOPLogId() {
		return ordersOPLogId;
	}
	public void setOrdersOPLogId(String ordersOPLogId) {
		this.ordersOPLogId = ordersOPLogId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setKeyBookService(IKeyBookService keyBookService) {
		this.keyBookService = keyBookService;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
}
