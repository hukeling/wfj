<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<title>店铺首页</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javaScript" language="javascript">
//更改成正在配货
function changeOrderState(ordersId){
	window.location = "${pageContext.request.contextPath}/front/store/shopOrder/changgeOrdersState.action?loginHome=1&orders.ordersId="+ ordersId+"&currentPage=${currentPage}";
}
</script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/jquery.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/jq.js"></script>
<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->

<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/wfj.css" />

<style>
* {
	margin: 0;
	padding: 0px;
}

a {
	text-decoration: none;
}

img {
	border: none;
}

li {
	list-style: none;
}

body {
	background-color: #f9f9f9;
	font-family: "微软雅黑";
}
</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="margin-center PublicWidth1004">
<div class="ClearBoth">
<%@ include file="../include/left_shop.jsp"%>
<!--right-->
<div id="rightBox" class="float-right publicHidden">
<section>
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店家首页</h3>
<div class="person ClearBoth">
<div class="person_left">
	<a href="#">
		<img width="115" height="115" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot'/><s:property value='shopInfo.logoUrl'/>"/>
	</a>
</div>
<div class="person_right">
<div class="saller ClearBoth"><div style="width:100px; margin-right:10px;" class="float-left text-right">店家编号：</div> <div class="float-left"><s:property value="shopInfo.shopInfoId"/></div></div>
<div class="saller ClearBoth"><div style="width:100px; margin-right:10px;" class="float-left text-right">店家账号：</div> <div class="float-left"><s:property value="customer.loginName"/></div></div>
<div class="saller ClearBoth"> <div style="width:100px; margin-right:10px;" class="float-left text-right">店铺名称：</div> <div class="float-left"><s:property value="shopInfo.shopName"/></div></div>
</div>
</div>
<div style="height:auto;width:950px;color: #333333;font-family: 宋体;font-size: 13px;font-weight: bold;line-height: 28px;overflow: hidden;">
	<div style="width:100px; margin-right:10px;">店铺简介：</div> 
	<div class="float-left">
	<p style="width:950px;height:auto;font-size:12px;font-weight:200;line-height:24px;">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="shopInfo.synopsis"/>
	</p></div>
</div>
<div class="publicrelative ClearBoth">
<p style="width:280px;">等待配货订单</p>
<!--<a href="" class="publicPadding5_10 ColorBlue publicunderline FontSize14 publicabsolute publicTop10 publicRight publicinline">Order Detail</a>-->
</div>
<table width="100%" border="0" class="RecentlyOrdersTable_lj publicMarginTop15" style="margin-bottom:50px; border-bottom: 0;">
  <tr class="gradient">                                                                                            
    <td width="20%" style="border-bottom:1px solid #d2d2d2;">订单号</td>
    <td width="18%" style="border-bottom:1px solid #d2d2d2;">时间</td>
    <td width="14%" style="border-bottom:1px solid #d2d2d2;">买家</td>   
    <td width="10%" style="border-bottom:1px solid #d2d2d2;">金额</td>
    <td width="10%" style="border-bottom:1px solid #d2d2d2;">状态</td>
    <td width="14%" style="border-bottom:1px solid #d2d2d2;">操作</td>
  </tr>
  <s:iterator value="waitOrdersList" >
  <tr>
    <td><s:property value="ordersNo"/></td>
    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
    <td><s:property value="loginName"/></td>
    <td>￥<s:property value="finalAmount"/></td>
    <td><s:if test="settlementStatus==1">已支付</s:if><s:else>未支付</s:else></td>  
    <td class="line-height2 lastR">
    <%-- 	<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=<s:property value="ordersId" />" class="ROButton publicblock BackgroundPurple ColorWhite1 radius">详情</a> --%>
    	<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;">订单明细</a>
    	<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderPrint.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;" target="_blank" >打印</a>
    	<a href="javascript:;" onclick="changeOrderState(<s:property value="ordersId" />);" class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已配货</a>
    	</td>
  </tr>
  </s:iterator>
</table>
<!--分页 start-->
<%-- <div class="pageList strong ClearBoth">
	<jsp:include page="../../util/splitPage.jsp" />
</div> 
--%>
<!--分页 end-->
<p style="width:280px;">订单状态</p>
<div style="width:793px; height:auto; overflow:hidden; padding-bottom:30px;" class="ClearBoth">
<div class="saller" style="padding-top:5px; width:auto; float:left; margin-right:60px; display:inline;">
	<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action?orders.ordersState=1">已付款(<span style="color:#0001fe; text-decoration:underline;"><s:if test="ordersHomeStatus.waitingNum!=null"><s:property value="ordersHomeStatus.waitingNum"/></s:if><s:else>0</s:else></span>)</a></div>
<!--<div class="saller" style="padding-top:5px; width:auto; float:left; margin-right:138px; display:inline;">Complaint(<span style="color:#0001fe; text-decoration:underline;"><s:property value="ordersHomeStatus.complaintNum"/></span>)</div>-->
<!--<div class="saller" style="padding-top:5px; width:auto; float:left;"><a href="${pageContext.request.contextPath}/front/store/shopOrder/ordersByStatus.action?flag=8">Refund(<span style="color:#0001fe; text-decoration:underline;"><s:property value="ordersHomeStatus.refundNum"/></span>)</a></div>-->

</div>  
<p style="width:280px;" class="publicMarginTop15">商品状态</p>
<div style="width:793px; height:auto; overflow:hidden; padding-bottom:30px;" class="ClearBoth">
      <div class="saller" style="padding-top:5px; width:auto; float:left; margin-right:60px; display:inline;"><a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoPutawayProductListInfoPage.action">上架(<span style="color:#0001fe; text-decoration:underline;"><s:if test="ordersHomeStatus.onShelfNum!=null"><s:property value="ordersHomeStatus.onShelfNum"/></s:if><s:else>0</s:else></span>)</a></div>
      <div class="saller" style="padding-top:5px; width:auto; float:left; margin-right:138px; display:inline;"><a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoUndercarriageProductListPage.action">下架(<span style="color:#0001fe; text-decoration:underline;"><s:if test="ordersHomeStatus.offShelfNum!=null"><s:property value="ordersHomeStatus.offShelfNum"/></s:if><s:else>0</s:else></span>)</a></div>
</div>
<%-- <p class="p_iconJJ" style="width:280px;" class="publicMarginTop15">活动状态</p>
<div style="width:793px; height:auto; overflow:hidden; padding-bottom:30px;" class="ClearBoth">
<div class="saller" style="padding-top:5px; width:auto; float:left; margin-right:60px; display:inline;"><a href="${pageContext.request.contextPath}/front/store/platformPromotion/gotoPlatformPromotionListPage.action?shopInfoId=1&pageIndex=1">活动(<span style="color:#0001fe; text-decoration:underline;"><s:if test="ordersHomeStatus.activityNum!=null"><s:property value="ordersHomeStatus.activityNum"/></s:if><s:else>0</s:else></span>)</a></div>
</div>  --%> 
</section>
</div>
<!--//right-->
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
