<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>优惠券列表</title>
		<%@ include file="../shop/include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
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
	<%@ include file="../shop/include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
		<form id="formModule" action="${pageContext.request.contextPath}/front/customer/customercoupon/gotoMyAccountCustoCouponList.action" method="post">
		<div class="ClearBoth">
			<s:if test="#session.customer.type==1">
				<%@ include file="../shop/include/left_account.jsp"%>
			</s:if>
			<s:elseif test="#session.customer.type==3">
				<%@ include file="../shop/include/left_account_gr.jsp"%>
			</s:elseif>
			<div id="rightBox" class="float-right publicHidden">
			<section id="Marketpalce">
			<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">优惠券列表</h3>
			<aside class="accountOrder_search ClearBoth publicMarginBot15">
					<div class="FontSize14"  style="line-height:22px;">
						使用状态:
					</div>
					<div>
						<select name="level" id="level" class="hyds_sele">
							<option selected="selected" value="">
								请选择
							</option>
							<option value="0">
								未使用
							</option>
							<option value="1">
								已使用
							</option>
						</select>
					</div>
					<div>
						<input id="comment-search" type="submit"
									class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
									style="padding: 2px 5px;"
									value="查询" />
					</div>
					</aside>
<!-- 			<div class="clear"> -->
			<div class="TabParent_lj  ClearBoth">
<!-- 					<div style="display: block;"> -->
						<div class="publicMarginTop15">
<!-- 				<table width="100%" border="0" class="RecentlyOrdersTable_lj"> -->
				<table width="100%" border="0"class="hydsOrders_table publicMarginT10" style="background-color:#F2F2F2">
								<tr>
									<!-- <td width="10%">
										优惠券图片
									</td> -->
									<td width="12%">
										优惠劵名称
									</td>
									<td width="10%">
										优惠金额
									</td>
									<td width="10%">
										启用状态
									</td>
									<td width="12%">
										 开始时间
									</td>
									<td width="12%">
										到期时间
									</td>
									<td width="10%">
										优惠券状态
									</td>
									<td width="8%">
										使用状态
									</td>
								</tr>
							
							<s:iterator value="ordersListMap" var="c">
								<tr>
									<%-- <td >
										<img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" title="${c.discountCouponName}" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${c.discountImage}"  style="height: 50px;width: 50px;"/>
									</td> --%>
									<td>
										${c.discountCouponName}
									</td>
									<td>
										${c.discountCouponAmount}
									</td>
									<td>
										<s:if test="#c.auseStatus==0">
											<span style="color:red">未启用</span> 
										</s:if>
										<s:elseif test="#c.auseStatus==1">
											<span style="color:blue">已启用</span> 
										</s:elseif>
									</td>
									<td>
										${c.beginTime}
									</td>
									<td>
										${c.expirationTime}
									</td>
									<td>
										<s:if test="#c.status==1">
											<span style="color:blue">正常</span> 
										</s:if>
										<s:elseif test="#c.status==2">
											<span style="color:red">过期</span>  
										</s:elseif>
										<s:elseif test="#c.status==3">
											<span style="color:yellow">作废</span>
										</s:elseif>
									</td>
									<td>
										<s:if test="#c.useStatus==0">
											<span style="color:blue">未使用</span> 
										</s:if>
										<s:elseif test="#c.useStatus==1">
											<span style="color:red">已使用</span> 
										</s:elseif>
									</td>
								</tr>
							</s:iterator>
						</table>

						<!--分页 start-->
						<div class="pageList strong ClearBoth">
							<jsp:include page="../util/splitPage.jsp" />
						</div>
						<!--分页 end-->
					</div>
<!-- 				</div> -->
			</div>
<!-- 			</div> -->
			</section>
			</div>
			</form>
		</div>
	</div>
<!--     This is my JSP page. <br> -->
    <%@ include file="../shop/include/footer.jsp"%>
  </body>
</html>
