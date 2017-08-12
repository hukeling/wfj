package shop.front.store.action;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.logistics.pojo.Logistics;
import shop.logistics.service.ILogisticsService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersList;
import shop.order.pojo.Shipping;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import shop.returnsApply.service.IReturnsApplyService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * 前台退换货
 * @author mqr
 */
public class FrontStoreReturnsAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**退换货申请service**/
	private IReturnsApplyService returnsApplyService;
	/**退换申请操作日志Service**/
	private IReturnsApplyOPLogService returnsApplyOPLogService;
	/**退换货申请Obj*/
	private ReturnsApply returnsApply=new ReturnsApply();
	/**店铺service**/
	private IShopInfoService shopInfoService;
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
	private Shipping shipping;
	//物流公司
	private ILogisticsService logisticsService;
	/*********************************************end*********************************************************/
	//跳转-店铺-退换货列表
	public String gotoReturnsApplyListPageShop(){
		Customer customer =(Customer) session.getAttribute("customer");
		shopInfo=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
		String sql="SELECT a.applyTime as applyTime, a.returnsApplyId as returnsApplyId, a.ordersId as ordersId,a.productId as productId," +
				"a.shopInfoId as shopInfoId, a.ordersNo as ordersNo,a.returnsApplyNo as returnsApplyNo,a.disposeMode as disposeMode," +
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
	/**
	 * 查看退货明细
	 * @return
	 */
	public String gotoComplaintsLogInfoPage(){
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		if(shopInfo!=null){
			returnsApply = (ReturnsApply) returnsApplyService.getObjectByParams(" where o.returnsApplyId="+returnsApplyId+" and o.shopInfoId="+shopInfo.getShopInfoId());
			if(returnsApply!=null){
				orders=(Orders) ordersService.getObjectByParams("where o.ordersId="+returnsApply.getOrdersId()+" and o.shopInfoId="+shopInfo.getShopInfoId());//查询订单
				if(orders!=null){
					ordersList=(OrdersList) ordersListService.getObjectByParams(" where o.ordersId="+returnsApply.getOrdersId()+" and o.productId="+returnsApply.getProductId());//查询订单明细商品数量
					if(null!=returnsApply && null!=returnsApply.getUploadImage()){
						String[] split = returnsApply.getUploadImage().split("@");
						for(String s:split){
							showUploadImgList.add(s);
						}
					}
					returnsApplyOPLogList = returnsApplyOPLogService.findObjects(" where o.returnsApplyNo='"+returnsApply.getReturnsApplyNo()+"' order by o.operatorTime ");
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 修改申请换货状态
	 * @return
	 */
	public String saveTradeOrder(){
		Customer customer =(Customer) session.getAttribute("customer");
		ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
		//修改状态
		ReturnsApply ra=(ReturnsApply) returnsApplyService.getObjectByParams(" where o.returnsApplyId="+returnsApplyId +" and o.shopInfoId="+shopInfo.getShopInfoId());
		if(ra!=null){
			ra.setApplyState(2);
			ra.setReturnsState(4);
			ra.setUpdateMember(customer.getLoginName());
			ra.setUpdateTime(new Date());
			Logistics logistics=(Logistics)logisticsService.getObjectByParams(" where o.code = '"+shipping.getCode()+"'");
			StringBuffer message = new StringBuffer();
			message.append("快递单号为："+shipping.getDeliverySn()+";</br>");
			message.append("快递公司为："+logistics.getDeliveryCorpName()+";</br>");
			message.append("快递公司网址为："+logistics.getDeliveryUrl()+";");
			ra.setMessage(message.toString());
			ra = (ReturnsApply) returnsApplyService.saveOrUpdateObject(ra);
		}
		//日志
		ReturnsApplyOPLog raol=new ReturnsApplyOPLog();
		raol.setOperatorLoginName(customer.getLoginName());//登陆名称
		raol.setOperatorName(customer.getLoginName());//操作人姓名
		raol.setReturnsApplyNo(ra.getReturnsApplyNo());//申请退货单号
		raol.setApplyId(ra.getReturnsApplyId());//id
		raol.setShopInfoId(ra.getShopInfoId());//店铺id
		String sata="换货完成";//换货状态
		raol.setComments("换货状态："+sata);//处理信息
		raol.setOperatorTime(new Date());//操作时间
		returnsApplyOPLogService.saveOrUpdateObject(raol);
		return SUCCESS;
	}
	//setter getter 
	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
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
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public void setLogisticsService(ILogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}
}
