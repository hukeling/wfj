<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户店铺会员列表</title>
<%@ include file="../include/head.jsp"%>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店铺会员列表</h3>
					<form id="formModule" action="${pageContext.request.contextPath}/front/memberShip/memberApplyList.action" method="post">
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="15%">店铺名称</td>
								<td width="15%">折扣比例</td>
								<td width="20%">申请时间</td>
								<td width="11%">审核时间</td>
								<td width="7%">审核状态</td>
							</tr>
							<s:if test="memberShipList.size>0">
								<s:iterator value="memberShipList">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
										<a target="_blank" style="color:#2b81ff;" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="shopInfoId" />">
											<s:property value="shopName" />
										</a>
										</td>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
											<s:if test="discount!=null&&discount!=''">
												<fmt:formatNumber value="${discount}" pattern="#,##0.0#" />折
											</s:if>
											<s:else>无</s:else>
										</td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:date name="auditTime" format="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><span>
												<s:set name="state" value="<s:property value='state' />" /> 
												<s:if test="state == 1">
													<p class="ColorGreennn" style="margin:0;">待审核</p>
												</s:if> 
												<s:elseif test="state == 2">
													<span style="color: blue;">审核通过</span>
												</s:elseif> <s:elseif test="state == 3">
													<p style="color: green;">审核拒绝</p>
												</s:elseif>
										</span></td>
									</tr>
								</s:iterator>
							</s:if>
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
