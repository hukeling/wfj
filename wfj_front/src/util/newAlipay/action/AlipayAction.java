package util.newAlipay.action;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.action.BaseAction;
import util.newAlipay.config.AlipayConfig;
/**
 * 支付宝即时到账
 * @author mqr
 *
 */
public class AlipayAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	public String alipayapi(){
		try {
		//支付类型
			String payment_type = "1";
			//必填，不能修改
			//服务器异步通知页面路径
//			String notify_url = "http://192.168.1.33:8080/thshop/toPay/toPaySuccess.action";
			String notify_url =  new String(request.getParameter("WIDseller_notify_url").getBytes("ISO-8859-1"),"UTF-8");
			//需http://格式的完整路径，不能加?id=123这类自定义参数
			//页面跳转同步通知页面路径
//			String return_url = "http://192.168.1.33:8080/thshop/toPay/toPaySuccess.action"; 
			String return_url =  new String(request.getParameter("WIDseller_return_url").getBytes("ISO-8859-1"),"UTF-8");
			//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
			//卖家支付宝帐户
			String seller_email = new String(request.getParameter("WIDseller_email").getBytes("ISO-8859-1"),"UTF-8");
			//必填
			//商户订单号
			String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//商户网站订单系统中唯一订单号，必填
			//订单名称
			String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			//必填
			//付款金额
			String price = new String(request.getParameter("WIDprice").getBytes("ISO-8859-1"),"UTF-8");
			//必填
			//商品数量
			String quantity = "1";
			//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
			//物流费用
			String logistics_fee = "0.00";
			//必填，即运费
			//物流类型
			String logistics_type = "EXPRESS";
			//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
			//物流支付方式
			String logistics_payment = "SELLER_PAY";
			//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
			//订单描述
			String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
			//商品展示地址
			String show_url = new String(request.getParameter("WIDshow_url").getBytes("ISO-8859-1"),"UTF-8");
			//需以http://开头的完整路径，如：http://www.xxx.com/myorder.html
			//收货人姓名
			String receive_name = new String(request.getParameter("WIDreceive_name").getBytes("ISO-8859-1"),"UTF-8");
			//如：张三
			//收货人地址
			String receive_address = new String(request.getParameter("WIDreceive_address").getBytes("ISO-8859-1"),"UTF-8");
			//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号
			//收货人邮编
			String receive_zip = new String(request.getParameter("WIDreceive_zip").getBytes("ISO-8859-1"),"UTF-8");
			//如：123456
			//收货人电话号码
			String receive_phone = new String(request.getParameter("WIDreceive_phone").getBytes("ISO-8859-1"),"UTF-8");
			//如：0571-88158090
			//收货人手机号码
			String receive_mobile = new String(request.getParameter("WIDreceive_mobile").getBytes("ISO-8859-1"),"UTF-8");
			//默认支付方式
			String paymethod = "bankPay";
			//必填
			//默认网银
			String defaultbank = new String("ICBCBTB".getBytes("ISO-8859-1"),"UTF-8");
			//必填，银行简码请参考接口技术文档
			//如：13312341234
			//////////////////////////////////////////////////////////////////////////////////
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "trade_create_by_buyer");//trade_create_by_buyer标准双接口  create_partner_trade_by_buyer担保交易
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("seller_email", seller_email);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("paymethod", paymethod);
			sParaTemp.put("defaultbank", defaultbank);
			sParaTemp.put("subject", util.other.Escape.unescape(subject));
			sParaTemp.put("price", price);
			sParaTemp.put("quantity", quantity);
			sParaTemp.put("logistics_fee", logistics_fee);
			sParaTemp.put("logistics_type", logistics_type);
			sParaTemp.put("logistics_payment", logistics_payment);
			sParaTemp.put("body", body);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("receive_name", receive_name);
			sParaTemp.put("receive_address", receive_address);
			sParaTemp.put("receive_zip", receive_zip);
			sParaTemp.put("receive_phone", receive_phone);
			sParaTemp.put("receive_mobile", receive_mobile);
			request.setAttribute("sParaTemp", sParaTemp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
