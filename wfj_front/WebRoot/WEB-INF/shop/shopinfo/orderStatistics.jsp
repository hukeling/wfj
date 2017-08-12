<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单统计</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			//导出Excel
			function apply(){
				var l = $("#jsonSize").val();
				if(l>0){
					var beginTime = $("#beginTime").val();
					var endTime = $("#endTime").val();
					$.ajax({
						type: "POST",
						dataType: "JSON",
						url : "${pageContext.request.contextPath}/front/store/orderStatistics/importExcelTJDD.action",
						data: {beginTime:beginTime,endTime:endTime},
						success:function(data){
							if(data.isSuccess=="true"){
								var url=data.filePathXLS;
								window.location.href="${pageContext.request.contextPath}/downloadFile.action?downloadFileUrl="+url+"&downloadFileName="+data.downloadFileName;
							}else{
								lalert("提醒", "导出失败点击确定关闭");
							}
						}
					});
				}else{
					lalert("提醒", "没有数据，不能导出");
				}
			}
		</script>
	</head>

	<body>
		<input id="jsonSize" type="hidden" value="<s:property value='collectiveOrderList.size()'/>" />
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">订单统计</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/orderStatistics/gotoCollectiveOrderPage.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:10px; border-bottom: 1px #ccc dotted;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							从<input id="beginTime" name="beginTime" value="${beginTime }" style="height: 16px;width: 100px;" class="Wdate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
							到<input id="endTime" name="endTime"  value="${endTime }" style="height: 16px;width: 100px;" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'#F{$dp.$D(\'beginTime\',{d:30})}'})"/>   
						</div>
						<div>
							<input type="submit" value=""
								class="publicNoBorder ColorWhite1 FontSize14" style=" cursor: pointer;  height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg) no-repeat;"/>
						</div>
						<div>
							<input type="button"onclick="apply();" class="publicNoBorder ColorWhite1 FontSize14" style="cursor: pointer; cursor: pointer;  height:22px; width:68px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/dcexcel.jpg) no-repeat;"/>
						</div>
					</aside>
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table style="width: 100%;border: 0;overflow-x:scroll;overflow-x:auto;overflow-y:hidden;" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td style="width: 18%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
										订单编号
									</td>
									<td style="width: 14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										订单生成时间
									</td>
									<td style="width: 17%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										商户店铺名称
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										买家会员名称
									</td>
									<!-- <td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										收货人名称
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										付款时间
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										配货时间
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										发货时间
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										收货时间
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										商品总金额(元)
									</td> -->
								</tr>
								<s:iterator value="collectiveOrderList">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="ordersNo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="createTime" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="shopName" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="loginName" />
										</td>
									<%-- 	<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="consignee" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="payTime" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="distributionTime" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="deliversTime" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="receivesTime" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											￥<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00#" />
										</td> --%>
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
