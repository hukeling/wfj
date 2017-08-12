<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>供应商佣金币分享明细</title>
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">供应商佣金币分享明细</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/supplierMallCoin/gotoSupplierBrokerageCoinDetailListPage.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							订单号:
						</div>
						<div>
							<input id="qordersNo" name="ordersNo" style="height:22px; line-height: 22px; border:1px #ccc solid;" type="text" value="${orders.ordersNo}"/>
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
					</div>
					</aside>
					<div class="MarketpalceTree publicMarginTop15" style="width:963px;">
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td width="18%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
										订单号
									</td>
									<td width="14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										赠送佣金币
									</td>
									<td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										总支出
									</td>
									<td width="12%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										交易时间
									</td>
								</tr>
								<s:iterator value="SupplierBrokerageCoinDetailList" var="map">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.ordersNo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.giveBrokerageCoin" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.totalOutPut" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.tradingTime" />
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
					<div id="rightBox" class="float-right publicHidden">
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">统计</h3>
						<table width="100%" border="0" class="RecentlyOrders_table">
								<tr>
									<td width="16.6%">
										总支出
									</td>
									<td width="16.6%">
										<fmt:formatNumber value="${expenditure}" pattern="#,##0.00#" type="number"/>
									</td>
								</tr>
						</table>
						</div>
			</div>
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
