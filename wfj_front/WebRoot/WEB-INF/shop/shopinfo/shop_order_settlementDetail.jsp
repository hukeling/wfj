<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单列表</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		function apply(id){
			//循环组拼选中的id数组
			$(".checkboxClass").each(function(){
				if(this.checked){
					id+=","+this.value;
				}
			});
			//组拼订单id串
			id=id.substring(1,id.length);
			$.ajax({
	  			url:"${pageContext.request.contextPath}/front/store/shopOrder/updateOrdersState.action",
	  			type:"post",
	  			dataType:"json",
	  			data:{ids:id},
	  			success:function(data){
	  				window.location.reload(); //删除后重新加载列表
	  			}
  			});
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">申请结算的订单
				<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopApplyRecord.action"><span style="margin-left:650px;color:blue;">返回申请结算记录列表</span></a>
				</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopOrder/settlementDetail.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:10px; border-bottom: 1px #ccc dotted;">
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table style="width: 100%;border: 0;" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td style="width: 14%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										订单号
									</td>
									<td style="width: 17%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										订单金额
									</td>
									<td style="width: 17%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										佣金
									</td>
									<td style="width: 17%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										结算金额
									</td>
								</tr>
								<s:iterator value="ordersList">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value='ordersNo'/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											￥<fmt:formatNumber value="${finalAmount}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											￥<fmt:formatNumber value="${yj}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											￥<fmt:formatNumber value="${js}" pattern="#,##0.00#" />
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
