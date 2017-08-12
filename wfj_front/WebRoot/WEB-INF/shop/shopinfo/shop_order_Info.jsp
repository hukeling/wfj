<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单详情</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<style type="text/css">
			.discProd{width:220px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;}
		</style>
		<SCRIPT type="text/javascript">
			$(function () {
				$("#deliver-goods-form").validate({meta: "validate", 
		    	      submitHandler:function(form){
						form.submit();
		    	      }
				});
			});
		</SCRIPT>
		<style>
			.yjfhr{color:red;background-color: #FF0000;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
			.yjfhr2{color:red;background-color: #3C89FF;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
		</style>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<article class="publicrelative publicMarginBot15 publicPaddingB30">
					
					<div style="background: #ffe0cb; border:1px solid #ff9144; padding-left:15px;">
						<h1 class="FontSize15 FontSizeB" style="  font-weight:bold;  height:30px; line-height:30px;">
						订单号：${orders.ordersNo}					
						</h1>												
						<div class="publicabsolute text-right FontSize15 FontSizeB" style=" top:7px; left:274px; font-weight:bold;">订单状态:
							<s:if test="orders.ordersState==0">生成订单</s:if>
							<s:if test="orders.ordersState==3">等待发货</s:if>
							<s:if test="orders.ordersState==4">已发货</s:if>
							<s:if test="orders.ordersState==5">已收货</s:if>
							<s:if test="orders.ordersState==6">订单取消</s:if>
							<s:if test="orders.ordersState==7">问题订单</s:if>
							<s:if test="orders.ordersState==9">已评价</s:if>
						</div>
						<div class="publicabsolute text-right FontSize15 FontSizeB" style=" top:7px; right:15px; font-weight:bold;">付款状态:
							<s:if test="orders.settlementStatus==0">未付款</s:if>
							<s:if test="orders.settlementStatus==1">已付款</s:if>
						</div>
					</div>
					
					
			<ul class="order_StatusUl ClearBoth publicMarginTop15" style="padding-left:203px;">
				<li>
					<p <s:if test="orders.ordersState==0"> class="select"</s:if> >
						生成订单
					</p>
<!-- 					<span class="datetimeP"><s:date name="orderDetailTime.createTime" format="yyyy-MM-dd HH:mm:ss"/></span> -->
				</li>
				<li>
					<p <s:if test="orders.ordersState==3"> class="select"</s:if>>
						 已配货
					</p>
<!-- 					<span class="datetimeP"><s:date name="orderDetailTime.payTime" format="yyyy-MM-dd HH:mm:ss"/></span> -->
				</li>
				<li>
					<p <s:if test="orders.ordersState==4"> class="select"</s:if>>
						 已发货
					</p>
<!-- 					<span class="datetimeP"><s:date name="orderDetailTime.deliveryTime" format="yyyy-MM-dd HH:mm:ss"/></span> -->
				</li>
				<li>
					<p <s:if test="orders.ordersState==5||orders.ordersState==9"> class="select"</s:if>>
					 确认收货
					</p>
<!-- 					<span class="datetimeP"><s:date name="orderDetailTime.receivingTime" format="yyyy-MM-dd HH:mm:ss"/></span> -->
				</li>
			</ul>
			
			
		 <article class="Order_tracking publicMarginTop15">
			<h3 class="FontSize14 FontSizeB publicPaddingT15">
				<p  style="width:260px;color: #d01743;font-weight: bold;">订单跟踪</p>
			</h3>
			<table width="100%" border="0"
				class="publicMarginTop15 Order_tracking_table ">
				<tr>
					<td>
						处理时间
					</td>
					<td>
						处理信息
					</td>
					<td>
						操作人
					</td>
				</tr>
				<s:iterator value="listOrdersLog">
					<tr>
						<td>
							<s:date name="operatorTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<s:if test="ordersOperateState==1">生成订单</s:if>
							<s:if test="ordersOperateState==2">已付款</s:if>
							<s:if test="ordersOperateState==3">正在配货</s:if>
							<s:if test="ordersOperateState==4">已发货</s:if>
							<s:if test="ordersOperateState==5">已收货</s:if>
							<s:if test="ordersOperateState==6">订单取消</s:if>
							<s:if test="ordersOperateState==7">问题订单</s:if>
							<s:if test="ordersOperateState==9">已评价</s:if>
						</td>
						<td>
							${operatorName}
						</td>
					</tr>
				</s:iterator>
				<s:if test="resultItemList!=null">
					<s:iterator value="resultItemList" var="resultItem">
					<tr>
						<td><s:property value="#resultItem.time" /></td>
						<td><s:property value="#resultItem.context" /></td>
						<td>${shipping.deliveryCorpName}</td>
					</tr>
					</s:iterator>
				</s:if>
			</table>
			</article>
			
			<article
						class="SellerInformation publicPadding10">
					<h3 class="FontSize14 FontSizeB publicMarginBot15">
						<p  style="width:260px;color: #d01743;font-weight: bold;">
							订单信息
						</p>
					</h3>
					<div class="ClearBoth">
						<p class="text-right FontSizeB">
							订&nbsp;&nbsp;单&nbsp;&nbsp;号:
						</p>
						<p>
							<s:property value="orders.ordersNo" />
						</p>
					</div>

					<div class="ClearBoth">
						<p class="text-right FontSizeB">
							下单时间:
						</p>
						<p>
							<s:date name="orderDetailTime.createTime" format="yyyy-MM-dd HH:mm:ss"/>
						</p>
					</div>
					<s:if test="orderDetailTime.payTime!=null">
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								付款时间:
							</p>
							<p>
								<s:date name="orderDetailTime.payTime" format="yyyy-MM-dd HH:mm:ss"/>
							</p>
						</div>
					</s:if>
					<s:if test="orderDetailTime.deliveryTime!=null">
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								发货时间:
							</p>
							<p>
								<s:date name="orderDetailTime.deliveryTime" format="yyyy-MM-dd HH:mm:ss"/>
							</p>
						</div>
					</s:if>
					</article>
					<s:if test="orders.sendType!=3">
						<article class="SellerInformation publicPadding10">
						<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15">
							<p  style="width:260px;color: #d01743;font-weight: bold;">
								收货人信息
							</p>
						</h3>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								收&nbsp;货&nbsp;人:
							</p>
							<p>
								<s:property value="orders.consignee" />
							</p>
						</div>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								联系电话:
							</p>
							<p>
								<s:property value="orders.mobilePhone" />
							</p>
						</div>
						<div class="ClearBoth" >
							<p class="text-right FontSizeB">
								收货地址:
							</p>
							<div title="${orders.address}${orders.city}${orders.regionLocation}${orders.country}" style="height: 1.5em;width:500px;word-wrap:break-word;overflow:hidden;color:#333;">
								${orders.address}
							</div>
						</div>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:
							</p>
							<p>
								<s:property value="orders.postcode" />
							</p>
						</div>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:
							</p>
							<p>
								<s:property value="orders.email" />
							</p>
						</div>
						</article>
					</s:if>
					<s:else>
						<article class="SellerInformation publicPadding10">
						<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15">
							<p  style="width:260px;color: #d01743;font-weight: bold;">
								收货信息
							</p>
						</h3>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								收货方式:
							</p>
							<p>
								线下自取
							</p>
						</div>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								提&nbsp;货&nbsp;人:
							</p>
							<p>
								<s:property value="orders.consignee" />
							</p>
						</div>
						<div class="ClearBoth">
							<p class="text-right FontSizeB">
								联系电话:
							</p>
							<p>
								<s:property value="orders.mobilePhone" />
							</p>
						</div>
						</article>
					</s:else>
					<article class="SellerInformation publicPadding10">
					<h3 class="FontSize14 FontSizeB publicMarginBot15">
						<p  style="width:260px;color: #d01743;font-weight: bold;">
							会员信息
						</p>
					</h3>
					<div class="ClearBoth">
						<p class="text-right FontSizeB">
							会员名称:
						</p>
						<p>
							<s:property value="#request.customerForOrdersInfo.loginName" />
						</p>
					</div>
					<div class="ClearBoth">
						<p class="text-right FontSizeB">
							邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:
						</p>
						<p>
							<s:property value="#request.customerForOrdersInfo.email" />
						</p>
					</div>
					</article>
						<s:if test="orders.ordersState==4||orders.ordersState==5||orders.ordersState==9">
							<s:if test="orders.sendType==1">
								<article class="SellerInformation publicPadding10">
								<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15">
									<p  style="width:260px;color: #d01743;font-weight: bold;">
										快递信息
									</p>
								</h3>
								<div class="ClearBoth">
									<p class="text-right FontSizeB">
										快递单号:
									</p>
									<p>
										${shipping.deliverySn}
									</p>
								</div>
								<div class="ClearBoth">
									<p class="text-right FontSizeB">
										快递公司名称:
									</p>
									<p>
										<a href="${shipping.deliveryUrl}" target="_blank" class="ColorBlue">${shipping.deliveryCorpName}</a>
									</p>
								</div>
							</article>
							</s:if>
							<s:else>
								<article class="SellerInformation publicPadding10">
								<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15">
									<p  style="width:260px;color: #d01743;font-weight: bold;">
										快递信息
									</p>
								</h3>
								<div class="ClearBoth">
									<p class="widthpx150 text-right FontSizeB"  style="text-align: left;">
										快递公司名称:
									</p>
									<p  style="text-align: left;">
										同城快递
									</p>
								</div>
								<div class="ClearBoth">
									<p class="widthpx150 text-right FontSizeB"  style="text-align: left;">
										快递员姓名:
									</p>
									<p  style="text-align: left;">
										${cityCourierMap.cityCourierName}
									</p>
								</div>
								<div class="ClearBoth">
									<p class="widthpx150 text-right FontSizeB"  style="text-align: left;">
										快递员联系方式:
									</p>
									<p  style="text-align: left;">
										${cityCourierMap.phone}
									</p>
								</div>
							</article>
							</s:else>
						</s:if>
			<article class="SellerInformation publicPadding10">
			<h3 class="FontSize14 FontSizeB publicPaddingT15 publicMarginBot15">
				<p  style="width:260px;color: #d01743;font-weight: bold;">发票信息</p>
			</h3>
			<s:if test="orders.invoiceType==3">
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发票类型：增值税发票
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发&nbsp;&nbsp;票&nbsp;&nbsp;抬&nbsp;&nbsp;头：${orders.companyNameForInvoice}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						纳税人识别号：${orders.taxpayerNumber}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：${orders.addressForInvoice}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：${orders.phoneForInvoice}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;行：${orders.openingBank}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：${orders.bankAccountNumber}
					</p>
				</div>
			</s:if>
			<s:elseif test="orders.invoiceType==2">
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发票类型：普通发票
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发票抬头：${orders.companyNameForInvoice}
					</p>
				</div>
				<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发票内容：${orders.invoiceInfo}
					</p>
				</div>
			</s:elseif>
			<s:else>
			<div class="ClearBoth">
					<p class="text-right FontSizeB">
						发票类型：不开发票
					</p>
				</div>
			</s:else>
			</article>
					
					<table width="100%" border="0"
						class="RecentlyOrders_table publicMarginT10">
						<tr class=" publicBorder gradient">
							<td>
								商品
							</td>
							<td>
								商品编号
							</td>
							<td>
								原价
							</td>
							<td>
								销售价
							</td>
							<td>会员折扣</td>
							<td>
								预计发货日
							</td>
							<td>
								赠送商城币
							</td>
							<td>
								赠送用金币
							</td>
							<td>
								数量
							</td>
							<td>
								小计
							</td>
						</tr>
						<s:iterator value="listProduct" var="prod">
							<tr>
								<td>
									<a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value='#prod.productId'/>">
										<img title="<s:property value='#prod.productFullName'/>"
											src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot+#prod.logoImg'/>"
											style="height: 80px; width: 160px;" />
									</a>
								</td>
								<td>
									${prod.productCode}
								</td>
								<td>
									￥<fmt:formatNumber value="${prod.marketPrice}" pattern="#,##0.00#" />
								</td>
								<td>
									￥<fmt:formatNumber value="${prod.salesPrice}" pattern="#,##0.00#" />
								</td>
								<td>
								<s:if test="#prod.memberPrice/#prod.salesPrice<1">
									<fmt:formatNumber value="${prod.memberPrice/prod.salesPrice*10}" pattern="#,##0.0#" />折
								</s:if>
								<s:else>无</s:else>
								</td>
								<td>
									<s:if test="#prod.storeNumber<=0||#prod.count>#prod.storeNumber">
										<span class="yjfhr2"><s:property value="#prod.stockUpDate"/></span>
									</s:if>
									<s:else>
										<span class="yjfhr">当日发货</span>
									</s:else>
								</td>
								<td>
									${prod.virtualCoinNumber}
								</td>
								<td>
									${prod.virtualCoin}
								</td>
								<td>
									${prod.count}
								</td>
								<td class="publicNoBorderR">
									￥<fmt:formatNumber value="${prod.memberPrice*prod.count}" pattern="#,##0.00#" />
								</td>
							</tr>
						</s:iterator>
					</table>
					<aside class="FontSize15 publicMarginTop15 text-right line-height1 publicBorderB">
					<div class="FontSize14 BackgroundE4Hui publicHidden publicMarginB10" style="padding:10px;">
						
						<p class="text-right">
							运费: ￥
							<fmt:formatNumber value="${orders.freight}" pattern="#,##0.00#" />
						</p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">+&nbsp;税费:</span> <span>￥<fmt:formatNumber value="${orders.taxation}" pattern="#,##0.00#" /></span></p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">-&nbsp;商城币抵扣数量:</span> <span><fmt:formatNumber value="${orders.userCoin}" type="number" /></span></p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">-&nbsp;商城币抵扣金额:</span> <span>￥<fmt:formatNumber value="${orders.changeAmount}" pattern="#,##0.00#" /></span></p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">-&nbsp;商城优惠券抵扣金额:</span> <span>￥<fmt:formatNumber value="${orders.orderCouponAmount}" pattern="#,##0.00#" /></span></p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">&nbsp;订单赠送商城币:</span> <span>￥<fmt:formatNumber value="${orders.virtualCoinNumber}" pattern="#,##0" /></span></p>
						<p class="text-right" style="margin-bottom:5px;"><span style="width:auto; text-align:right;">&nbsp;订单赠送佣金币:</span> <span>￥<fmt:formatNumber value="${orders.virtualCoin}" pattern="#,##0" /></span></p>
						<p class="text-right">
							支付金额: ￥
							<span class="strong ColorRed publicPaddingL7"><fmt:formatNumber
									value="${orders.finalAmount}" pattern="#,##0.00#" />
							</span>
						</p>
					</div>
					</aside>
					</article>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
