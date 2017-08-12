package util.tenpay.action;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ISafetyCertificateService;
import shop.front.shoppingOnLine.service.IShoppingCartOrderService;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import util.action.BaseAction;
import util.tenpay.RequestHandler;
import util.tenpay.ResponseHandler;
import util.tenpay.client.ClientResponseHandler;
import util.tenpay.client.TenpayHttpClient;
import util.tenpay.util.TenpayUtil;
public class PayAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	/**请求路径的url**/
	private String requestUrl;
	/**通过财付通支付之后返回，修改订单状态**/
	private IShoppingCartOrderService shoppingCartOrderService;
	/**订单Service**/
	private IOrdersService ordersService;
	/**安全认证Service获取支付秘钥**/
	private ISafetyCertificateService safetyCertificateService;
	/***************************************end**************************************************/
	/**
	 * 支付交易请求参数封装
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void tradePay() {
		try{
			Map<String,String> fileUrlConfig = (Map<String,String>)servletContext.getAttribute("fileUrlConfig");
			//商户号
			 String partner = fileUrlConfig.get("partner");
			//密钥
			String key = fileUrlConfig.get("key");
			//交易完成后跳转的URL
			 String return_url = fileUrlConfig.get("return_url");
			//接收财付通通知的URL
			String notify_url = fileUrlConfig.get("notify_url");
			//---------------------------------------------------------
			//财付通网关支付请求示例，商户按照此文档进行开发即可
			//---------------------------------------------------------
			 String tradeId = request.getParameter("tradeId");//交易ID
			 String tradeMoney = request.getParameter("tradeMoney");//交易金额
			 if(StringUtils.isNotEmpty(tradeMoney)){
				 BigDecimal tradeMoney1 = new BigDecimal(tradeMoney);
				 //BigDecimal tradeMoney1 = new BigDecimal(0.01);
				 tradeMoney1=tradeMoney1.multiply(new BigDecimal(100));
				 tradeMoney = String.valueOf(tradeMoney1);
				 int indexOf = tradeMoney.indexOf(".");
				 if(indexOf >= 0){
					 tradeMoney = tradeMoney.substring(0, indexOf);
				 }
			 }
			 String tradeType = request.getParameter("tradeType");//交易类型
			// ---------------生成订单号 开始------------------------
			// 当前时间 yyyyMMddHHmmss
			String currTime = TenpayUtil.getCurrTime();
			// 8位日期
			String strTime = currTime.substring(8, currTime.length());
			// 四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
			// 10位序列号,可以自行调整。
			String strReq = strTime + strRandom;
			// 订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
			String out_trade_no = strReq;
			// ---------------生成订单号 结束------------------------
			//创建支付请求对象
			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init();
			//设置密钥
			reqHandler.setKey(key);
			//设置支付网关
			reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");
			//-----------------------------
			//设置支付参数
			//-----------------------------
			if("3".equals(tradeType)){
				List<Orders> ordersList = ordersService.findObjects("where o.totalOrdersNo='"+tradeId+"'");
				BigDecimal finalAmount = new BigDecimal(0); //商品金额
				BigDecimal freight = new BigDecimal(0); //运费
				BigDecimal tradeMoney1 = new BigDecimal(0);//支付金额
				for(Orders orders:ordersList){
					 //支付金额
					//支付金额没有传递时处理
					tradeMoney1 = tradeMoney1.add(orders.getFinalAmount());
						 //BigDecimal tradeMoney1 = new BigDecimal(0.01);
					 //运费
					 freight = freight.add(orders.getFreight());
					 //商品金额
					 finalAmount = finalAmount.add(orders.getFinalAmount());
					 //BigDecimal finalAmount = new BigDecimal(0.01);
					 SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					 String createTime = outFormat.format(orders.getCreateTime());
					 reqHandler.setParameter("time_start", createTime); 
				}
				tradeMoney1=tradeMoney1.multiply(new BigDecimal(100));
				freight=freight.multiply(new BigDecimal(100));
				finalAmount=finalAmount.multiply(new BigDecimal(100));
				tradeMoney = String.valueOf(tradeMoney1);
				int indexOf = tradeMoney.indexOf(".");
				if(indexOf >= 0){
					tradeMoney = tradeMoney.substring(0, indexOf);
				}
				String transport_fee = String.valueOf(freight);
				int indexOfTransport_fee = transport_fee.indexOf(".");
				if(indexOfTransport_fee >= 0){
					transport_fee = transport_fee.substring(0, indexOfTransport_fee);
				}
				String product_fee = String.valueOf(tradeMoney);
				int indexOfProduct_fee = product_fee.indexOf(".");
				if(indexOfProduct_fee >= 0){
					product_fee = product_fee.substring(0, indexOfProduct_fee);
				}
				reqHandler.setParameter("product_fee", product_fee);                 //商品费用，必须保证transport_fee + product_fee=total_fee
				reqHandler.setParameter("transport_fee", transport_fee);               //物流费用，必须保证transport_fee + product_fee=total_fee
				           //订单生成时间，格式为yyyymmddhhmmss
				reqHandler.setParameter("out_trade_no", tradeId);		//商家订单号（国桥甲乙方交易Id）
			}else{
				reqHandler.setParameter("product_fee", "");                 //商品费用，必须保证transport_fee + product_fee=total_fee
				reqHandler.setParameter("transport_fee", "0");               //物流费用，必须保证transport_fee + product_fee=total_fee
				reqHandler.setParameter("time_start", currTime);            //订单生成时间，格式为yyyymmddhhmmss
				reqHandler.setParameter("out_trade_no", out_trade_no);		//商家订单号（国桥甲乙方交易Id）
			}
			reqHandler.setParameter("partner", partner);		        //商户号
			reqHandler.setParameter("total_fee", tradeMoney);			//商品金额,以分为单位（交易金额）
			reqHandler.setParameter("return_url", return_url);		    //交易完成后跳转的URL
			reqHandler.setParameter("notify_url", notify_url);		    //接收财付通通知的URL
			//商品描述（交易描述）
			if("1".equals(String.valueOf(tradeType))){
				reqHandler.setParameter("body", "项目金额支付");	
			}else if("2".equals(String.valueOf(tradeType))){
				reqHandler.setParameter("body", "项目保证金支付");
			}else if("3".equals(String.valueOf(tradeType))){
				reqHandler.setParameter("body", "从天海商城购买的商品");
			}
			reqHandler.setParameter("bank_type", "DEFAULT");		    //银行类型(中介担保时此参数无效)
			reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip，不是商户服务器IP
			reqHandler.setParameter("fee_type", "1");                    //币种，1人民币
			//商品名称(中介交易时必填)
			if("1".equals(String.valueOf(tradeType)) || "2".equals(String.valueOf(tradeType))){
				reqHandler.setParameter("subject", "交易名称"); 
			}else if("3".equals(String.valueOf(tradeType))){
				reqHandler.setParameter("subject", "订单信息"); 
			}
			//交易类型、交易ID
			reqHandler.setParameter("tradeType", tradeType);
			reqHandler.setParameter("tradeId", tradeId);
			//系统可选参数
			reqHandler.setParameter("sign_type", "MD5");                //签名类型,默认：MD5
			reqHandler.setParameter("service_version", "1.0");			//版本号，默认为1.0
			reqHandler.setParameter("input_charset", "utf-8");            //字符编码
			reqHandler.setParameter("sign_key_index", "1");             //密钥序号
			//业务可选参数
			reqHandler.setParameter("attach", "");                      //附加数据，原样返回
			reqHandler.setParameter("time_expire", "");                 //订单失效时间，格式为yyyymmddhhmmss
			reqHandler.setParameter("buyer_id", "");                    //买方财付通账号
			reqHandler.setParameter("goods_tag", "");                   //商品标记
			reqHandler.setParameter("trade_mode", "1");                 //交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
			reqHandler.setParameter("transport_desc", "");              //物流说明
			reqHandler.setParameter("trans_type", "1");                  //交易类型，1实物交易，2虚拟交易
			reqHandler.setParameter("agentid", "");                     //平台ID
			reqHandler.setParameter("agent_type", "");                  //代理模式，0无代理(默认)，1表示卡易售模式，2表示网店模式
			reqHandler.setParameter("seller_id", "");                   //卖家商户号，为空则等同于partner
			//请求的url
			requestUrl = reqHandler.getRequestURL();
			//获取debug信息,建议把请求和debug信息写入日志，方便定位问题
			String debuginfo = reqHandler.getDebugInfo();
//			System.out.println("requestUrl:  " + requestUrl);
//			System.out.println("sign_String:  " + debuginfo);
			JSONObject jo = new JSONObject();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			jo.accumulate("requestUrl", requestUrl);
			out.println(jo.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
//		//打开新窗口
//		java.io.PrintWriter out = response.getWriter();
//		out.println("<html>");
//		out.println("<script>");
//		out.println("window.open ('"+requestUrl+"','_blank')");
//		out.println("</script>");
//		out.println("</html>");
	}
	/**
	 * 支付成功财付通请求方法，处理交易状态，并返回车成功给财付通服务器
	 */
	public void tradePayNotice() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		ShopInfo shopInfo2 = (ShopInfo)session.getAttribute("shopInfo");
		Sonaccount son = (Sonaccount)session.getAttribute("sonaccount");
		@SuppressWarnings("unchecked")
		Map<String,String> fileUrlConfig = (Map<String,String>)servletContext.getAttribute("fileUrlConfig");
		//商户号
		 String partner = fileUrlConfig.get("partner");
		//密钥
		String key = fileUrlConfig.get("key");
		//---------------------------------------------------------
		//财付通支付通知（后台通知）
		//---------------------------------------------------------
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(key);
//		System.out.println("后台回调返回参数:"+resHandler.getAllParameters());
		//判断签名
		if(resHandler.isTenpaySign()) {
			//通知id
			String notify_id = resHandler.getParameter("notify_id");
			//创建请求对象
			RequestHandler queryReq = new RequestHandler(null, null);
			//通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			//应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();
			//通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(key);
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
			queryReq.setParameter("partner", partner);
			queryReq.setParameter("notify_id", notify_id);
			//通信对象
			httpClient.setTimeOut(5);
			//设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
//			System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());
			//后台调用
			if(httpClient.call()) {
				//设置结果参数
				queryRes.setContent(httpClient.getResContent());
//				System.out.println("验证ID返回字符串:" + httpClient.getResContent());
				queryRes.setKey(key);
				//获取id验证返回状态码，0表示此通知id是财付通发起
				String retcode = queryRes.getParameter("retcode");
				//商户订单号
				//String out_trade_no = resHandler.getParameter("out_trade_no");
				//财付通订单号
				String transaction_id = resHandler.getParameter("transaction_id");
				//金额,以分为单位
				//String total_fee = resHandler.getParameter("total_fee");
				//如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
				//String discount = resHandler.getParameter("discount");
				//支付结果
				String trade_state = resHandler.getParameter("trade_state");
				//交易模式，1即时到账，2中介担保
				String trade_mode = resHandler.getParameter("trade_mode");
				//交易类型、交易ID
				String  tradeType = resHandler.getParameter("tradeType");
				String  tradeId = resHandler.getParameter("tradeId");
				//判断签名及结果
				if(queryRes.isTenpaySign()&& "0".equals(retcode)){ 
//					System.out.println("id验证成功");
					if("1".equals(trade_mode)){ //即时到账 
						if( "0".equals(trade_state)){
					        //------------------------------
							//即时到账处理业务开始
							//------------------------------
//							System.out.println("交易成功-zpp测试");
							//处理数据库逻辑
							//注意交易单不要重复处理
							//注意判断返回金额（交易支付没有进行金额判断）
							if("1".equals(String.valueOf(tradeType))){//甲方支付给平台项目金额成功，tradeId：甲乙方交易记录ID
										resHandler.sendToCFT("success");
							}else if("2".equals(String.valueOf(tradeType))){//乙方支付给平台项目保证金成功，tradeId：甲乙方交易记录ID
										resHandler.sendToCFT("success");
							}else if("3".equals(String.valueOf(tradeType))){//商品购买，订单金额成功，tradeId：商品购买订单ID
								shoppingCartOrderService.saveOrUpdateToPaySuccess(tradeId,customer,shopInfo2,son,transaction_id);
								resHandler.sendToCFT("success");
							}
							//------------------------------
							//即时到账处理业务完毕
							//------------------------------
							//System.out.println("即时到账支付成功-状态并成功修改");
							//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
							//resHandler.sendToCFT("success");
						}else{
//							System.out.println("即时到账支付失败");
							resHandler.sendToCFT("fail");
						}
					}else if("2".equals(trade_mode)){    //中介担保
						//------------------------------
						//中介担保处理业务开始
						//------------------------------
						//处理数据库逻辑
						//注意交易单不要重复处理
						//注意判断返回金额
						int iStatus = TenpayUtil.toInt(trade_state);
						switch(iStatus) {
							case 0:		//付款成功
								break;
							case 1:		//交易创建
								break;
							case 2:		//收获地址填写完毕
								break;
							case 4:		//卖家发货成功
								break;
							case 5:		//买家收货确认，交易成功
								break;
							case 6:		//交易关闭，未完成超时关闭
								break;
							case 7:		//修改交易价格成功
								break;
							case 8:		//买家发起退款
								break;
							case 9:		//退款成功
								break;
							case 10:	//退款关闭
								break;
							default:
						}
						//------------------------------
						//中介担保处理业务完毕
						//------------------------------
//						System.out.println("trade_state = " + trade_state);
						//给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
						resHandler.sendToCFT("success");
					}
				}else{
					//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
//					System.out.println("查询验证签名失败或id验证失败"+",retcode:" + queryRes.getParameter("retcode"));
				}
			} else {
//				System.out.println("后台调用通信失败");
//				System.out.println(httpClient.getResponseCode());
//				System.out.println(httpClient.getErrInfo());
				//有可能因为网络原因，请求已经处理，但未收到应答。
			}
		}else{
//			System.out.println("通知签名验证失败");
		}
	}
	public void setShoppingCartOrderService(
			IShoppingCartOrderService shoppingCartOrderService) {
		this.shoppingCartOrderService = shoppingCartOrderService;
	}
	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}
	public void setSafetyCertificateService(
			ISafetyCertificateService safetyCertificateService) {
		this.safetyCertificateService = safetyCertificateService;
	}
}
