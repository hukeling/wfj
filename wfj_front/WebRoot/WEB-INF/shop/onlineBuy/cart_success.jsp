<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生成订单页面</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
	<script type="text/javascript" >
		$(document).ready(function() {
			$("#to_alipay").click(function(){
				$('#edit-payway-alipay').overlay({
							mask: {
					        color: '#ebecff',
					        loadSpeed: 200,
					        opacity: 0.4
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
		//支付宝支付
		function toTenPay(){
			//提交支付表单
			$("#alipayForm").submit();
			//加载浮层
			$('#edit-payway-alipay').overlay().load();
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
<div class="cart_title" style="margin-left: 293px;">
    	<ul>
        	<li class="title4 strong ColorWhite1 " style="line-height:41px;">1.我的购物车</li>
            <li class="title4 strong ColorWhite1 " style="line-height:41px;">2.填写核对订单信息</li>
            <s:if test="ordersNo!=null">
            	<li class="title4 strong ColorWhite1 " style="line-height:41px;">3.成功提交订单</li>
            </s:if>
            <s:else>
            	<li class="title4 strong ColorWhite1 " style="line-height:41px;">3.订单提交失败</li>
            </s:else>
        </ul>
    </div>
   <div class="BackgroundRed height5 publicMarginTB20"></div>
<div class="registerSuccessBody Backgroundpink radius publicPadding20">
	<s:if test="ordersNo!=null">
		<div class="registerSuccessTitle  ColorRed">订单已生成 !</div>
		<p class="FontSize14">您可以去&nbsp;买家中心&gt;我的订单&nbsp;查询详情</p>
	    <p class="FontSize14">请您尽快完成付款，我们会尽快把商品送到你的手中</p>
	    <p class="FontSize14">
			<font>商城支持微信支付，请扫描下方二维码，或长按下方二维码，关注云商谷微信电商，进入微商城支付订单。</font>
		</p>
		<p style="text-align: center;">
			<img style="max-height: 230px;max-width: 230px;margin-top: 50px;" src="${fileUrlConfig.fileServiceUploadRoot}images/tu.jpg"/>
   		</p>
     		<!-- <input name="" type="button" onclick="toTenPay();"	class="radius publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize16 strong"
								value="立即支付" style="padding-bottom:4px;"/> -->

     		<s:if test="#session.customer.type==3">
     		 <a id="yOrder" class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong " onclick="toTenPay();" href="javascript:;" style="display:;width:80px;text-align:center;height:30px;line-height:30px;background-color:#d01743 !important ">快钱支付</a>
     		
     		<!--<input name="" type="button" onclick="toTenPay();" 
     		class="radius publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize16 strong"
								value="快钱支付" style="padding-bottom:4px;">-->
								&nbsp;&nbsp; 
     		 <input name="" type="button" onclick="toPay('${totalOrdersNo}');" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14"
								value="模拟支付" style="padding-bottom:4px;"/> 
			</s:if>
		
		<!-- <p class="FontSize14" style="margin-bottom:0;"><jsp:include page="newPay.jsp"></jsp:include></p> -->
 	</s:if>
 	<s:else>
 		<div class="registerSuccessTitle  ColorRed">订单没有生成 !</div>
		<p class="FontSize14">由于你的确认订单操作有误，导致无法生成订单！</p>
	    <p class="FontSize14">请返回购物车，重新确认订单！</p>
		<p class="FontSize14"></p>	
 	</s:else>
    <p><a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action" class="ColorBlue FontSize14">查看我的订单&nbsp;&gt;&gt;</a></p>
    <p><a href="${pageContext.request.contextPath}/" class="ColorBlue FontSize14">继续购物&nbsp; &gt;&gt;</a></p>
</div>
	<div class="modal" id="edit-payway-alipay" style="width: 300px;height:320px;">
		<h2 class="modal-title">
			支付
		</h2>
		<div class="modal-content">
		<p class="modal-buttons">
			<button  style="width:100px; background-color:#D01743 ;font-size:16px;color:#fff;font-weight:bold;" id="paySuccess">支付成功</button>
			<button class="close" style="width:100px; background-color:#D01743 ;font-size:16px;color:#fff;font-weight:bold;" id="payProblem">
				遇见问题
			</button>
		</p>
		</div>
	</div>
</div>
<%@ include file="../include/footer.jsp"%>
<!-- 快钱支付表单 -->
<form id="payForm" target="_blank" action="${pageContext.request.contextPath}/quickBuck/quickBuckAPI.action" method="post">
	<!-- 将需要操作的订单的总订单号传到后台 -->
	<input name="ordersId" type="hidden" value="${ordersNo}" />
	<!-- payType为支付类型，1为单笔订单支付 2为总订单支付 3为合并付款支付 -->
	<input name="paymentType" type="hidden" value="2" />
</form>
<!-- 支付宝支付表单 -->
<form id="alipayForm" target="_blank" action="${pageContext.request.contextPath}/front/customer/shoppingOnline/alipayapi.action" method="post">
	<!-- 将需要操作的订单的总订单号传到后台 -->
	<input type="hidden" name="WIDout_trade_no" value="${totalOrdersNo}"/>
	<input name="WIDbody" value="${OrdersNoAll}"/>
</form>
</body>
</html>
