<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付成功</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
	<script type="text/javascript" >
	function closewin(){
		self.opener=null;
		self.close();
	}
	function clock(){
		i=i-1;
		document.title="本窗口将在"+i+"秒后自动关闭!";
		if(i>0)setTimeout("clock();",1000);
		else closewin();
	}
	var i=10;
	clock();
</script> 
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<div class="cart_title">
    	<ul>
        	<li class="line-height4 strong">购物车</li>
            <li class="line-height4 strong">生成订单</li>
            <li class="title4 line-height4 strong">支付</li>
        </ul>
    </div>
   <div class="BackgroundRed height5 publicMarginTB20"></div>
<div class="registerSuccessBody Backgroundpink radius publicPadding20">
	<div class="registerSuccessTitle  ColorRed">支付成功 !</div>
	<p class="FontSize14">你的订单已经支付，我们会尽快安排配送商品，感谢你的支持。</p>
	<p class="FontSize14"></p>	
	<p><a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action" class="ColorBlue FontSize14">回到我的订单>></a></p>
    <p><a href="${pageContext.request.contextPath}/" class="ColorBlue FontSize14">继续购物>></a></p>
    
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
