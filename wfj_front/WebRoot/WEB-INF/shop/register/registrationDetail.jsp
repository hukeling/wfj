<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的分享记录</title>
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
	</head>
	<script>
			//导出excel方法
			function daochuExcel(){
				window.location.href="${pageContext.request.contextPath}/front/customer/Setting/exportShareRegisterExcel.action?loginName="+$("#loginName").val()+"&startTime="+$("#stime").val()+"&endTime="+$("#etime").val();
			}
		</script>
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">分享记录明细</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/customer/Setting/gotoShareRegisterDetail.action" method="post">
					<aside class="accountOrder_search ClearBoth publicMarginBot15"
							style="height:22px;">
							<div class="FontSize14" style="line-height:22px;">分享会员:</div>
							<div>
								<input id="loginName" name="loginName" value="${loginName}"
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
										分享会员
									</td>
									<td width="25%">
										赠送金币数
									</td>
									<td width="25%">
										分享时间
									</td>
								</tr>
								<s:iterator value="mapList" id="m">
									<tr>
										<td>
											<s:property value='#m.loginName'/>
										</td>
										<td>
											<s:property value='#m.giveCoinNumber' />
										</td>
										<td>
											<s:property value='#m.shareTime' />
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
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
