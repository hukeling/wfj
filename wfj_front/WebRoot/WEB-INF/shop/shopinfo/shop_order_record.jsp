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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">结算记录明细</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopOrder/shopApplyRecord.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:10px; border-bottom: 1px #ccc dotted;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							起始日期:
						</div>
						<div>
							<input id="stime" style="width: 150px; border:0; height:22px; line-height: 22px; border:1px #ccc solid;" name="startTime"  type="text"  
						    onclick="WdatePicker({lang:'cn', maxDate:'#F{$dp.$D(\'etime\',{d:-1})||\'2020-10-01\'}'})"  value="${startTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div class="FontSize14" style="line-height:24px;">
							结束日期:
						</div>
						<div>
							<input id="etime" style="width: 150px;border:0; height:22px; line-height: 22px; border:1px #ccc solid;" type="text" name="endTime" 
						    onclick="WdatePicker({minDate:'#F{$dp.$D(\'stime\',{d:1})}',maxDate:'2020-10-01',lang:'cn'})" value="${endTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div>
							<input type="submit" value="" onclick="changePage('${pageHelper.prePageIndex}');"
								class="publicNoBorder ColorWhite1 FontSize14" style=" cursor: pointer; height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg)">
						</div>
					</aside>
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table style="width: 100%;border: 0;" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td style="width:14%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										申请结算金额
									</td>
									<td style="width:14%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
									佣金
									</td>
									<td style="width:14%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
									结算金额
									</td>
									<td style="width:14%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										状态
									</td>
									<td style="width:17%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										结算信息
									</td>
									<td style="width:17%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										申请时间
									</td>
									<td style="width:10%; border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										操作
									</td>
								</tr>
								<s:iterator value="productInfoList">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											￥<fmt:formatNumber value="${totalCost}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											￥<fmt:formatNumber value="${commission}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											￥<fmt:formatNumber value="${totalCost-commission}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											<s:if test="status==1">未审核，请等待审核</s:if>
											<s:elseif test="status==2">已通过,等待结算</s:elseif>
											<s:elseif test="status==3">未通过，请与平台联系</s:elseif>
											<s:elseif test="status==4">已结算，请查收账单</s:elseif>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											<s:property value="paymentInfo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid; border-left:1px #ccc solid;">
											<a style="color:#1da1de;" href="${pageContext.request.contextPath}/front/store/shopOrder/settlementDetail.action?settlementId=<s:property value='settlementId'/>">查看结算明细</a>
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
