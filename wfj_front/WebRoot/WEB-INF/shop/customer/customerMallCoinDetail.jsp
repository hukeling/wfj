<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的商城币</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<STYLE type="text/css">
			.RecentlyOrders_table tr{
				font-size: 13px;
				line-height:3em;
			}
			.RecentlyOrders_table{
				table-layout:fixed;
			}
			.RecentlyOrders_table td{
				text-overflow: ellipsis;word-break:keep-all;white-space: nowrap;overflow:hidden;
			}
		</STYLE>
		<script>
			//导出excel方法
			function daochuExcel(){
				window.location.href="${pageContext.request.contextPath}/front/customer/customerMallCoin/exportExcel.action?ordersNo="+$("#ordersNo").val()+"&startTime="+$("#stime").val()+"&endTime="+$("#etime").val();
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">商城币明细</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/customer/customerMallCoin/getCustomerMallCoinDetail.action" method="post">
				<aside class="accountOrder_search ClearBoth publicMarginBot15"
							style="height:22px;">
							<div class="FontSize14" style="line-height:22px;">订单编号:</div>
							<div>
								<input id="ordersNo" name="ordersNo" value="${ordersNo}"
									type="text" class="widthpx180" style="width: 120px"/>
							</div>
							<div class="FontSize14" style="line-height:22px;">时间:</div>
							<div>
								<input id="stime" style="width: 68px" name="startTime"
									type="text"
									onclick="WdatePicker({lang:'cn', maxDate:'#F{$dp.$D(\'etime\',{d:-1})||\'2020-10-01\'}'})"
									value="${startTime}"
									onfocus="if(this.value==this.defaultValue){this.value=''}" />
							</div>
							<div>-</div>
							<div>
								<input id="etime" style="width: 68px" type="text" name="endTime"
									onclick="WdatePicker({minDate:'#F{$dp.$D(\'stime\',{d:1})}',maxDate:'2020-10-01',lang:'cn'})"
									value="${endTime}"
									onfocus="if(this.value==this.defaultValue){this.value=''}" />
							</div>
							<div>
								<input type="submit"
									class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
									style="padding: 2px 5px;"
									value="查询" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" onclick="daochuExcel()" 
									class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
									style="padding: 2px 5px;"
									value="导出Excel" />
							</div>
						</aside>
					<div class="">
						<div class="">
							<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
								<tr class="gradient publicBorder">
									<td width="25%">
										流水号
									</td>
									<td width="25%">
										订单号
									</td>
									<td width="25%">
										收支数量
									</td>
									<td width="25%">
										类型
									</td>
									<td width="25%">
										剩余商城币
									</td>
									<td width="25%">
										时间
									</td>
								</tr>

								<s:iterator value="mapList" id="m">
									<tr>
										<td>
											<s:property value='#m.serialNumber'/>
										</td>
										<td>
											<a href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="#m.ordersId" />"><s:property value='#m.ordersNo'/></a>
										</td>
										<td>
											<s:property value="#m.transactionNumber" />
										</td>
										<td>
											<s:if test="#m.type==1">收入</s:if>
											<s:if test="#m.type==2">支出</s:if>
											<s:if test="#m.type==3">退回</s:if>
											<s:if test="#m.type==4">交易作废</s:if>
										</td>
										<td>
											<s:property value='#m.remainingNumber' />
										</td>
										<td>
											<s:property value='#m.operatorTime' />
										</td>
									</tr>
								</s:iterator>
							</table>
							
							<!--分页 start-->
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
							<!--分页 end-->
						</div>
					</div>
				</form>
				</div>
						<div id="rightBox" class="float-right publicHidden">
							<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">统计</h3>
							<table width="100%" border="0" class="RecentlyOrders_table">
									<tr>
										<td width="16.6%">
											收入
										</td>
										<td width="16.6%">
											<fmt:formatNumber value="${earning}" pattern="#" type="number"/>
										</td>
										<td width="16.6%">
											支出
										</td>
										<td width="16.6%">
											<fmt:formatNumber value="${expenditure}" pattern="#" type="number"/>
										</td>
										<td width="16.6%">
											剩余商城币
										</td>
										<td width="16.6%">
											<fmt:formatNumber value="${earning-expenditure}" pattern="#" type="number"/>
										</td>
									</tr>
							</table>
							</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
