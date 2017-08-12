<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的退货-退货列表</title>
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
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">退货</h3>
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="13%">
									订单编号
								</td>
								<td width="30%">
									申请上传的图片
								</td>
								<td width="8%">申请人</td>
								<td width="8%">申请时间</td>
								<td width="13%">退/换货申请编号</td>
								<td width="5%">退/换货</td>
								<td width="10%">审核</td>
								<td width="10%">退/换货状态</td>
								<td width="5%">操作</td>
							</tr>
<!-- 							订单对象 -->
							<s:iterator value="returnsApplyListMap">
								<tr>
									<td style="padding:0 5px;">
										<a	href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="ordersId" />"><s:property value="ordersNo" /></a>
									</td>
									<td style="padding:0 5px;">
										<div class="ClearBoth imgBox">
										<s:iterator value="imgList" var="img">
												<img style="height: 50px; width: 50px;margin:2px 1px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#img"/>" />
										</s:iterator>
										</div>
									</td>
									<td style="padding:0 5px;">
										<s:property value="loginName"/>
									</td>
									<td style=" width:8%;padding:0 5px;">
										<s:date name="applyTime" format="yyyy-MM-dd HH:mm:ss"/>
									</td>
									<td style="padding:0 5px;">
										<s:property value="returnsApplyNo"/>
									</td>
									<td style="padding:0 5px;">
										<s:if test="disposeMode==2">换货</s:if>
										<s:elseif test="disposeMode==3">退货</s:elseif>
									</td>
									<td style="padding:0 5px;">
										<s:iterator value="#application.keybook['applyState']" var="kb">
											<s:if test="applyState==#kb.value">
												<s:property value="#kb.name"/>
											</s:if>
										 </s:iterator>
									</td>
									<td style="padding:0 5px;">
										<s:if test="disposeMode==2">
											<s:iterator value="#application.keybook['trade']" var="kb">
												<s:if test="returnsState==#kb.value">
													<s:property value="#kb.name"/>
												</s:if>
											 </s:iterator>
										</s:if>
										<s:elseif test="disposeMode==3">
											<s:iterator value="#application.keybook['returnsState']" var="kb">
												<s:if test="returnsState==#kb.value">
													<s:property value="#kb.name"/>
												</s:if>
											 </s:iterator>
										</s:elseif>
									</td>
									<td style="padding:0 5px;">
										<a href="${pageContext.request.contextPath }/front/customer/frontCustomerComplaints/gotoComplaintsLogInfoPage.action?returnsApplyId=<s:property value='returnsApplyId'/>" style="color: blue;">详情</a>
									</td>
								</tr>
							</s:iterator>
						</table>
						
						
						<!--分页 start-->
						<form id="formModule" action="${pageContext.request.contextPath }/front/customer/frontCustomerComplaints/gotoReturnsApplyListPage.action" method="post">
						
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
						</form>
						<!--分页 end-->
					
					</section>

				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
