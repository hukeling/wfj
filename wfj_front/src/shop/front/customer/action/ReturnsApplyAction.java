package shop.front.customer.action;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import shop.returnsApply.service.IReturnsApplyService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.SerialNumberUtil;
import util.other.Utils;
import util.upload.ImageFileUploadUtil;
/**
 * 前台退换货
 * @author mqr
 */
public class ReturnsApplyAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**退换货申请service**/
	private IReturnsApplyService returnsApplyService;
	/**退换申请操作日志Service**/
	private IReturnsApplyOPLogService returnsApplyOPLogService;
	/**退换货申请Obj*/
	private ReturnsApply returnsApply=new ReturnsApply();
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**商品service**/
	private IProductInfoService productInfoService;
	/**订单明细service**/
	private IOrdersListService ordersListService;
	/**订单service**/
	private IOrdersService ordersService;
	/**订单明细对象Obj**/
	private OrdersList ordersList=new OrdersList();
	/**订单对象Obj**/
	private Orders orders=new Orders();
	/**商品Obj**/
	private ProductInfo productInfo=new ProductInfo();
	/**店铺Obj**/
	private ShopInfo shopInfo=new ShopInfo();
	/**退换货操作日志Obj**/
	private ReturnsApplyOPLog returnsApplyOPLog=new ReturnsApplyOPLog();
	/**店铺ID**/
	private Integer shopInfoId;
	/**订单ID**/
	private Integer ordersId;
	/**商品ID**/
	private Integer productId;
	/**当前页ID**/
	private Integer pageIndex=1;
	/**订单包含的商品List<Map>**/
	private List<Map> mapImg=new ArrayList<Map>();
	/**退换货记录List**/
	private List<Map> returnsApplyListMap=new ArrayList<Map>();
	/**退换货多文件上传图片**/
	private List<File> listProductUploadImgs;
	/**退换货多文件上传图片**/
	private List<String>  listProductUploadImgsFileName;
	/**审核状态**/
	private Integer asState;
	/**退货处理状态**/
	private Integer rsState;
	/**退货处理id**/
	private Integer returnsApplyId;
	/**某一商品的退货总数**/
	private Integer countTatol=0;
	/**某一商品的购买总数**/
	private Integer count;
	/**查看商品详情时，查看商品图片集合**/
	private List<String> showUploadImgList =  new ArrayList<String>();
	private List<ReturnsApplyOPLog> returnsApplyOPLogList;
	/*********************************************end*********************************************************/
	//跳转-订单商品退换货申请列表页
	public String gotoCustomerComplaintsPage(){
		Customer customer =(Customer) session.getAttribute("customer");
		String params="WHERE o.customerId="+customer.getCustomerId()+" and o.ordersId="+ordersId;
		orders=(Orders) ordersService.getObjectByParams(params);
		String hql="select a.productFullName as productName,a.productId as productId, a.logoImage as thumbnail  from OrdersList a  where a.ordersId="+ordersId;
		mapImg=ordersService.findListMapByHql(hql);
		if(mapImg!=null&&mapImg.size()>0){
			for(Map m:mapImg){
				Integer productId=(Integer) m.get("productId");
				ordersList=(OrdersList) ordersListService.getObjectByParams(" where o.ordersId="+ordersId+" and o.productId="+productId);//查询订单明细
				if(ordersList!=null){
					count = ordersList.getCount();
				}else{
					count=0;
				}
				List<ReturnsApply> l=returnsApplyService.findObjects(" where o.ordersId="+ordersId+" and o.productId="+productId);//退换货申请记录
				if(l!=null&&l.size()>0){
					for(ReturnsApply ra:l){
						countTatol=countTatol+ ra.getCount();
					}
				}
				if(count.compareTo(countTatol)>0){//购买数 大于 退货数 ：可申请退货
					m.put("sq", 0);//可以申请
				}else{
					m.put("sq", 1);
				}
				m.put("list", l);
				m.put("countTatol", countTatol);//退换货申请记录  退货总数
				m.put("count", count);//订单中该商品的购买总数
			}
		}
		return SUCCESS;
	}
	//跳转-某个商品填写申请退换货信息页
	public String gotoComplaintsSalesInfoPage(){
		String params="WHERE o.ordersId="+ordersId;
		orders=(Orders) ordersService.getObjectByParams(params);//查询订单
		ordersList=(OrdersList) ordersListService.getObjectByParams(" where o.ordersId="+ordersId+" and o.productId="+productId);//查询订单明细
//		productInfo=(ProductInfo) productInfoService.getObjectByParams(" where o.productId="+ordersList.getProductId());//商品对象
//		shopInfo=(ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+productInfo.getShopInfoId());
		return SUCCESS;
	}
	/**
	 * 查看退货明细
	 * @return
	 */
	public String gotoComplaintsLogInfoPage(){
		returnsApply = (ReturnsApply) returnsApplyService.getObjectByParams(" where o.returnsApplyId="+returnsApplyId);
		orders=(Orders) ordersService.getObjectByParams("where o.ordersId="+returnsApply.getOrdersId());//查询订单
//		productInfo=(ProductInfo) productInfoService.getObjectByParams(" where o.productId="+returnsApply.getProductId());//商品对象
//		shopInfo=(ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+returnsApply.getShopInfoId());
		ordersList=(OrdersList) ordersListService.getObjectByParams(" where o.ordersId="+returnsApply.getOrdersId()+" and o.productId="+returnsApply.getProductId());//查询订单明细商品数量
		if(null!=returnsApply && null!=returnsApply.getUploadImage()){
			String[] split = returnsApply.getUploadImage().split("@");
			for(String s:split){
				showUploadImgList.add(s);
			}
		}
		returnsApplyOPLogList = returnsApplyOPLogService.findObjects(" where o.returnsApplyNo='"+returnsApply.getReturnsApplyNo()+"' order by o.operatorTime ");
		return SUCCESS;
	}
	//保存退换货记录
	public String saveReturnApply() throws ParseException{
		Customer customer =(Customer) session.getAttribute("customer");
		//查询当前退货商品是否符合退货条件
		Integer count2 = returnsApplyService.getCount(" where o.customerId="+customer.getCustomerId()+" and o.ordersId="+returnsApply.getOrdersId()+" and o.applyState!=3");
		if(count2==0){
			if(returnsApply!=null){
				//图片处理
				if(listProductUploadImgs!=null){
					String uploadImg="";
					for(int i=0;i<listProductUploadImgs.size();i++){
						String imagePathFileName = ImageFileUploadUtil.uploadImageFile(listProductUploadImgs.get(i), listProductUploadImgsFileName.get(i), fileUrlConfig, "image_product");
						uploadImg+=imagePathFileName+"@";
					}
					uploadImg=uploadImg.substring(0, uploadImg.lastIndexOf("@"));
					returnsApply.setUploadImage(uploadImg);
				}
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formatTime = sdf.format(new Date());
				Date date = sdf.parse(formatTime);
				returnsApply.setApplyTime(date);
				returnsApply.setUpdateTime(date);
				returnsApply.setCustomerId(customer.getCustomerId());//用户id
				orders = (Orders) ordersService.getObjectByParams(" where o.ordersId='"+returnsApply.getOrdersId()+"'");
				returnsApply.setOrdersNo(orders.getOrdersNo());//订单号
				productInfo = (ProductInfo) productInfoService.getObjectById(String.valueOf(returnsApply.getProductId()));
				returnsApply.setProductName(productInfo.getProductName());//商品名称
				String str=SerialNumberUtil.ReturnsApplyNoNumber();
				returnsApply.setReturnsApplyNo(str);//退换货申请单号
				returnsApply.setApplyState(1);//审核状态(默认：1 处理中)
				returnsApply.setReturnsState(0);//退款状态(默认：0未处理)
				returnsApply=(ReturnsApply)returnsApplyService.saveOrUpdateObject(returnsApply);
				//生成退换货操作日志
				Integer dd = returnsApply.getReturnsApplyId();
				returnsApplyOPLog.setApplyId(dd);
				returnsApplyOPLog.setReturnsApplyNo(str);
				returnsApplyOPLog.setOperatorLoginName(customer.getLoginName());//操作人登陆名称
				returnsApplyOPLog.setShopInfoId(orders.getShopInfoId());//店铺Id
				returnsApplyOPLog.setOperatorName(customer.getLoginName());//登陆人姓名
				returnsApplyOPLog.setComments("退货申请！");
				if(Utils.objectIsNotEmpty(returnsApply.getDisposeMode())){
					if("2".equals(returnsApply.getDisposeMode())){
						returnsApplyOPLog.setComments("换货申请！");
					}
				}
				returnsApplyOPLog.setOperatorTime(date);
				returnsApplyOPLogService.saveOrUpdateObject(returnsApplyOPLog);
			}
		}
		return SUCCESS;
	}
	//跳转-退换货记录查询
	public String gotoReturnsApplyListPage(){
		Customer customer =(Customer) session.getAttribute("customer");
		String sql="SELECT a.returnsApplyId as returnsApplyId, a.ordersId as ordersId,a.productId as productId," +
				"a.shopInfoId as shopInfoId, a.ordersNo as ordersNo,a.returnsApplyNo as returnsApplyNo,a.disposeMode as disposeMode," +
				"a.productName as productName,a.count as count ,a.applyState as applyState," +
				"a.returnsState as returnsState,a.uploadImage as uploadImage ,a.applyTime as applyTime,b.loginName as loginName " +
				"FROM ReturnsApply a,Customer b where a.customerId=b.customerId and a.customerId="+customer.getCustomerId();
		String sqlCount="SELECT count(a.ordersNo) FROM ReturnsApply a,Customer b where a.customerId=b.customerId and a.customerId="+customer.getCustomerId();
		Integer totalRecordCount = returnsApplyService.getMultilistCount(sqlCount);
		pageHelper.setPageInfo(6, totalRecordCount, currentPage);
		returnsApplyListMap=returnsApplyService.findListMapPage(sql+" order by a.applyTime desc", pageHelper);
		for(Map m:returnsApplyListMap){
			if(m.get("uploadImage")!=null){
				String uploadImage = (String) m.get("uploadImage");
				String[] split = uploadImage.split("@");
				List<String> l=new ArrayList<String>();
				for(String s:split){
					l.add(s);
				}
				m.put("imgList", l);
			}
		}
		return SUCCESS;
	}
	//跳转-店铺-退换货列表
	public String gotoReturnsApplyListPageShop(){
		Customer customer =(Customer) session.getAttribute("customer");
		shopInfo=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
		String sql="SELECT a.applyTime as applyTime, a.returnsApplyId as returnsApplyId, a.ordersId as ordersId,a.productId as productId," +
				"a.shopInfoId as shopInfoId, a.ordersNo as ordersNo,a.returnsApplyNo as returnsApplyNo," +
				"a.productName as productName,a.count as count ,a.applyState as applyState,a.returnsState as returnsState," +
				"a.uploadImage as uploadImage FROM ReturnsApply a where a.shopInfoId="+shopInfo.getShopInfoId();
		String sqlCount="SELECT count(a.ordersNo) FROM ReturnsApply a where a.shopInfoId="+shopInfo.getShopInfoId();
		Integer totalRecordCount = returnsApplyService.getMultilistCount(sqlCount);
		pageHelper.setPageInfo(6, totalRecordCount, currentPage);
		returnsApplyListMap=returnsApplyService.findListMapPage(sql+" order by a.applyTime desc", pageHelper);
		for(Map m:returnsApplyListMap){
			if(m.get("uploadImage")!=null){
				String uploadImage = (String) m.get("uploadImage");
				String[] split = uploadImage.split("@");
				List<String> l=new ArrayList<String>();
				for(String s:split){
					l.add(s);
				}
				m.put("imgList", l);
			}
		}
		return SUCCESS;
	}
	//setter getter 
	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public Orders getOrders() {
		return orders;
	}
	public Integer getAsState() {
		return asState;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setAsState(Integer asState) {
		this.asState = asState;
	}
	public Integer getRsState() {
		return rsState;
	}
	public void setRsState(Integer rsState) {
		this.rsState = rsState;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public List<Map> getMapImg() {
		return mapImg;
	}
	public void setMapImg(List<Map> mapImg) {
		this.mapImg = mapImg;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public OrdersList getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public ReturnsApply getReturnsApply() {
		return returnsApply;
	}
	public void setReturnsApplyOPLogService(
			IReturnsApplyOPLogService returnsApplyOPLogService) {
		this.returnsApplyOPLogService = returnsApplyOPLogService;
	}
	public void setReturnsApply(ReturnsApply returnsApply) {
		this.returnsApply = returnsApply;
	}
	public ReturnsApplyOPLog getReturnsApplyOPLog() {
		return returnsApplyOPLog;
	}
	public void setReturnsApplyOPLog(ReturnsApplyOPLog returnsApplyOPLog) {
		this.returnsApplyOPLog = returnsApplyOPLog;
	}
	public List<Map> getReturnsApplyListMap() {
		return returnsApplyListMap;
	}
	public void setReturnsApplyListMap(List<Map> returnsApplyListMap) {
		this.returnsApplyListMap = returnsApplyListMap;
	}
	public List<File> getListProductUploadImgs() {
		return listProductUploadImgs;
	}
	public void setListProductUploadImgs(List<File> listProductUploadImgs) {
		this.listProductUploadImgs = listProductUploadImgs;
	}
	public List<String> getListProductUploadImgsFileName() {
		return listProductUploadImgsFileName;
	}
	public void setListProductUploadImgsFileName(
			List<String> listProductUploadImgsFileName) {
		this.listProductUploadImgsFileName = listProductUploadImgsFileName;
	}
	public Integer getReturnsApplyId() {
		return returnsApplyId;
	}
	public void setReturnsApplyId(Integer returnsApplyId) {
		this.returnsApplyId = returnsApplyId;
	}
	public Integer getCountTatol() {
		return countTatol;
	}
	public void setCountTatol(Integer countTatol) {
		this.countTatol = countTatol;
	}
	public List<String> getShowUploadImgList() {
		return showUploadImgList;
	}
	public void setShowUploadImgList(List<String> showUploadImgList) {
		this.showUploadImgList = showUploadImgList;
	}
	public List<ReturnsApplyOPLog> getReturnsApplyOPLogList() {
		return returnsApplyOPLogList;
	}
	public void setReturnsApplyOPLogList(
			List<ReturnsApplyOPLog> returnsApplyOPLogList) {
		this.returnsApplyOPLogList = returnsApplyOPLogList;
	}
}
