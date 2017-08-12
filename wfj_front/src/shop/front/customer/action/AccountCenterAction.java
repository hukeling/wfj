package shop.front.customer.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerService;
import shop.customer.service.ICustomerServiceService;
import shop.customer.service.IShopCustomerServiceService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import shop.product.service.IProductImgService;
import shop.store.service.ICustomerHaveCouponService;
import util.action.BaseAction;
import util.pojo.PageHelper;
@SuppressWarnings("all")
public class AccountCenterAction extends BaseAction {
	private IShoppingCartService shopCartService;
	private IOrdersService ordersService ;
	private ICustomerHaveCouponService customerHaveCouponService;
	private IProductImgService productImgService;
	private ICustomerService customerService;
	private Customer customer;
	private List<Map> cartList; //购物车列表
	private List<Orders>  orderList ; //订单列表
	private Map<Integer,Object> orderProductList; //订单产品列表
	private List<Map> customerProductList; //wish list
	private int unpaid;//未付款单数
	private int shipped;//已经发货单数
	private int coupon; //优惠券
	private int unreadNotice; //未读通知
	private String page; //当前页  用于order list
	//order list 筛选条件
	public String status; //订单状态
	public String startDate; //订单创建时间
	public String endDate; //订单创建时间
	public String orderNo; //订单号
	private static final long serialVersionUID = 1L;
	/**店铺关联客服信息service**/
	private IShopCustomerServiceService shopCustomerServiceService;
	/**客服**/
	private ICustomerServiceService customerServiceService;
	
	/**
	 * 显示用户中心首页
	 */
	public String index(){
		customer= (Customer) session.getAttribute("customer");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		int userId = customer.getCustomerId();//userHelper.getCustomer().getCustomerid();
		//购物车 
		String hql="select c.productFullName as productFullName , c.storeNumber as storeNumber, a.quantity as quantity, a.stockUpDate as stockUpDate,c.sku as sku, c.marketPrice as marketPrice, a.shopCartId as shopCartId,b.shopInfoId as shopInfoId,c.productId as productId ," +
    			" c.describle as describle, c.productName as productName,c.salesPrice as salesPrice,d.brandId as brandId," +
    			" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale, c.stockUpDate as stockUpDate,"+
    			" b.shopName as shopName,b.customerName as customerName,c.logoImg as logoImg ,c.freightPrice as freightPrice from ShoppingCart a ,ShopInfo b ,ProductInfo c ,Brand d " +
    			" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId and c.isPutSale = 2 and a.customerId="+userId;
		cartList = shopCartService.findListMapByHql(hql);
		//订单
		String order_hql="";
		if(customer!=null){
			order_hql = new StringBuffer(" where o.customerId = ").append(userId).append(" ORDER BY createTime DESC ").toString();
			if(son!=null){
				if(son.getType()==1){
					order_hql = new StringBuffer(" where o.customerId = ").append(userId).append(" and o.sonaccountId=").append(son.getSonAccountId()).append(" ORDER BY createTime DESC ").toString();
				}
				if(son.getType()==2){
					order_hql = new StringBuffer(" where o.customerId = ").append(userId).append(" and o.ordersState=1").append(" or o.ordersState=2").append(" ORDER BY createTime DESC ").toString();
				}
			}
		}
		pageHelper = new PageHelper();
		pageHelper.setPageRecordBeginIndex(0);
		pageHelper.setPageSize(5);
		orderList= ordersService.findListByPageHelper(null, pageHelper, order_hql);
		//订单产品
		String product_hql = ("select o.logoImage as logoImg, o.productId as productId,o.productFullName as productFullName from OrdersList o  where ordersId = %{ordersId}");
		orderProductList = new HashMap<Integer, Object>();
		if(orderList!=null&&orderList.size()>0){
			for (Orders o : orderList) {
				List<Map> m = ordersService.findListMapByHql(product_hql.replace("%{ordersId}", o.getOrdersId()+""));
				orderProductList.put(o.getOrdersId(), m);
			}
		}
		if(son!=null&&"1".equals(son.getType().toString())){
			//未付款条数
			unpaid = ordersService.getCount(" where o.settlementStatus=0 and customerId = " + customer.getCustomerId()+" and o.sonaccountId="+son.getSonAccountId());
			//已发货条数
			shipped = ordersService.getCount(" where o.ordersState=4  and customerId = " + customer.getCustomerId()+" and o.sonaccountId="+son.getSonAccountId());
		}else{
			//未付款条数
			unpaid = ordersService.getCount(" where o.settlementStatus=0 and customerId = " + customer.getCustomerId());
			//已发货条数
			shipped = ordersService.getCount(" where o.ordersState=4  and customerId = " + customer.getCustomerId());
		}
		//优惠券数量
		//coupon = customerHaveCouponService.getCount(" where NOW() BETWEEN o.beginTime and o.expirationTime and o.state =1 and  o.customerId = " + userId);
		
		//查询专属客服信息
		List<Map> findObjectsByHQL = customerServiceService.findListMapByHql( "select a.nikeName as nikeName,a.qq as qq,a.photoUrl as photoUrl,a.phone as phone,a.mobile as mobile,a.workNumber as workNumber from CusService a,ShopCustomerService b where a.customerServiceId=b.customerServiceId and a.useState=1 and b.customerId="+customer.getCustomerId());
		if(findObjectsByHQL!=null&&findObjectsByHQL.size()>0){
			Map<String,Object> map=findObjectsByHQL.get(0);
			request.setAttribute("customerServiceInfo", map);
		}
		return SUCCESS;
	}
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getCartList() {
		return cartList;
	}
	@SuppressWarnings({"rawtypes" })
	public void setCartList(List<Map> cartList) {
		this.cartList = cartList;
	}
	public List<Orders> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getCustomerProductList() {
		return customerProductList;
	}
	@SuppressWarnings("rawtypes")
	public void setCustomerProductList(
			List<Map> customerProductList) {
		this.customerProductList = customerProductList;
	}
	public int getUnpaid() {
		return unpaid;
	}
	public void setUnpaid(int unpaid) {
		this.unpaid = unpaid;
	}
	public int getShipped() {
		return shipped;
	}
	public void setShipped(int shipped) {
		this.shipped = shipped;
	}
	public int getUnreadNotice() {
		return unreadNotice;
	}
	public void setUnreadNotice(int unreadNotice) {
		this.unreadNotice = unreadNotice;
	}
	public void setShopCartService(IShoppingCartService shopCartService) {
		this.shopCartService = shopCartService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public Map<Integer, Object> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(Map<Integer, Object> orderProductList) {
		this.orderProductList = orderProductList;
	}
	public int getCoupon() {
		return coupon;
	}
	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ICustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setCustomerHaveCouponService(
			ICustomerHaveCouponService customerHaveCouponService) {
		this.customerHaveCouponService = customerHaveCouponService;
	}
	public void setShopCustomerServiceService(
			IShopCustomerServiceService shopCustomerServiceService) {
		this.shopCustomerServiceService = shopCustomerServiceService;
	}
	public void setCustomerServiceService(
			ICustomerServiceService customerServiceService) {
		this.customerServiceService = customerServiceService;
	}
}
