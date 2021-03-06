
<%
	/* *
	 *功能：即时到账交易接口接入页
	 *版本：3.3
	 *日期：2012-08-17
	 *说明：
	 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
	<head>
		<title>
			<%--支付宝即时到账交易接口--%>
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<style>
* {
	margin: 0;
	padding: 0;
}

ul,ol {
	list-style: none;
}

.title {
	color: #ADADAD;
	font-size: 14px;
	font-weight: bold;
	padding: 8px 16px 5px 10px;
}

.hidden {
	display: none;
}

.new-btn-login-sp {
	border: 1px solid #D74C00;
	padding: 1px;
	display: inline-block;
}

.new-btn-login {
	background-color: transparent;
	background-image:
		url("${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/new-btn-fixed.png")
		;
	border: medium none;
}

.new-btn-login {
	background-position: 0 -198px;
	width: 82px;
	color: #FFFFFF;
	font-weight: bold;
	height: 28px;
	line-height: 28px;
	padding: 0 10px 3px;
}

.new-btn-login:hover {
	background-position: 0 -167px;
	width: 82px;
	color: #FFFFFF;
	font-weight: bold;
	height: 28px;
	line-height: 28px;
	padding: 0 10px 3px;
}

.bank-list {
	overflow: hidden;
	margin-top: 5px;
}

.bank-list li {
	float: left;
	width: 153px;
	margin-bottom: 5px;
}

#main {
	width: 750px;
	margin: 0 auto;
	font-size: 14px;
	font-family: '宋体';
}

#logo {
	background-color: transparent;
	background-image:
		url("${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/new-btn-fixed.png")
		;
	border: medium none;
	background-position: 0 0;
	width: 166px;
	height: 35px;
	float: left;
}

.red-star {
	color: #f00;
	width: 10px;
	display: inline-block;
}

.null-star {
	color: #fff;
}

.content {
	margin-top: 5px;
}

.content dt {
	width: 160px;
	display: inline-block;
	text-align: right;
	float: left;
}

.content dd {
	margin-left: 100px;
	margin-bottom: 5px;
}

#foot {
	margin-top: 10px;
}

.foot-ul li {
	text-align: center;
}

.note-help {
	color: #999999;
	font-size: 12px;
	line-height: 130%;
	padding-left: 3px;
}

.cashier-nav {
	font-size: 14px;
	margin: 15px 0 10px;
	text-align: left;
	height: 30px;
	border-bottom: solid 2px #CFD2D7;
}

.cashier-nav ol li {
	float: left;
}

.cashier-nav li.current {
	color: #AB4400;
	font-weight: bold;
}

.cashier-nav li.last {
	clear: right;
}

.alipay_link {
	text-align: right;
}

.alipay_link a:link {
	text-decoration: none;
	color: #8D8D8D;
}

.alipay_link a:visited {
	text-decoration: none;
	color: #8D8D8D;
}
</style>
	</head>
	<body text=#000000 bgColor="#ffffff" leftMargin=0 topMargin=4>
		<div id="main">
			<form id="alipayment" name="alipayment" action="${pageContext.request.contextPath}/alipay/alipayapi.action" method="post" target="_blank">
				<input name="WIDseller_notify_url" type="hidden" value="${application.keybook['orderPayAJAXURL'][0].value}"/>
				<input name="WIDseller_return_url" type="hidden" value="${application.keybook['orderPayGETURL'][0].value}"/>
				<div id="body" style="clear: left">
					<dl class="content" style="display: none">
						<dt>
							卖家支付宝帐户：
						</dt>
						<dd >
							<span class="null-star">*</span>
							<input size="30" name="WIDseller_email" value="${application.keybook['alipayEmail'][0].value}"/>
							<span>必填</span>
						</dd>
						<dt>
							商户订单号：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDout_trade_no" value="${ordersNo}"/>
							<span>商户网站订单系统中唯一订单号，必填</span>
						</dd>
						<dt>
							订单名称：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDsubject" value="${payMap.subject}"/>
							<span>必填 </span>
						</dd>
						<dt>
							付款金额：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDprice" value="${payMap.payAmount}"/>
							<span>必填 </span>
						</dd>
						<dt>
							订单描述 ：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDbody" value=""/>
							<span></span>
						</dd>
						<dt>
							商品展示地址：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDshow_url" value=""/>
							<span>需以http://开头的完整路径，如：http://www.xxx.com/myorder.html </span>
						</dd>
						<dt>默认网银：</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDdefaultbank" value="${payMap.defaultbank}" />
							<span>必填，银行简码请参考接口技术文档</span>
					</dd>
					<dt>支付方式：</dt>
					<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIPaymethod" value="${payMap.paymethod}" />
							<span>必填，银行简码请参考接口技术文档</span>
					</dd>
						<dt>
							收货人姓名：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDreceive_name" value="${payMap.consignee}"/>
							<span>如：张三 </span>
						</dd>
						<dt>
							收货人地址：
						</dt>
						<dd >
							<span class="null-star">*</span>
							<input size="30" name="WIDreceive_address" value="${payMap.address}"/>
							<span>如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号 </span>
						</dd>
						<dt>
							收货人邮编：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDreceive_zip"  value="${payMap.postcode}"/>
							<span>如：123456 </span>
						</dd>
						<dt>
							收货人电话号码：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDreceive_phone" value="${payMap.phone}"/>
							<span>如：0571-88158090 </span>
						</dd>
						<dt>
							收货人手机号码：
						</dt>
						<dd>
							<span class="null-star">*</span>
							<input size="30" name="WIDreceive_mobile" value="${payMap.mobilePhone}"/>
							<span>如：13312341234</span>
						</dd>
						<dt></dt>
					</dl>
						<dd>
							<span class="new-btn-login-sp">
								<button class="new-btn-login" type="submit" id="to_alipay" style="text-align: center;">立即支付</button> </span>
						</dd>
				</div>
			</form>
			<%--<div id="foot">
			<ul class="foot-ul">
				<li><font class="note-help">如果您点击“确认”按钮，即表示您同意该次的执行操作。 </font></li>
				<li>
					支付宝版权所有 2011-2015 ALIPAY.COM 
				</li>
			</ul>
		</div>
	--%>
		</div>
	</body>
</html>