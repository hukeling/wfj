<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户订单支付页面</title>
<%@ include file="../include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#to_alipay").click(function(){
			    $('#edit-payway-alipay').overlay({
					mask: {
				        color: '#ebecff',
				        loadSpeed: 200,
				        opacity: 0.5					      
			        },
					closeOnEsc:false,
					closeOnClick: false
				});
				$('#edit-payway-alipay').overlay().load();//加载浮层
				$("#payProblem").click(function(){
					window.location="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action";
				});
				$("#paySuccess").click(function(){
					window.location="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action";
				});
			});
		});
		//通过财付通支付
		function toTenPay(){
			$("#payForm").submit();
			$('#edit-payway-alipay').overlay().load();//加载浮层
			$("#payProblem").click(function(){
				window.location="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action";
			});
			$("#paySuccess").click(function(){
				window.location="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action";
			});
		}
		//模拟支付
		function toPay(tradeId){
			window.location="${pageContext.request.contextPath}/front/customer/shoppingOnline/toPay.action?tradeId="+tradeId;
		}
	</script>
</head>
<body>
<%@ include file="../include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<article class="publicrelative publicMarginBot15 publicPaddingB30">
	<article class="SellerInformation publicMarginTop15 publicMarginBot15">
		<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15"><p style="width:220px;color: #d01743;font-weight: bold; ">收货人信息</p></h3>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">收货人:</p>
			<p>${consignee}</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">收货地址:</p>
			<p>${address}</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">联系方式:</p>
			<p>${orders.mobilePhone}</p>
		</div>
	</article>

	<article class="SellerInformation publicMarginTop15 publicMarginBot15">
		<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15"><p style="width:220px;color: #d01743;font-weight: bold;">订单信息</p></h3>
		
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">订单编号:</p>
			<p>${orders.ordersNo}</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">下单时间:</p>
			<p><s:date name="orders.createTime" format="yyyy-MM-dd HH:mm:ss"/></p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">运费:</p>
			<p>￥<fmt:formatNumber value="${orders.freight}" pattern="#,##0.00#" /></p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">付款方式:</p>
			<%-- <p><s:if test="payMode==1">快钱</s:if></p> --%>
			<p><s:if test="orders.payMode==2">支付宝</s:if><s:else>在线支付</s:else></p>
		</div>
		<%-- <div class="ClearBoth">
<!-- 		<div class="ClearBoth"> -->
<!-- 			<p class="widthpx150 text-right FontSizeB">使用SHOPJSP币:</p> -->
<!-- 			<p>使用SHOPJSP币数量：${orders.userCoin}，抵扣金额为：- ${orders.changeAmount}</p> -->
<!-- 		</div> --> --%>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">-抵扣金额:</p>
			<p>￥<fmt:formatNumber value="${orders.changeAmount}" pattern="#,##0.00#" /></p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">付款金额:</p>
			<p>￥<fmt:formatNumber value="${orders.finalAmount}" pattern="#,##0.00#" /></p>
		</div>
	</article>
	
	<article class="SellerInformation publicMarginTop15 publicMarginBot15">
	<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15"><p style="width:220px;color: #d01743;font-weight: bold;">产品信息</p></h3>
	<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
		<tr>
			<td>店铺</td>
			<td>商品</td>
			<td>品牌</td>
			<td>单价</td>
			<td>数量</td>
			<td>小计</td>
		</tr>
		<s:iterator value="listProduct" var="prod">
		<tr>
			<td><a class="ColorBlue" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="#prod.shopInfoId"/>"><s:property value="#prod.shopName"/></a></td>
			<td><a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="#prod.productId" />.html"><img title="${prod.productFullName}" src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot+#prod.logoImg'/>" style="height:80px; width:160px;"/><s:property value="#prod.logoImage"/></a></td>
			<td><s:property value="#prod.brandName"/></td>
			<td>￥<fmt:formatNumber value="${prod.salesPrice}" pattern="#,##0.00#" /></td>
			<td><s:property value="count"/></td>
			<td>￥<fmt:formatNumber value="${prod.salesPrice*prod.count+prod.freightPrice}" pattern="#,##0.00#" /></td>
		</tr>
		</s:iterator>
	</table>
	</article>
	<!--  <div class="FontSize14 BackgroundE4Hui" style="padding:10px 0; height: 70px;">  -->
	 <div class="FontSize14" height: 70px;"> 
		<p class="text-right">
		<%--<input name="" type="button" onclick="toTenPay();" 
		class=" radius publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize16 strong"
								value="财付通" style="padding-bottom:4px;width: 80px;height: 30px;">
		<input name="" type="button" onclick="toPay('${orders.ordersNo}');" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" value="模拟支付" style="padding-bottom:4px;">
		--%>
		<jsp:include page="newPay.jsp"></jsp:include><!-- 支付宝     -->	
		<!-- <a id="yOrder" class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong " onclick="toTenPay();" href="javascript:;" style="display:;width:80px;text-align:center;height:30px;line-height:30px;background-color:#d01743 !important;margin-right: 20px;">快钱支付</a> -->
		</p>
		
     </div>
</article>
	<div class="modal" id="edit-payway-alipay" style="width: 300px;height: 100px;">
		<h2 class="modal-title">
			支付
		</h2>
		<div class="modal-content">
		<p class="modal-buttons">
			<button style="width:100px; background-color:#D01743 ;font-size:16px;color:#fff;font-weight:bold;"   class="close" id="paySuccess">支付成功</button>
			<button style="width:100px; background-color:#D01743 ;font-size:16px;color:#fff;font-weight:bold;"  class="close" id="payProblem">
				遇见问题
			</button>
		</p>
	</div>
</div>
</div>
<!-- 支付表单 -->
<%-- <form id="payForm" target="_blank" action="${pageContext.request.contextPath}/quickBuck/quickBuckAPI.action" method="post">
	<input name="ordersId" type="hidden" value="${orders.ordersId}" />
	<!-- payType为支付类型，1为单笔订单支付 2为总订单支付 3为合并付款支付 -->
	<input name="paymentType" type="hidden" value="1" />
</form> --%>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
