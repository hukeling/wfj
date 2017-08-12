<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>评价列表</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<script src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<SCRIPT type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/js/json2.js"></SCRIPT>
		<script type="text/javascript">
		
		$(function(){
				var level ='${level}'
				$('#level').val(level);
			});
		</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
		<form id="formModule" action="${pageContext.request.contextPath}/front/customer/commentList.action" method="post">
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">评价</h3>
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
					<div class="FontSize14"  style="line-height:22px;">
						评价等级:
					</div>
					<div>
						<select name="level" id="level">
							<option selected="selected" value="">
								全部
							</option>
							<option value="1">
								好评
							</option>
							<option value="0">
								中评
							</option>
							<option value="-1">
								差评
							</option>
						</select>
					</div>
					<div>
						<input type="submit" value="查询" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" id="comment-search">
					</div>
					</aside>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div style="display: block;">
							<div class="publicMarginTop15">
								<table width="100%" border="0" class="RecentlyOrdersTable_lj">
									<tbody>
										<tr style="border-bottom: 0; background-color: #fafafa;">
											<td width="25%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
												订单号
											</td>
											<td width="18%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
												评价
											</td>
											<td width="17%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
												详细信息
											</td>
											<td width="16%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
												卖家
											</td>
											<td width="14%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
												商品
											</td>
										</tr>
									
									<s:iterator value="evaluateGoodsMap" var="c">
										<tr>
											<td style="border-bottom: 1px #d2d2d2 solid;">
											<a href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="#c.ordersId" />">${c.ordersNo }</a>
											</td>
											<td style="border-bottom: 1px #d2d2d2 solid;">
												<s:if test="#c.level==1">
													好评
												</s:if>
												<s:elseif test="#c.level==0">
													中评
												</s:elseif>
												<s:elseif test="#c.level==-1">
													差评
												</s:elseif>
												
											</td>
											<td style="border-bottom: 1px #d2d2d2 solid;">
												<div title="${c.content}" style="line-height:24px;margin-top:14px; height: 5em;word-wrap:break-word;overflow:hidden;">${c.content}</div>
											</td>
											<td style="border-bottom: 1px #d2d2d2 solid;">
												${c.acceptAppraiserName}
											</td>
											<td class="lastR" style="border-bottom: 1px #d2d2d2 solid;">
												<a target="_blank"  href="${pageContext.request.contextPath}/productInfo/${c.productId}.html">
												<img title="${c.productFullName}" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${c.logoImg}"  style="height: 50px;width: 50px;"/></a>
												<p class="ColorRed" style="text-align: center;">
												  <span class="FontSize12">￥${c.salesPrice}</span>
												</p>
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
					</section>

				</div>
				</form>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
