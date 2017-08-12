<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付成功</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
</head>
<script language="javascript">
	function closewin(){
		self.opener=null;
		self.close();
	}
	function clock(){
		i=i-1
		document.title="本窗口将在"+i+"秒后自动关闭!";
		if(i>0)setTimeout("clock();",1000);
		else closewin();
	}
	var i=10
	clock();
</script> 
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<div class="registerSuccessBody Backgroundpink radius publicPadding20">
	<div class="registerSuccessTitle  ColorRed">支付成功 !</div>
	<p class="FontSize14">您所购买的SHOPJSP币已经到账，感谢您的支持。</p>
	<p class="FontSize14"></p>	
	<p><a href="${pageContext.request.contextPath}/front/customer/brokerageManage/getSuperTerminalVirtualCoinDetail.action" class="ColorBlue FontSize14">回到我的SHOPJSP币>></a></p>
    <p><a href="${pageContext.request.contextPath}/front/customer/thcions/gotobuyTHCoins.action" class="ColorBlue FontSize14">继续购买SHOPJSP币>></a></p>
    
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
