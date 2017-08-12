package util.quickBuck.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
import util.quickBuck.Pkipair;

/**
 * 快钱支付action类
 * @author mqr
 */
public class QuickBuckAction extends BaseAction {
	private static final long serialVersionUID = -3316371770657444241L;
	/**支付类型**/
	private String paymentType;//1为单笔订单支付 2为总订单支付 3为合并付款支付
	/**订单ID**/
	private String ordersId;
	/**支付金额**/
	private String payMoney;
	/**传递参数用的map**/
	private Map<String,Object> map;
	/**订单service**/
	private IOrdersService ordersService;
	/**
	 * 生成标识信息
	 * （必须）
	 * @return
	 */
	public String quickBuckAPI(){
		//传递用户操作信息
		Customer customer2 = (Customer)session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo) session.getAttribute("shopInfo");
		Sonaccount sonaccount2 = (Sonaccount)session.getAttribute("sonaccount");
		String customerInfoJson="'customerId':'"+customer2.getCustomerId()+"'";
		if(shopInfo2!=null&&shopInfo2.getShopInfoId()!=null){
			customerInfoJson+=",'shopInfoId':'"+shopInfo2.getShopInfoId()+"'";
		}
		if(sonaccount2!=null&&sonaccount2.getSonAccountId()!=null){
			customerInfoJson+=",'sonaccountId','"+sonaccount2.getSonAccountId()+"'";
		}
		
		map=new HashMap<String,Object>();
		//人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
		String merchantAcctId = String.valueOf(fileUrlConfig.get("quickbuck_merchantAcctId"));
		map.put("merchantAcctId",merchantAcctId);
		//编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
		String inputCharset = "1";
		map.put("inputCharset",inputCharset);
		//接收支付结果的页面地址，该参数一般置为空即可。
		String pageUrl = "";
		map.put("pageUrl",pageUrl);
		//服务器接收支付结果的后台地址，该参数务必填写，不能为空。
		String bgUrl = String.valueOf(fileUrlConfig.get("quickbuck_bgUrl"));
		map.put("bgUrl",bgUrl);
		//网关版本，固定值：v2.0,该参数必填。
		String version =  "v2.0";
		map.put("version",version);
		//语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
		String language =  "1";
		map.put("language",language);
		//签名类型,该值为4，代表PKI加密方式,该参数必填。
		String signType =  "4";
		map.put("signType",signType);
		//支付人姓名,可以为空。
		String payerName= ""; 
		map.put("payerName",payerName);
		//支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
		String payerContactType =  "";
		//支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
		String payerContact =  "";
		
