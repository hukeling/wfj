package shop.order.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 订单明细的action操作
 * @author 张攀攀
 *
 */
@SuppressWarnings("serial")
public class OrdersListAction extends BaseAction {
	private IOrdersListService ordersListService;//订单明细Service
	private IOrdersService ordersService;//订单Service
	private IProductInfoService productInfoService;//商品信息Service
	private OrdersList ordersList;
	private List<OrdersList> ordersListList = new ArrayList<OrdersList>();//订单详情List
	private List<Orders> ordersLists = new ArrayList<Orders>();//订单List
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();//商品List 
	private String orderDetailId;
	private String ids;
	private String ordersListId;
	private String params;
	/**订单号**/
	private String ordersNo;
	/*******************************************end***************************************************/
	//删除订单明细
	public void deleteOrdersList(){
		ordersListService.deleteObjectsByIds("orderDetailId", ids);
	}
	//获得订单明细列表
	public void getOrderListObject() throws IOException{
		ordersListList =ordersListService.findObjects(" where o.ordersNo='"+ordersNo+"'");
		if(ordersListList!=null&&ordersListList.size()>0){
			for(OrdersList ol:ordersListList){
				ProductInfo pi = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+ol.getProductId());
				if(pi!=null){
					ol.setLogoImage(pi.getBarCode());
				}
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ordersDetailList", ordersListList);
		map.put("ordersNo", ordersNo);
		JSONObject jo = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存订单明细
	public void saveOrUpdateOrdersList(){
		if(ordersList != null){
			//添加订单号到订单明细中
			Integer ordersId = ordersList.getOrdersId();
			Orders ordersTemp = (Orders)ordersService.getObjectByParams(" where o.ordersId='"+ordersId+"'");
			if(ordersTemp != null){
				String ordersNo = ordersTemp.getOrdersNo();
				ordersList.setOrdersNo(ordersNo);
			}
			Integer productId = ordersList.getProductId();
			ProductInfo productInfo = (ProductInfo)productInfoService.getObjectById(productId.toString());
			if(productInfo != null){
				ordersList.setProductFullName(productInfo.getProductFullName());
				if(productInfo.getMarketPrice() != null){
					ordersList.setMarketPrice( productInfo.getMarketPrice());
				}
				if( productInfo.getMemberPrice() != null){
					ordersList.setMarketPrice(productInfo.getMemberPrice());
				}
			}
			ordersListService.saveOrUpdateObject(ordersList);
		}
	}
	//订单明细列表
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ordersListList() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		String countHql="select count(a.ordersOPLogId) from OrdersOPLog a , Orders b where a.ordersId=b.ordersId";
		hqlsb.append(" select b.ordersNo as ordersNo,b.ordersState as ordersState,b.createTime as createTime,c.loginName as loginName from OrdersList a , Orders b,Customer c where a.ordersId=b.ordersId and b.customerId=c.customerId ");
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String parameter = request.getParameter("title");
			if(StringUtils.isNotEmpty(parameter)){
				hqlsb.append(" and a.ordersNo like'%"+parameter+"%'");
				countHql+=" and a.ordersNo like'%"+parameter+"%'";
			}
		}
		int totalRecordCount = ordersListService.getMoreTableCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> ordersDetailList = ordersListService.findListMapPage(hqlsb.toString(),pageHelper);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Map map:ordersDetailList){
			map.put("createTime", formatter.format(map.get("createTime")));
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", ordersDetailList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//到订单明细页面
	public String gotoOrderListPage(){
		return SUCCESS;
	}
	public OrdersList getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOrdersListId() {
		return ordersListId;
	}
	public void setOrdersListId(String ordersListId) {
		this.ordersListId = ordersListId;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public List<OrdersList> getOrdersListList() {
		return ordersListList;
	}
	public void setOrdersListList(List<OrdersList> ordersListList) {
		this.ordersListList = ordersListList;
	}
	public List<Orders> getOrdersLists() {
		return ordersLists;
	}
	public void setOrdersLists(List<Orders> ordersLists) {
		this.ordersLists = ordersLists;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
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
