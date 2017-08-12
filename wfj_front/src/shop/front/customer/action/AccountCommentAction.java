package shop.front.customer.action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.map.LinkedMap;

import shop.customer.pojo.Customer;
import shop.customer.pojo.EvaluateGoods;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.IEvaluateGoodsService;
import shop.front.customer.service.IAccountCommentService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
import util.other.Utils;
import util.pojo.PageHelper;
/**
 * 前台用户评价ACtion
 * @author ss
 *
 */
@SuppressWarnings("unchecked")
public class AccountCommentAction extends BaseAction {
	private IEvaluateGoodsService evaluateGoodsService;
	private IOrdersService ordersService;
	private IOrdersListService ordersListService;
    private IProductInfoService   productInfoService;
	private Orders order;  //订单信息
	private List<OrdersList> orderDetails; //订单详情信息
	private Map<Integer,ProductInfo> productImg; //产品图片信息
	private String commentJson; //添加评价参数
	private static final long serialVersionUID = 1L;
	private List<EvaluateGoods> commentList;
	//列表筛选
	private String level; //状态
	private String startDate; //起始时间
	private String endDate ; //结束日期
	private String productName; //产品名称
	private String type;
	/**新用户评价集合**/
	@SuppressWarnings("rawtypes")
	private List<Map> evaluateGoodsMap;
	/** 弄一个MAP接收评价表的信息和店铺的信息 **/
	private Map<EvaluateGoods,ProductInfo> mapComment = new LinkedMap();
	private IAccountCommentService accountCommentService;
	/**
	 * 显示添加评价
	 * @return
	 */
	public String orderComment(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(order!=null&&customer!=null){
			int customerId = customer.getCustomerId(); 
			order = (Orders) ordersService.getObjectByParams(String.format(" where customerId = %s and ordersId = %s", customerId,order.getOrdersId()));
			if(order !=null){
				//订单详情
				orderDetails = ordersListService.findObjects(" where ordersId = " + order.getOrdersId());
				//产品图片
				productImg = new HashMap<Integer, ProductInfo>();
				for (OrdersList o : orderDetails) {
					 int id = o.getOrderDetailId();
					 productImg.put(id, (ProductInfo) productInfoService.getObjectByParams(" where productId = " + o.getProductId()));
				}
			}
			return SUCCESS;
		}
		return null;
	}
	/**
	 * 处理添加评价
	 * @throws IOException 
	 */
	public void addComment() throws IOException{
		if(commentJson!=null){
			Customer customer = (Customer) session.getAttribute("customer");
			Sonaccount son =(Sonaccount)session.getAttribute("sonaccount");
			ShopInfo si =(ShopInfo)session.getAttribute("shopInfo");
			boolean rs = false;
			try {
				JSONArray array = JSONArray.fromObject(commentJson);
				//订单信息
				order = (Orders) ordersService.getObjectByParams(String.format(" where customerId = %s and ordersId = %s", customer.getCustomerId(),order.getOrdersId()));
				//查找是否已经评价过
				int ecount = evaluateGoodsService.getCount(" where ordersId = " + order.getOrdersId());
				if(ecount>0){
					response.getWriter().print(String.format("{\"success\":false,\"err\":\"订单已经评价！\"}", false));
					return;
				}
				rs = accountCommentService.saveComment(order, array, customer,servletContext,son,si);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().print(String.format("{\"success\":%s}", rs));
		}
	}
	/**
	 * 评价列表
	 */
	public String commentList(){
		Customer customer = (Customer) session.getAttribute("customer");
//		commentList = getComments(2);
//		//解开查询到评价LIST并查询和添加商品对象添加到map中
//		for(EvaluateGoods evaluateGoods : commentList){
//			ProductInfo productInfo =(ProductInfo) productInfoService.getObjectById(String.valueOf(evaluateGoods.getProductId()));
//			mapComment.put(evaluateGoods, productInfo);
//		}
//		type ="2";
		String countHql ="select count(eg.appraiserId) from EvaluateGoods eg where eg.appraiserId ="+customer.getCustomerId();
		String hql ="select eg.ordersId as ordersId ,eg.ordersNo as ordersNo, eg.salesPrice as salesPrice, eg.level as level,eg.content as content,eg.acceptAppraiserName as acceptAppraiserName,eg.logoImg as logoImg,eg.productId as productId,eg.productFullName as productFullName from EvaluateGoods eg where eg.appraiserId="+customer.getCustomerId();
		if(Utils.stringIsNotEmpty(level)){
			countHql+=" and eg.level="+level;
			hql+=" and eg.level="+level;
		}
		hql +=" order by eg.createTime desc ";
		int count =evaluateGoodsService.getMoreTableCount(countHql);
		pageHelper.setPageInfo(pageSize, count, currentPage);
		evaluateGoodsMap = evaluateGoodsService.findListMapPage(hql, pageHelper);
		return SUCCESS;
	}
	private List<EvaluateGoods> getComments(int flag){
		Customer customer = (Customer) session.getAttribute("customer");
		List<EvaluateGoods>  list = new ArrayList<EvaluateGoods>();
		StringBuffer hql = new StringBuffer();
		if(flag == 2){
			//给他人的评价appraiserId
			hql.append(" where appraiserId = ").append(customer.getCustomerId());
		}else if(flag ==1){
			//来自卖家的评价
			hql.append(" where evaluateUserType = 2 and acceptAppraiserId = ").append(customer.getCustomerId());
		}else {
			//来自买家的评价
			hql.append(" where evaluateUserType = 1 and acceptAppraiserId = ").append(customer.getCustomerId());
		}
		//筛选 createTime
		if(startDate != null && endDate !=null){
			hql.append(String.format(" and createTime between '%s' and '%s'",startDate,endDate));
		}
		//筛选 level
		if(level!=null){
			hql.append(" and level =" + level);
		}
		//筛选 productName
		if(productName != null){
			hql.append(" and productFullName like '%${productName}%' ".replace("${productName}", productName));
		}
		int count = evaluateGoodsService.getCount(hql.toString());
		pageHelper = new PageHelper();
		//int pageIndex = util.other.Utils.parseInt(page, 1);
		pageHelper.setPageInfo(pageSize, count, currentPage);
		list = evaluateGoodsService.findListByPageHelper(null, pageHelper, hql.toString()+" order by o.createTime desc");
		return list;
	}
	public void setEvaluateGoodsService(IEvaluateGoodsService evaluateGoodsService) {
		this.evaluateGoodsService = evaluateGoodsService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public List<OrdersList> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrdersList> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public Map<Integer, ProductInfo> getProductImg() {
		return productImg;
	}
	public void setProductImg(Map<Integer, ProductInfo> productImg) {
		this.productImg = productImg;
	}
	public String getCommentJson() {
		return commentJson;
	}
	public void setCommentJson(String commentJson) {
		this.commentJson = commentJson;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public PageHelper getPageHelper() {
		return pageHelper;
	}
	public void setPageHelper(PageHelper pageHelper) {
		this.pageHelper = pageHelper;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<EvaluateGoods> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<EvaluateGoods> commentList) {
		this.commentList = commentList;
	}
	public Map<EvaluateGoods, ProductInfo> getMapComment() {
		return mapComment;
	}
	public void setMapComment(Map<EvaluateGoods, ProductInfo> mapComment) {
		this.mapComment = mapComment;
	}
	public void setAccountCommentService(
			IAccountCommentService accountCommentService) {
		this.accountCommentService = accountCommentService;
	}
	public List<Map> getEvaluateGoodsMap() {
		return evaluateGoodsMap;
	}
	public void setEvaluateGoodsMap(List<Map> evaluateGoodsMap) {
		this.evaluateGoodsMap = evaluateGoodsMap;
	}
}
