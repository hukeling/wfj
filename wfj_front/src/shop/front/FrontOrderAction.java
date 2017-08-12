package shop.front;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.ICustomerService;
import shop.order.pojo.Orders;
import shop.order.pojo.OrdersOPLog;
import shop.order.service.IOrdersListService;
import shop.order.service.IOrdersOPLogService;
import shop.order.service.IOrdersService;
import util.action.BaseAction;
/**
 * 前台订单Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class FrontOrderAction extends BaseAction{
	private IOrdersService ordersService;//订单信息Service
	@SuppressWarnings("unused")
	private IOrdersListService ordersListService;//订单明细Service
	@SuppressWarnings("unused")
	private ICustomerService customerService;//会员Service
	private ICustomerAcceptAddressService customerAcceptAddressService;//会员收获地址Service
	private IOrdersOPLogService ordersOPLogService;//订单操作日志Service
	private CustomerAcceptAddress customerAcceptAddress;//会员地址
	//核对订单
	public String checkOrder(){
		Customer customer = (Customer) session.getAttribute("customer");
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerId='"+customer.getCustomerId()+"' and o.isSendAddress=1");
		return SUCCESS;
	}
	//生成订单
	public String generateOrder() throws ParseException{
		Customer customer = (Customer) session.getAttribute("customer");
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerId='"+customer.getCustomerId()+"' and o.isSendAddress=1");
		Orders orders = new Orders();
		Date createTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String createTimeStr = sdf.format(createTime);
		orders.setCreateTime(createTime);
		if("".equals(orders.getOrdersId())){
			orders.setOrdersId(null);
		}
//		if("".equals(orders.getUseRedbag())){
//			orders.setUseRedbag(null);
//		}
		Double random = Math.random();
		String num = random.toString().substring(2, 8);
		String ordersNo = createTimeStr+num;
		orders.setOrdersNo(ordersNo);//订单号
		orders.setCustomerId(customer.getCustomerId());//会员ID
//		orders.setProvince(customerAcceptAddress.getProvince());//省
//		orders.setCityDistrict(customerAcceptAddress.getCity());//市区
//		orders.setCounty(customerAcceptAddress.getCountry());//县
		orders.setConsignee(customerAcceptAddress.getConsignee());//收货人
		orders.setEmail(customerAcceptAddress.getEmail());//电子邮件
		orders.setAddress(customerAcceptAddress.getAddress());//详细地址
		orders.setPostcode(customerAcceptAddress.getPostcode());//邮政编码
		orders.setPhone(customerAcceptAddress.getPhone());//住宅电话
		orders.setMobilePhone(customerAcceptAddress.getMobilePhone());//手机号
		orders.setFlagContractor(customerAcceptAddress.getFlagContractor());//标致建筑
		orders.setBestSendDate(customerAcceptAddress.getBestSendDate());//最佳送货时间
//		orders.setSendType(1);//暂时定为1，配送方式设计完再修改
		orders.setPayMode(1);//暂时定为2，支付方式设计完再修改(支付宝)
//		orders.setProductSumNumber((Double)session.getAttribute("totalMoney"));//商品总金额
//		orders.setPayNumber((Double)session.getAttribute("totalMoney"));//商品应付金额
//		orders.setSendNumber(20.00);//运费，默认为20，根据配送方式定价格
		orders.setIsLocked(0);//锁定状态，未锁定
		orders.setOrdersState(1);//订单状态，未处理
		ordersService.saveOrUpdateObject(orders);
		//添加操作日志
		OrdersOPLog ordersOPLog = new OrdersOPLog();
		ordersOPLog.setOrdersId(orders.getOrdersId());//订单ID
		ordersOPLog.setOrdersNo(orders.getOrdersNo());//订单号
		ordersOPLog.setOoperatorId(customer.getCustomerId());//操作人ID
		ordersOPLog.setOperatorName(customer.getLoginName());//操作人姓名
		ordersOPLog.setOoperatorSource("3");//操作人来源
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatTime = sdff.format(createTime);
		Date date = sdf.parse(formatTime);
		ordersOPLog.setOperatorTime(date);
		ordersOPLog.setOrdersOperateState(10);
		ordersOPLog.setComments(customer.getLoginName()+"在"+formatTime+"时生成了订单："+orders.getOrdersNo());
		ordersOPLogService.saveOrUpdateObject(ordersOPLog);
		//保存订单明细，订单明细保存完以后清除购物车和cookie
//		Cookie [] cookies = request.getCookies();
//		for(Cookie c : cookies){
//			if("gwc".equals(c.getName())){
//				String value = c.getValue();
//				String[] goodsArray = value.split(",");
//				for(int i=0;i<goodsArray.length;i++){
//					if(!"".equals(goodsArray[i])){
//						String[] goodsValue = goodsArray[i].split("_");
//					}
//				}
//			}
//		}
		request.setAttribute("ordersNo", ordersNo);
		String deliveryTime = "";
		if(customerAcceptAddress.getBestSendDate()==1){
			deliveryTime = "只工作日送货(双休日、假日不用送)";
		}else if(customerAcceptAddress.getBestSendDate()==2){
			deliveryTime = "工作日、双休日与假日均可送货";
		}else if(customerAcceptAddress.getBestSendDate()==3){
			deliveryTime = "只双休日、假日送货(工作日不用送)";
		}
		request.setAttribute("deliveryTime", deliveryTime);
		return SUCCESS;
	}
	//订单详情
	public String ordersInfo(){
		@SuppressWarnings("unused")
		String ordersNo = request.getParameter("ordersNo");
		return SUCCESS;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public void setOrdersListService(IOrdersListService ordersListService) {
		this.ordersListService = ordersListService;
	}
	public CustomerAcceptAddress getCustomerAcceptAddress() {
		return customerAcceptAddress;
	}
	public void setCustomerAcceptAddress(CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setOrdersOPLogService(IOrdersOPLogService ordersOPLogService) {
		this.ordersOPLogService = ordersOPLogService;
	}
}
