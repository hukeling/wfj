<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>佣金币提现记录</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<STYLE type="text/css">
			.RecentlyOrders_table tr{
				font-size: 13px;
				line-height: 3em;
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
			window.location.href="${pageContext.request.contextPath}/front/customer/thbtx/exportTHBTXExcel.action";
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">佣金币提现记录</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/customer/thbtx/gotoListPage.action" method="get">
				<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div>
							<input type="button"onclick="daochuExcel();" class="publicNoBorder ColorWhite1 FontSize14" style="cursor: pointer; cursor: pointer;  height:22px; width:68px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/dcexcel.jpg) no-repeat;"/>
						</div>
					</div>
					</aside>
					<div class="MarketpalceTree publicMarginTop15" style="width:963px;">
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
								<tr class="gradient publicBorder">
									<td width="8%">
										持卡人
									</td>
									<td width="16%">
										银行卡号
									</td>
									<td width="10%">
										预留手机号
									</td>
									<td width="10%">
										提现金额
									</td>
									<td width="10%">
										用户留言
									</td>
									<td width="8%">
										交易状态
									</td>
									<td width="12%">
										提现时间
									</td>
								</tr>

								<s:iterator value="#request.list" var="m">
									<tr>
										<td width="10%">
											<s:property value="#m.cardHolder"/>
										</td>
										<td width="17%">
											<a style="text-decoration: none;" title="<s:property value="#m.cardNumber"/>"><s:property value="#m.cardNumber"/></a>
										</td>
										<td width="15%">
											<s:property value="#m.phone"/>
										</td>
										<td width="10%">
											<s:property value="#m.transactionAmount"/>
										</td>
										<td width="10%">
											<a style="text-decoration: none;" title="<s:property value="#m.customerMessage"/>"><s:property value="#m.customerMessage"/></a>
										</td>
										<td width="8%">
											<s:if test="#m.state==1">
												申请提现
											</s:if>
											<s:elseif test="#m.state==2">
												平台支付完成
											</s:elseif>
											<s:else>
												平台支付失败
											</s:else>
										</td>
										<td width="10%">
											<a style="text-decoration: none;" title="<fmt:formatDate value="${m.createTime}" type="both"/>"><fmt:formatDate value="${m.createTime}" type="both"/></a>
										</td>
									</tr>
								</s:iterator>
							</table>
							<!--分页 start-->
							<s:if test="#request.list.size>0">
								<div class="pageList strong ClearBoth">
									<jsp:include page="../../util/splitPage.jsp" />
								</div>
							</s:if>
							<!--分页 end-->
						</div>
					</div>
				</form>
				</div>
				<!--//right-->
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
