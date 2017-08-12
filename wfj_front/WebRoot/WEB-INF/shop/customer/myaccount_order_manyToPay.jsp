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
				var triggers = $('#edit-payway-alipay').overlay({
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
	<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15"><p class="p_iconJJ" style="width:220px;">订单信息</p></h3>
	<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
		<tr>
								<td width="15%">订单编号</td>
								<td width="20%">商品</td>
								<td width="11%">收货人</td>
								<!-- <td width="11%">
									金额
								</td> -->
								<%-- <s:if test="#session.customer.type!=3">
									<td width="10%">使用授信额度</td>
								</s:if>
								<s:else>
								</s:else> --%>
								<td width="10%">金额</td>
								<td width="7%">下单时间</td>
								<td width="8%">订单状态</td>
								<td width="8%">付款状态</td>
							</tr>
							<s:iterator value="ordersMap" var="listMap">
								<s:iterator value="#listMap.value" status="index">
									<s:if test="#listMap.value.size>1&&#index.index==0">
										<tr>
											<td
												style="font-size: 12px; border-bottom: 1px #d2d2d2 dashed; border-top: 1px #d2d2d2 dashed;"
												colspan="9" width="100%">
												尊敬的用户，由于你的商品不是在同一个店铺，您的订单被系统拆分为多个子订单分开配送，所产生的额外运费由SHOPJSP承担，给你带来不便请谅解。<br />
												<!-- 										<span style="float:right; padding-right:8px;"> -->
												<!-- 											<a href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.totalOrdersNo=<s:property value='totalOrdersNo' />"> -->
												<!-- 												<img src="${pageContext.request.contextPath }/common/shop/front/images/mergerPay.png"></img> -->
												<!-- 											</a> --> <!-- 										</span> -->
											</td>
										</tr>
									</s:if>
									<tr>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><a
											href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="ordersId" />"><s:property
													value="ordersNo" /> </a></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
											<div class="ClearBoth" style="text-align: center;">
												<s:iterator value="mapImg">
													<a target="_blank"
														href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
														<img style="height: 50px; width:50px;"
														title="<s:property value="productName"/>"
														alt="<s:property value="productName"/>"
														onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}images/blank.gif'"
														src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="thumbnail"/>" />
													</a>
												</s:iterator>
											</div>
										</td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:property
												value="consignee" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><span
											class="ColorRed FontSizeB">￥<fmt:formatNumber
													value="${finalAmount}" pattern="#,##0.00#" />
										</span></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:date
												name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><span>
												<s:set name="ordersState"
													value="<s:property value='ordersState' />" /> <s:if
													test="ordersState == 0">
													<p class="ColorGreennn" style="margin:0;">生成订单</p>
												</s:if> <s:elseif test="ordersState == 1">
													<span style="color: blue;"> 付款成功 </span>
												</s:elseif> <s:elseif test="ordersState == 3">
													<p style="color: green;">正在配货</p>
												</s:elseif> <s:elseif test="ordersState == 4">
													<p style="color: purple;">已经发货</p>
												</s:elseif> <s:elseif test="ordersState == 5">
													<p class="ColorGreennn">已收货</p>
												</s:elseif> <s:elseif test="ordersState == 6">
													<span style="color: fuchsia;"> 订单取消 </span>
												</s:elseif> <s:elseif test="ordersState == 7">
													<span style="color: fuchsia;"> 问题订单 </span>
												</s:elseif> <s:elseif test="ordersState == 9">
													<p class="ColorGreennn">已评价</p>
												</s:elseif>
										</span></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
												<s:set name="settlementStatus" value="<s:property value='settlementStatus' />" /> 
												<s:if test="settlementStatus==0">
													<span id="wfk_0" class="ColorGreennn" style="margin:0;" >未付款</span>
												</s:if> 
												<s:elseif test="settlementStatus==1">
													<span id="yfk_1" style="color: blue;" > 已付款 </span>
												</s:elseif></td>
									</tr>
								</s:iterator>
							</s:iterator>
	</table>
	
	</article>
	<article class="SellerInformation publicMarginTop15 publicMarginBot15">
		<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15"><p class="p_iconJJ" style="width:220px;">合计</p></h3>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSizeB">应付总金额:</p>
			<p>￥<fmt:formatNumber value="${totalFinalMoney}" pattern="#,##0.00#" /></p>
		</div>
	</article>
	 <div class="FontSize14 BackgroundE4Hui" style="padding:10px 0; height: 70px;"> 
     	<p class="text-right">
     		<%--<input name="" type="button" onclick="toTenPay();" 
     		class=" radius publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize16 strong"
								value="财付通" style="padding-bottom:4px;width: 80px;height: 30px;">
		--%>
<!-- 		<input name="" type="button" onclick="toPay('${totalOrdersNo}');" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" value="模拟支付" style="padding-bottom:4px;"> -->
		<jsp:include page="newPay.jsp"></jsp:include><!-- 支付宝 -->
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
<form id="payForm" target="_blank" action="${pageContext.request.contextPath}/quickBuck/quickBuckAPI.action" method="post">
	<input name="ordersId" type="hidden" value="${ordersIds}" />
	<!-- payType为支付类型，1为单笔订单支付 2为总订单支付 3为合并付款支付 -->
	<input name="paymentType" type="hidden" value="3" />
</form>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