		//获取当前session中的用户信息
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null&&!"".equals(customer.getPhone())){//当用户的信息不为空的时候，填写支付人信息
			payerContactType="2";
			payerContact=customer.getPhone();
		}
		map.put("payerContactType",payerContactType);
		map.put("payerContact",payerContact);
		
		//商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
		String orderId = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		map.put("orderId",orderId);
		
		//订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
		String orderAmount = "1";
		
		
		//扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
		String ext1 = "";
		//扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
		String ext2 = "";
		if(!"".equals(customerInfoJson)){
			ext2="{"+customerInfoJson+"}";
		}
		//计算订单交易金额
		if(paymentType!=null&&"1".equals(paymentType)){//单笔订单支付
			//查询当前订单对象
			Orders orders = (Orders) ordersService.getObjectByParams(" where o.ordersId="+ordersId);
			if(orders!=null){
				payMoney=String.valueOf(orders.getFinalAmount().multiply(new BigDecimal(100)));//从订单中获取支付价格
				if(payMoney.indexOf(".")>0){
					payMoney=payMoney.substring(0,payMoney.indexOf("."));
				}
				orderAmount=payMoney;
				ext1=ordersId;
			}
		}else if(paymentType!=null&&"2".equals(paymentType)){//总订单支付
			//获取订单需要支付的总金额 与订单IDS字符串
			List<Map<String, Object>> ordersListMap = ordersService.findListMapBySql("SELECT  GROUP_CONCAT(conv( oct( a.ordersId ) , 8, 10 )) as ordersIds ,SUM(a.finalAmount) as totalFinalAmount FROM shop_orders a where a.totalOrdersNo='"+ordersId+"';");
			if(ordersListMap!=null&&ordersListMap.size()>0){
				Map<String, Object> ordersMap = ordersListMap.get(0);
				payMoney=String.valueOf(new BigDecimal(String.valueOf(ordersMap.get("totalFinalAmount"))).multiply(new BigDecimal(100)));//获取此次交易所需支付的总价格
				if(payMoney.indexOf(".")>0){
					payMoney=payMoney.substring(0,payMoney.indexOf("."));
				}
				orderAmount=payMoney;
				ext1=String.valueOf(ordersMap.get("ordersIds"));//获取总订单所包含的订单ids字符串
			}
		}else if(paymentType!=null&&"3".equals(paymentType)){
			//获取订单需要支付的总金额 与订单IDS字符串
			List<Map<String, Object>> ordersListMap = ordersService.findListMapBySql("SELECT  GROUP_CONCAT(conv( oct( a.ordersId ) , 8, 10 )) as ordersIds ,SUM(a.finalAmount) as totalFinalAmount FROM shop_orders a where a.ordersId in ( "+ordersId+");");
			if(ordersListMap!=null&&ordersListMap.size()>0){
				Map<String, Object> ordersMap = ordersListMap.get(0);
				payMoney=String.valueOf(new BigDecimal(String.valueOf(ordersMap.get("totalFinalAmount"))).multiply(new BigDecimal(100)));//获取此次交易所需支付的总价格
				if(payMoney.indexOf(".")>0){
					payMoney=payMoney.substring(0,payMoney.indexOf("."));
				}
				orderAmount=payMoney;
				ext1=String.valueOf(ordersMap.get("ordersIds"));//获取总订单所包含的订单ids字符串
			}
		}
		map.put("orderAmount",orderAmount);
		map.put("ext1",ext1);
		map.put("ext2",ext2);
		
		//订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
		String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		map.put("orderTime",orderTime);
		//商品名称，可以为空。
		String productName= "从SHOPJSP购买商品"; 
		map.put("productName",productName);
		//商品数量，可以为空。
		String productNum = "1";
		map.put("productNum",productNum);
		//商品代码，可以为空。
		String productId = "";
		map.put("productId",productId);
		//商品描述，可以为空。
		String productDesc = "";
		map.put("productDesc",productDesc);
		//支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10，必填。
		String payType = "00";
		map.put("payType",payType);
		//银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表。
		String bankId = "";
		map.put("bankId",bankId);
		//同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
		String redoFlag = "";
		map.put("redoFlag",redoFlag);
		//快钱合作伙伴的帐户号，即商户编号，可为空。
		String pid = "";
		map.put("pid",pid);
		// signMsg 签名字符串 不可空，生成加密签名串
		String signMsgVal = "";
		signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset);
		signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl);
		signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl);
		signMsgVal = appendParam(signMsgVal, "version", version);
		signMsgVal = appendParam(signMsgVal, "language", language);
		signMsgVal = appendParam(signMsgVal, "signType", signType);
		signMsgVal = appendParam(signMsgVal, "merchantAcctId",merchantAcctId);
		signMsgVal = appendParam(signMsgVal, "payerName", payerName);
		signMsgVal = appendParam(signMsgVal, "payerContactType",payerContactType);
		signMsgVal = appendParam(signMsgVal, "payerContact", payerContact);
		signMsgVal = appendParam(signMsgVal, "orderId", orderId);
		signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount);
		signMsgVal = appendParam(signMsgVal, "orderTime", orderTime);
		signMsgVal = appendParam(signMsgVal, "productName", productName);
		signMsgVal = appendParam(signMsgVal, "productNum", productNum);
		signMsgVal = appendParam(signMsgVal, "productId", productId);
		signMsgVal = appendParam(signMsgVal, "productDesc", productDesc);
		signMsgVal = appendParam(signMsgVal, "ext1", ext1);
		signMsgVal = appendParam(signMsgVal, "ext2", ext2);
		signMsgVal = appendParam(signMsgVal, "payType", payType);
		signMsgVal = appendParam(signMsgVal, "bankId", bankId);
		signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag);
		signMsgVal = appendParam(signMsgVal, "pid", pid);
		Pkipair pki = new Pkipair();
		String signMsg = pki.signMsg(signMsgVal);
		//System.out.println(signMsg);
		map.put("signMsg", signMsg);
		return SUCCESS;
	}
	/**
	 * 辅助生成标识信息方法
	 * @param returns
	 * @param paramId
	 * @param paramValue
	 * @return
	 */
	public String appendParam(String returns, String paramId, String paramValue) {
		if (returns != "") {
			if (paramValue != "") {
				returns += "&" + paramId + "=" + paramValue;
			}
		} else {
			if (paramValue != "") {
				returns = paramId + "=" + paramValue;
			}
		}
		return returns;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	
}
