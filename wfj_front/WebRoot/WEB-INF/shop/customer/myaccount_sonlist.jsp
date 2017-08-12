<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>子帐号管理</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#sonType").val($("#selectStatus").val());
			});

		</script>

	</script></head>

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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">子帐号信息列表</h3>
					<form id="formModule" action="${pageContext.request.contextPath}/front/customer/Setting/listSonaccount.action?flag=1" method="post">
					<aside class="accountOrder_search ClearBoth publicMarginBot15" style="height:22px;">
						<div class="FontSize14" style="line-height:22px;">
							会员类型 :
						</div>
						<div>
							<input type="hidden" id="selectStatus" value="${sonaccount.type}"/>
							<select id="sonType" name="sonaccount.type" onchange="changePage('${pageHelper.prePageIndex}');" style="padding:0px;">
								<option id="type" value="">全部</option>
								<option id="type1" value="1">采购人员</option>   
								<option id="type2" value="2">财务人员</option> 
							</select>
						</div>
						
						<div class="FontSize14" style="line-height:22px;">
							日期:
						</div>
						<div>
						    <input id="stime" style="width: 68px" name="startTime"  type="text"  
						    onclick="WdatePicker({lang:'cn', maxDate:'#F{$dp.$D(\'etime\',{d:-1})||\'2020-10-01\'}'})"  value="${startTime}" onfocus="if(this.value==this.defaultValue){this.value=''}"/>
						</div>
						<div>
							-
						</div>
						<div>
						    <input id="etime" style="width: 68px" type="text" name="endTime" 
						    onclick="WdatePicker({minDate:'#F{$dp.$D(\'stime\',{d:1})}',maxDate:'2020-10-01',lang:'cn'})" value="${endTime}" onfocus="if(this.value==this.defaultValue){this.value=''}"/>
						</div>
						<div class="FontSize14" style="line-height:22px;">
							真实姓名:
						</div>
						<div>
							<input name="sonaccount.nickname" value="${sonaccount.nickname}" type="text" class="widthpx180" style="width: 120px" >
						</div>
						<div>
							<input name="" type="submit"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14"
								value="查询" />
						</div>
						<div style="float:right;">
							<a href="${pageContext.request.contextPath}/front/customer/Setting/gotoADFPage.action?flag=1" 
							class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14">添加子帐号</a>
						</div>
					</aside>
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="8%">
									真实姓名
								</td>
								<td width="12%">
									手机号
								</td>
								<td width="15%">
									邮箱
								</td>
								<td width="11%">
									添加日期
								</td>
								<td width="10%">
									会员类型
								</td>
								<td width="10%">
									操作
								</td>
							</tr>
							<s:iterator value="sonaccountList" var="list">
								<tr>
									<input type="hidden" id="sonAccountId" value="<s:property value="#list.sonAccountId"/>"/>
									<input type="hidden" id="customerId" value="<s:property value="#list.customerId"/>"/>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:property value="#list.nickname" />
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:property value="#list.phone" />
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:property value="#list.email" />
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:property value="#list.registerDate" />
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<s:if test="#list.type==1">
											<span>采购人员</span>
										</s:if>
										<s:elseif test="#list.type==2">
											<span>财务人员</span>
										</s:elseif>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed;">
										<a href="${pageContext.request.contextPath}/front/customer/Setting/getSonaccountObject.action?sonaccount.sonAccountId=<s:property value="#list.sonAccountId"/>&flag=2"/>修改</a>
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
