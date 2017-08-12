<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户中心</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript">
			
			//跳转我的优惠券
			function gotoMyCoupon(x){
				if(x != null){
					location.href="${pageContext.request.contextPath}/front/customer/frontMyCoupon/gotoMyCouponPage.action";
				}else{
					location.href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action";
				}
			}
			//取消订单是触发函数 防止重复提交
			function turnCheck(value){
				var url="${pageContext.request.contextPath}/front/customer/myOrders/cancelOrder.action?orders.ordersId="+value;
				lconfirm("提醒","确定取消订单!",function(o){
						  window.location.href=url;
				});
			}
			//确认收货前的提示
			var affirmOption = function(ordersId){
				lconfirm("提醒","请确认己收到货物，否则将财物两空！",function(o){
					var url='${pageContext.request.contextPath}/front/customer/myOrders/confirmOrder.action?orders.ordersId='+ordersId;
					window.location.href = url;
				});
			}
		</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<s:if test="#session.customer.type==1">
					<%@ include file="../include/left_account.jsp"%>
				</s:if>
				<s:elseif test="#session.customer.type==3">
					<%@ include file="../include/left_account_gr.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">用户中心</h3>
					<div class="ClearBoth">
						<figure class="float-left">
							<a href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action">
							<img style="height:120px; width:120px;" class="accountFigure" onerror="this.src='${fileUrlConfig.visitFileUploadRoot}<s:property value="+productInfo.logoImg+"/>'" id="member-img" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='customer.photoUrl'/>" /></a>
						</figure>
						<aside class="float-left publicMarginL10 accountCenter_info publicHidden" style="width: 520px;">
						<div class="publicPaddingTB3 ClearBoth" >
							<p class="FontSize16 publicMarginR20">
								用户 ID: ${customer.loginName}
							</p>
							<p>
								<s:if test="#session.customer.type==3">
									<a href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action" class="ColorBlue FontSize13">详细信息管理</a>
								</s:if>
								<s:elseif test="#session.customer.type==1">
									<s:if test="#session.sonaccount==null">
										<a href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action" class="ColorBlue FontSize13">详细信息管理</a>
									</s:if>
								</s:elseif>
							</p>
							<p>
								<s:if test="#session.customer.type==3">
									<a href="${pageContext.request.contextPath}/front/customer/Setting/address.action" class="ColorBlue FontSize13">收货地址管理</a>
								</s:if>
								<s:elseif test="#session.customer.type==1">
									<s:if test="#session.sonaccount==null">
										<a href="${pageContext.request.contextPath}/front/customer/Setting/address.action" class="ColorBlue FontSize13">收货地址管理</a>
									</s:if>
									<s:elseif test="#session.sonaccount!=null&&#session.sonaccount.type==1">
										<a href="${pageContext.request.contextPath}/front/customer/Setting/address.action" class="ColorBlue FontSize13">收货地址管理</a>
									</s:elseif>
								</s:elseif>
							</p>
						</div>
						<div class="publicPaddingTB3 ClearBoth labelDiv">
							<p class="FontSize16 publicMarginR20" style="text-align: left; width:auto;">
								您的订单:
							</p>
							<p style=" margin-left:0;">
								<s:if test="#session.sonaccount!=null">
									<s:if test="#session.sonaccount.type==2">
										<a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action?orders.ordersState=0" class="ColorBlue FontSize13">未付款(<span
										class="ColorRed">${unpaid}</span>)</a>
									</s:if>
									<s:if test="#session.sonaccount.type==1">
										<a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action?orders.ordersState=4" class="ColorBlue FontSize13">发货(<span
										class="ColorRed">${shipped}</span>)</a>
									</s:if>
								</s:if>
								<s:else>
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action?orders.ordersState=0" class="ColorBlue FontSize13">未付款(<span
										class="ColorRed">${unpaid}</span>)</a>
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action?orders.ordersState=4" class="ColorBlue FontSize13">发货(<span
										class="ColorRed">${shipped}</span>)</a>
								</s:else>
							</p>
						</div>
						</aside>
						<s:if test="#request.customerServiceInfo!=null&&#request.customerServiceInfo.size>0">
							<div style="width:150px; height:150px; float:left;">
							  <ul style="width:150px; height:122px; float:left;">
							    <li style="width:130px; float:left; height:22px; line-height:22px;padding:0 10px;">客服：<s:property value='#request.customerServiceInfo.nikeName'/></li>
							    <li style="width:130px; float:left; height:22px; line-height:22px;padding:0 10px;">编号：<s:property value='#request.customerServiceInfo.workNumber'/></li>
							    <li style="width:130px; float:left; height:22px; line-height:22px;padding:0 10px;">QQ：<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=<s:property value='#request.customerServiceInfo.qq'/>&site=qq&menu=yes"><img border="0"  src="http://wpa.qq.com/pa?p=2:12345678:51" ></a></li>
							    <li style="width:130px; float:left; height:22px; line-height:22px;padding:0 10px;">电话：<s:property value='#request.customerServiceInfo.phone'/></li>
							    <li style="width:130px; float:left; height:22px; line-height:22px;padding:0 10px;">手机：<s:property value='#request.customerServiceInfo.mobile'/></li>
							  </ul>
							  </div>
							  <div style="width:150px; height:150px; float:left;">
							  <figure class="float-left">
								<a href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action">
								<img style="height:120px; width:120px;" class="accountFigure" onerror="this.src='${fileUrlConfig.visitFileUploadRoot}<s:property value="+productInfo.logoImg+"/>'" id="member-img" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#request.customerServiceInfo.photoUrl'/>" /></a>
							</figure>
							</div>
						</s:if>
					</div>
					<br/>
					<section>
					<h4 class=" ClearBoth SectionH4" style="background-color: #f2f2f2; height:32px; line-height:32px;">
						<span class="FontSize15 publicinline line-height2" style="float:left;">我的购物车</span>
						<a href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action"
							class="publicinline float-right publicPadding5_10 BackgroundPurple ColorWhite1 FontSize14">我的购物车</a>
					</h4>
					<ul class="account_MyCart">
					<s:iterator value="cartList" var="c">
						<li class="ClearBoth">
							
							<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${c.productId}.html" style=" display:block; width:100px; height:100px; float:left; margin:0 20px 0 0;">
							  <img src="${application.fileUrlConfig.visitFileUploadRoot}/${c.logoImg}" onerror="this.src='${fileUrlConfig.visitFileUploadRoot}<s:property value="+productInfo.logoImg+"/>'"  />
 							</a> 
							<div>
								<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${c.productId}.html" class="FontSize13 FontSizeB">${c.productFullName}</a>
								<span class="ColorQHui publicMarginT5 publicblock">
									<s:if test="#c.describle!=null&&#c.describle!=''">
											商品描述:${c.describle}
									</s:if>
								</span>
								<a target="_blank" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${c.shopInfoId}" class="ColorBlue publicinline line-height3">
								<i class="icon iconShop01"></i>${c.shopName}</a>
							</div>
							<div class="ColorRed FontSize18">
								￥ ${c.salesPrice}
							</div>
						</li>
					</s:iterator>
					</ul>
					</section>
					<section>
					<h4 class="gradient ClearBoth SectionH4 publicMarginTop15" style="height: 32px;">
						<span class="FontSize15 publicinline line-height2" style="float:left;">近期订单
						</span>
						
						<a style="margin-top:5px;" href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action"
							class="publicinline float-right publicPadding5_10 ColorBlue FontSize14">更多
							>>
						</a>
						
					</h4>
					<table width="100%" border="0"
						class="RecentlyOrders_table publicMarginT10">
						<tr>
							<td width="15%">
								订单编号
							</td>
							<td width="11%">
								商品
							</td>
							<td width="11%">
								收货人
							</td>
							<td width="11%">
								金额
							</td>
							<td width="12%">
								订单日期
							</td>
							<td width="11%">
								订单状态
							</td>
							<td width="11%">
								付款状态
							</td>
							<td width="10%">
								操作
							</td>
						</tr>
					<s:iterator value="orderList" var="o">
						<tr>
							<td>
								<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">${o.ordersNo}</a>
							</td>
							<td>
								<div class="ClearBoth" style="text-align: center;">
								  <s:set value="%{orderProductList.get(#o.ordersId)}" var="pi"></s:set>
								  <s:iterator value="#pi" var="p">
								  	<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${p.productId}.html"><img style="height:50px; width:50px;" title="${p.productFullName}" alt="${p.productFullName}" src="${application.fileUrlConfig.visitFileUploadRoot}${p.logoImg}"   onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"  /></a>
								  </s:iterator>
								</div>
							</td>
							<td>
								${o.consignee}
							</td>
							<td>
								<span class="ColorRed FontSizeB">${o.finalAmount}</span>
							</td>
							<td>
								<s:date name="#o.createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:if test="#o.ordersState == 0"><span class="ColorRed">生成订单</span></s:if>
								<s:elseif test="#o.ordersState==1"><span class="ColorGreen">付款成功</span></s:elseif>
								<s:elseif test="#o.ordersState==3"><span class="ColorPurple">已配货</span></s:elseif>
								<s:elseif test="#o.ordersState==4"><span class="ColorGreen">已发货</span></s:elseif>
								<s:elseif test="#o.ordersState==5"><span class="ColorGreen">完成</span></s:elseif>
								<s:elseif test="#o.ordersState==6"><span class="ColorRed">订单取消</span></s:elseif>
								<s:elseif test="#o.ordersState==7"><span class="ColorRed">问题订单</span></s:elseif>
								<s:elseif test="#o.ordersState==9"><span class="ColorRed">评价</span></s:elseif>
							</td>
							<td>
								<s:if test="#o.settlementStatus == 0"><span class="ColorRed">未付款</span></s:if>
								<s:elseif test="#o.settlementStatus==1"><span class="ColorGreen">已付款</span></s:elseif>
							</td>
							<td>
								<s:if test="ordersState==0">
									<s:if test="settlementStatus==0">
										<a href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.ordersId=<s:property value='ordersId' />">立即付款</a><br />
										<a href="javascript:turnCheck(<s:property value='ordersId' />);">取消订单</a><br />
									</s:if>
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
								</s:if>
								<s:elseif test="ordersState == 4">
									<s:if test="settlementStatus==1">
										<a  href="javascript:affirmOption(<s:property value='ordersId' />);">确认收货</a>
										<br />
									</s:if>
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
								</s:elseif>
								<s:elseif test="ordersState == 5">
									<s:if test="settlementStatus==1">
										<a href="${pageContext.request.contextPath}/front/customer/orderComment.action?order.ordersId=<s:property value='ordersId' />">评价</a>
										<br />
									</s:if>
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
									<br/>
									<s:if test="settlementStatus==1">
										<a href="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/gotoCustomerComplaintsPage.action?ordersId=<s:property value='ordersId' />">退/换货</a>
									</s:if>
								</s:elseif>
								<s:elseif test="ordersState == 9">
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
									<br/>
									<s:if test="settlementStatus==1">
										<a href="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/gotoCustomerComplaintsPage.action?ordersId=<s:property value='ordersId' />">退/换货</a>
									</s:if>
								</s:elseif>
								<s:else><!-- 付款之后，异常订单，取消订单 -->
									<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
								</s:else>
							</td>
						</tr>
				</s:iterator>
					</table>

				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
