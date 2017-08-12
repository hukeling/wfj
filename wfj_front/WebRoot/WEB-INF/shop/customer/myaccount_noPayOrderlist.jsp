<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户未结算订单列表</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">未结算订单列表</h3>										
					<form id="formModule" action="${pageContext.request.contextPath}/front/customer/myOrders/toPayOrdersList.action" method="post">
					<input id="pageIndex" name="pageIndex" type="hidden" value="" />
					<aside class="accountOrder_search ClearBoth publicMarginBot15" style="height:22px;">
						<div class="FontSize14" style="line-height:22px;">
							起始日期:
						</div>
						<div>
						    <input id="stime" style="width: 150px" name="startTime"  type="text"  
						    onclick="WdatePicker({lang:'cn', maxDate:'#F{$dp.$D(\'etime\',{d:-1})||\'2020-10-01\'}'})"  value="${startTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div class="FontSize14" style="line-height:22px;">
							结束日期:
						</div>
						<div>
						    <input id="etime" style="width: 150px" type="text" name="endTime" 
						    onclick="WdatePicker({minDate:'#F{$dp.$D(\'stime\',{d:1})}',maxDate:'2020-10-01',lang:'cn'})" value="${endTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div>
							<input name="" type="submit"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14"
								value="查询" />
						</div>
				 	</aside>
					<table style="width: 100%,border:0;" class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td style="width: 15%;">
									订单号
								</td>
								<td style="width: 11%;">
									结算金额
								</td>
								<td style="width: 12%;">
									订单生成时间
								</td>
								<td style="width: 11%;">
									订单状态
								</td>
								<td style="width: 10%;">
									操作
								</td>
							</tr>
							<s:iterator value="ordersList">
								<tr>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<a href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="ordersId" />"><s:property value="ordersNo" /></a>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<span class="ColorRed FontSizeB">￥<fmt:formatNumber value="${finalAmount}" pattern="#,##0.00#" />
										</span>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<span class="ColorGreennn" style="margin:0;"> 
											未付款
										</span>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<a href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.ordersId=<s:property value='ordersId' />">去付款</a>
									</td>
								</tr>
							</s:iterator>
						</table>
						<!--分页 start-->
						<div class="pageList strong ClearBoth">
							<jsp:include page="../../util/splitPage.jsp" />
						</div>
						<!--分页 end-->
					</form>
					</section>

				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
