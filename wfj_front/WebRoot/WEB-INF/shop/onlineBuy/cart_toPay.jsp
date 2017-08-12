<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付成功</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<div class="cart_title">
    	<ul>
        	<li class="line-height4 strong">购物车</li>
            <li class="line-height4 strong">地址编辑</li>
            <li class="line-height4 strong">结算</li>
            <li class="title4 line-height4 strong"><a href="" class="Son">成功</a></li>
        </ul>
    </div>
   <div class="BackgroundRed height5 publicMarginTB20"></div>
<div class="registerSuccessBody Backgroundpink radius publicPadding20">
	<form action="${pageContext.request.contextPath}/front/customer/shoppingOnline/toPaySuccess.action" method="post">
	<div class="registerSuccessTitle  ColorRed">购买成功 !</div>
	<p class="FontSize14"><input type="hidden" name="ordersNo" value="${ordersNo}"/>你的订单号为:${ordersNo},可以去你的订单查询详情</p>
    <p class="FontSize14">只是为了走完流程，现在假设买家在付钱！</p>
	<p class="FontSize14">点击按钮为提交付钱，为了走完这个流程，跑数据，为了下一步，来吧！，点击我！</p>	
    <p><input type="submit" value="提交"/></p>
    <p><a href="${pageContext.request.contextPath}/" class="ColorBlue FontSize14">回到首页继续购物>></a></p>
	</form>
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
