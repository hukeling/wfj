<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="util.tenpay.util.TenpayUtil" %>
<%@ page import="util.tenpay.RequestHandler"%>
<%@ include file = "config.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//财付通网关支付请求示例，商户按照此文档进行开发即可
//---------------------------------------------------------
 String tradeId = (String) request.getAttribute("tradeId");//交易ID
 String tradeMoney = (String) request.getAttribute("tradeMoney");//交易金额
 String describe = (String) request.getAttribute("describe");//支付金额的描述（目的、作用、意图）

 
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
reqHandler.setParameter("partner", partner);		        //商户号
reqHandler.setParameter("out_trade_no", tradeId);		//商家订单号（国桥甲乙方交易Id）
reqHandler.setParameter("total_fee", tradeMoney);			//商品金额,以分为单位（交易金额）
reqHandler.setParameter("return_url", return_url);		    //交易完成后跳转的URL
reqHandler.setParameter("notify_url", notify_url);		    //接收财付通通知的URL
reqHandler.setParameter("body", describe);	                    //商品描述（交易描述）
reqHandler.setParameter("bank_type", "DEFAULT");		    //银行类型(中介担保时此参数无效)
reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip，不是商户服务器IP
reqHandler.setParameter("fee_type", "1");                    //币种，1人民币
reqHandler.setParameter("subject", "交易名称");              //商品名称(中介交易时必填)

//系统可选参数
reqHandler.setParameter("sign_type", "MD5");                //签名类型,默认：MD5
reqHandler.setParameter("service_version", "1.0");			//版本号，默认为1.0
reqHandler.setParameter("input_charset", "utf-8");            //字符编码
reqHandler.setParameter("sign_key_index", "1");             //密钥序号


//业务可选参数
reqHandler.setParameter("attach", "");                      //附加数据，原样返回
reqHandler.setParameter("product_fee", "");                 //商品费用，必须保证transport_fee + product_fee=total_fee
reqHandler.setParameter("transport_fee", "0");               //物流费用，必须保证transport_fee + product_fee=total_fee
reqHandler.setParameter("time_start", currTime);            //订单生成时间，格式为yyyymmddhhmmss
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
String requestUrl = reqHandler.getRequestURL();

//获取debug信息,建议把请求和debug信息写入日志，方便定位问题
String debuginfo = reqHandler.getDebugInfo();
//System.out.println("requestUrl:  " + requestUrl);
//System.out.println("sign_String:  " + debuginfo);

out.print("requestUrl:  " + requestUrl + "<br><br>");
out.print("sign_String:  " + debuginfo + "<br><br>");
%>
<html>
<head>

<title>财付通即时到帐程序演示</title>
</head>
<body>
<br/><a target="_blank" href="<%=requestUrl%>">财付通支付</a>
</body>
</html>
