<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>评价</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<script type="text/javascript">
		function changEstimate(level){
			window.location="${pageContext.request.contextPath}/front/store/shopOrder/shopEvaluationList.action?evaluateGoods.level="+level;
		}
		</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">商品评价</h3>
					<form id="formModule" name="form1" method="post" action="${pageContext.request.contextPath}/front/store/shopOrder/shopEvaluationList.action">
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div style="display: block;">
							<div class="publicMarginTop15">
								<table width="100%" border="0" class="RecentlyOrdersTable_lj">
									<tbody>
										<tr class="gradient publicBorder">
											<td style="width:5%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid;  border-left: 1px #d2d2d2 solid; vertical-align: middle;">
												<select name="evaluateGoods.level" onchange="changEstimate(this.value);">
													<option <s:if test="evaluateGoods.level==null">selected="selected"</s:if> value="" >评价</option>
													<option <s:if test="evaluateGoods.level==1">selected="selected"</s:if> value="1">好评</option>
													<option <s:if test="evaluateGoods.level==0">selected="selected"</s:if> value="0">中评</option>
													<option <s:if test="evaluateGoods.level==-1">selected="selected"</s:if> value="-1">差评</option>
												</select>
											</td>
											<td  style="width:15%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid;">
												订单号
											</td>
											<td style="width:30%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid; ">
												评价
											</td>
											<td  style="width:10%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid;">
												买家
											</td>
											<td style="width:20%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid;">
												商品名称
											</td>
											<td style="width:20%; border-bottom: 1px #d2d2d2 solid;  border-top: 1px #d2d2d2 solid;  border-right: 1px #d2d2d2 solid;">
												时间
											</td>
										</tr>
									<s:iterator value="evaluateGoodsMap" var="c">
										<tr>
											<td  style="width:5%; border-bottom: 1px #d2d2d2 solid; vertical-align: middle;">
												<s:if test="#c.level==1">好评</s:if>
												<s:if test="#c.level==0">中评</s:if>
												<s:if test="#c.level==-1">差评</s:if>
											</td>
											<td style=" line-height:25px; width:15%; border-bottom: 1px #d2d2d2 solid;  vertical-align: middle;">
												<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}">${c.ordersNo}</a>
											</td>
											<td title="${c.content}" style=" line-height:22px; width:30%; border-bottom: 1px #d2d2d2 solid;  vertical-align: middle;">
												<s:if test="#c.content.length>10">
													<s:property value="#c.content.substring(0,10)"/>
												</s:if>
												<s:else><s:property value="#c.content"/></s:else>
											</td>
											<td style=" line-height:25px; width:10%; border-bottom: 1px #d2d2d2 solid;  vertical-align: middle;">
												${c.appraiserName}
											</td>
											<td style="width:20%; line-height:25px; border-bottom: 1px #d2d2d2 solid;  vertical-align: middle;">
												<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${c.productId}.html">
													<img style="height: 50px; width: 80px;" title="${c.productFullName}"
														alt="${c.productFullName}"
														src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#c.logoImg"/>" />
												</a>
											</td>
											<td class="lastR"  style="width:20%;  line-height:25px; border-bottom: 1px #d2d2d2 solid;  vertical-align: middle;">
												<s:date name="#c.createTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
										</tr>
									</s:iterator>
									</tbody>
								</table>
								<!--分页 start-->
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
							<!--分页 end-->
							</div>
						</div>
					</div>
					</form>
					</section>

				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
