<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>供应商商城币提现列表</title>
		<%@ include file="../include/head.jsp"%>
        <link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}css/themes/default/easyui.css"/>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/common.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
	<script>
		//全选和反选
		function selectAllCheckBox(id) {
			var b = document.getElementsByName("ordersInfo");
			var all = document.getElementById(id);
			if (all != null) {
				if (all.checked) {
					for (var i = 0; i < b.length; i++) {
						b[i].checked = true;
					}
				} else {
					for (i = 0; i < b.length; i++) {
						b[i].checked = false;
					}
				}
			}
		}
		//点击一个checkbox的操作
		function doCheckBox() {
			//获取选中checkbox的长度
			var checkLength = $('input[name=ordersInfo]:checked').length;
			//获取所有checkbox的长度（除全选checkbox外）
			var totalLength = $('input[name=ordersInfo]').length;
			if (checkLength == totalLength) {
				//选中全选checkbox
				$("#selectAll").attr("checked", true);
			} else {
				$("#selectAll").removeAttr("checked");
			}
		}
		//导出excel方法
		function daochuExcel(){
			window.location.href="${pageContext.request.contextPath}/front/store/supplierMallCoin/exportSCBTXExcel.action?state="+$("#qstate").val()+"&startTime="+$("#stime").val()+"&endTime="+$("#etime").val();
		}
</script>
<style>
	/* 表格两端封口样式 */
	.RecentlyOrdersTable_lj tr {
	    border-bottom: 1px solid #d2d2d2;
	    border-left: 1px solid #d2d2d2;
	    border-right: 1px solid #d2d2d2;
	}
</style>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div id="overly_div" class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">供应商商城币提现列表</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/supplierMallCoin/gotoSCBTXListPage.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<%-- <div class="FontSize14" style="line-height:24px;">
							流水号:
						</div>
						<div>
							<input id="qserialNumber" name="serialNumber" style="height:22px; line-height: 22px; border:1px #ccc solid;" type="text" value="${serialNumber}"/>
						</div> --%>
						<div class="FontSize14" style="line-height:24px;">
							交易状态
						</div>
						<div>
							<select id="qstate" name="state" style="height:22px; line-height: 22px; border:1px #ccc solid;">
								<option value="">全部</option>
								<option value="1">申请提现</option>   
								<option value="2">审核通过</option>   
								<option value="3">审核未通过</option>   
								<option value="4">支付完成</option>   
								<option value="5">支付失败</option>   
							</select>
						</div>
						<div class="FontSize14" style="line-height:22px;">交易时间:</div>
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
							<input type="button" value="" onclick="changePage('1');"class="publicNoBorder ColorWhite1 FontSize14" style=" cursor: pointer; height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg)"/>
						</div>
						<div>
							<input type="button"onclick="daochuExcel();" class="publicNoBorder ColorWhite1 FontSize14" style="cursor: pointer; cursor: pointer;  height:22px; width:68px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/dcexcel.jpg) no-repeat;"/>
						</div>
					</div>
					</aside>
					<div class="MarketpalceTree publicMarginTop15" style="width:963px;">
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
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
										申请时间
									</td>
								</tr>
								<s:iterator value="shopMallCoinWithdrawalsList" var="m">
									<tr>
										<td width="10%">
											<s:property value="#m.cardHolder"/>
										</td>
										<td width="16%">
											<a style="text-decoration: none;" title="<s:property value="#m.cardNumber"/>"><s:property value="#m.cardNumber"/></a>
										</td>
										<td width="13%">
											<s:property value="#m.phone"/>
										</td>
										<td width="10%">
											<s:property value="#m.transactionAmount"/>
										</td>
										<td width="10%">
											<a style="text-decoration: none;" title="<s:property value="#m.customerMessage"/>">
												<s:if test="#m.customerMessage.length()>7">
													<s:property value='#m.customerMessage.substring(0,6)'/>...
												</s:if><s:else>
													<s:property value='#m.customerMessage'/>
												</s:else></a>
										</td>
										<td width="8%">
											<s:if test="#m.state==1">
												申请提现
											</s:if>
											<s:elseif test="#m.state==2">
												审核通过
											</s:elseif>
											<s:elseif test="#m.state==3">
												审核未通过
											</s:elseif>
											<s:elseif test="#m.state==4">
												支付完成
											</s:elseif>
											<s:elseif test="#m.state==5">
												支付失败
											</s:elseif>
										</td>
										<td width="15%">
											<s:property value="#m.tradeTime"/>
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
				<!--//right-->
			</div>
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
