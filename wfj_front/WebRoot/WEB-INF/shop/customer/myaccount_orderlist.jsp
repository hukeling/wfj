<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户订单列表</title>
<%@ include file="../include/head.jsp"%>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
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
	$(function() {
		$("#ordersStatus").val($("#selectStatus").val());
	});
	function hbfk(id) {
		var flag = true;
// 		循环组拼选中的id数组
		var check = $(".checkboxClass");
		for(var i=0;i<check.length;i++){
			if(check[i].checked){
				id += "," + check[i].value;
				if($("#orderstates_"+check[i].value).val()!=0){
					flag = false;
				}
			}
		}
		if(!flag){
			lalert("提醒","当前选中的订单存在已付款,无法操作!");
		}else{
// 			组拼订单id串
			id = id.substring(1, id.length);
			location.href = "${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?ids="+ id;
		}
	}
	function dc(id) {
		//循环组拼选中的id数组
		$(".checkboxClass").each(function() {
			if (this.checked) {
				id += "," + this.value;
			}
		});
		//组拼订单id串
		id = id.substring(1, id.length);
		if(id==""){
			lalert("提醒","请选择要导出的订单");
		}else{
			$.ajax({
				type: "POST",
				dataType: "JSON",
				url : "${pageContext.request.contextPath}/front/customer/myOrders/importExcelCom.action",
				data: {ids:id},
				success:function(data){
					if(data.isSuccess=="true"){
						var url=data.filePathXLS;
						window.location.href="${pageContext.request.contextPath}/downloadFile.action?downloadFileUrl="+url+"&downloadFileName="+data.downloadFileName;
					}else{
						lalert("提醒","导出失败");
					}
				}
			});
		}
	} 
	
	//取消订单是触发函数 防止重复提交
	function turnCheck(value){
		var url="${pageContext.request.contextPath}/front/customer/myOrders/cancelOrder.action?orders.ordersId="+value;
		lconfirm("提醒","确定取消订单!",function(o){
				  window.location.href=url;
		});
	}
	
	//确认收货前的提示
	var affirmOption = function(ordersId){
		lconfirm("提醒","请确认己收到货物，否则将财物两空！",function(o){
			var url='${pageContext.request.contextPath}/front/customer/myOrders/confirmOrder.action?orders.ordersId='+ordersId;
			window.location.href = url;
		});
	}
</script>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
		<div class="ClearBoth">
			<s:if test="#session.customer.type==1">
				<%@ include file="../include/left_account.jsp"%>
			</s:if>
			<s:elseif test="#session.customer.type!=2">
				<%@ include file="../include/left_account_gr.jsp"%>
			</s:elseif>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
				<section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">订单列表</h3>
					<form id="formModule"
						action="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action"
						method="post">
						<aside class="accountOrder_search ClearBoth publicMarginBot15"
							style="height:22px;">
							<div class="FontSize14" style="line-height:22px;">
								<s:if test="#session.sonaccount.type==2">
									付款状态 
								</s:if>
								<s:if test="#session.sonaccount.type==1">
									订单状态
								</s:if>
								<s:if test="#session.sonaccount==null">
									交易状态
								</s:if>
							:</div>
							<div>
								<input type="hidden" id="selectStatus"
									value="${orders.ordersState}" /> <select id="ordersStatus"
									name="orders.ordersState"
									onchange="changePage('1');"
									style="padding:0px;">
									<option id="status" value="">全部</option>
									<s:if test="#session.sonaccount.type==2">
										<option id="status1" value="0">未付款</option>
										<option id="status2" value="1">已付款</option>
									</s:if>
									<s:if test="#session.sonaccount.type==1">
										<option id="status3" value="3">正在配货</option>
										<option id="status4" value="4">已发货</option>
										<option id="status5" value="5">已收货</option>
										<option id="status6" value="6">订单取消</option>
										<option id="status6" value="9">已评价</option>
									</s:if>
									<s:if test="#session.sonaccount==null">
										<option id="status1" value="0">未付款</option>
										<option id="status2" value="1">已付款</option>
										<option id="status3" value="3">正在配货</option>
										<option id="status4" value="4">已发货</option>
										<option id="status5" value="5">已收货</option>
										<option id="status6" value="6">订单取消</option>
										<option id="status6" value="9">已评价</option>
									</s:if>
								</select>
							</div>
							<div class="FontSize14" style="line-height:22px;">日期:</div>
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
							<div class="FontSize14" style="line-height:22px;">订单编号:</div>
							<div>
								<input name="orders.ordersNo" value="${orders.ordersNo}"
									type="text" class="widthpx180" style="width: 120px"/>
							</div>
							<%-- <s:if test="#session.sonaccount.type!=1&&#session.customer.type!=3">
								<div>
									采购人员: <input name="orders.buyerName"
										value="${orders.buyerName}" type="text" class="widthpx180"
										style="width: 120px"/>
								</div>
							</s:if> --%>
							<div>
								<input name="" type="submit"
									class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
									style="padding: 2px 5px;"
									value="查询" />
							</div>
							<%--	<div class="float-right publicHidden">
										<input type="button" onclick=" dc('');"
											class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
											style="padding: 2px 5px;margin-left: 1px;" value="导出" />
								</div>
							 <s:if test="(#session.customer.type==1&&#session.sonaccount.type==2)||#session.customer.type!=2">
								<div class="float-right publicHidden">
										<input type="button" onclick=" hbfk('');"
											class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
											style="padding: 2px 5px;" value="合并付款" />
								</div>
							</s:if> --%>
						</aside>
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<%-- <td width="5%"><input id="selectAll" class="checkboxClass2" type="checkbox" name="ordersInfo" onclick="selectAllCheckBox(this.id)" value=""/><span class="selectAll2"></span></td> --%>
								<td width="15%">订单编号</td>
								<td width="20%">商品</td>
								<td width="11%">收货人</td>
								<!-- <td width="11%">
									金额
								</td> -->
								<%-- <s:if test="#session.customer.type!=3">
									<td width="10%">使用授信额度</td>
								</s:if>
								<s:else>
									<td width="10%">金额</td>
								</s:else> --%>
								<td width="10%">金额</td>
								<td width="7%">下单时间</td>
								<td width="8%">订单状态</td>
								<td width="8%">付款状态</td>
								<%-- <s:if test="#session.customer.type!=3">
									<td width="10%">采购员</td>
								</s:if> --%>
								<td width="10%">操作</td>
							</tr>
							<s:iterator value="ordersMap" var="listMap">
								<s:iterator value="#listMap.value" status="index">
									<s:if test="#listMap.value.size>1&&#index.index==0">
										<tr>
											<td
												style="font-size: 12px; border-bottom: 1px #d2d2d2 dashed; border-top: 1px #d2d2d2 dashed;"
												colspan="9" width="100%">
												尊敬的用户，由于你的商品不是在同一个店铺，您的订单被系统拆分为多个子订单分开配送，所产生的额外运费由SHOPJSP承担，给你带来不便请谅解。<br />
												<!-- 										<span style="float:right; padding-right:8px;"> -->
												<!-- 											<a href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.totalOrdersNo=<s:property value='totalOrdersNo' />"> -->
												<!-- 												<img src="${pageContext.request.contextPath }/common/shop/front/images/mergerPay.png"></img> -->
												<!-- 											</a> --> <!-- 										</span> -->
											</td>
										</tr>
									</s:if>
									<tr>
									<%-- <td>
											<input class="checkboxClass" name="ordersInfo" onclick="doCheckBox()" id="checkbox_<s:property value="ordersId" />" type="checkbox" value="<s:property value="ordersId" />"/>
											<input type="hidden" id="orderstates_<s:property value="ordersId" />" value="<s:property value='settlementStatus'/>"/>
										</td> --%>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><a href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="ordersId" />"><s:property
													value="ordersNo" /> </a></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
											<div class="ClearBoth" style="text-align: center;">
												<s:iterator value="mapImg">
													<a target="_blank"
														href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
														<img style="height: 50px; width:50px;"
														title="<s:property value="productName"/>"
														alt="<s:property value="productName"/>"
														onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}images/blank.gif'"
														src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="thumbnail"/>" />
													</a>
												</s:iterator>
											</div>
										</td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:property
												value="consignee" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><span
											class="ColorRed FontSizeB">￥<fmt:formatNumber
													value="${finalAmount}" pattern="#,##0.00#" />
										</span></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><s:date
												name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;"><span>
												<s:set name="ordersState"
													value="<s:property value='ordersState' />" /> <s:if
													test="ordersState == 0">
													<p class="ColorGreennn" style="margin:0;">生成订单</p>
												</s:if> <s:elseif test="ordersState == 1">
													<span style="color: blue;"> 已付款 </span>
												</s:elseif> <s:elseif test="ordersState == 3">
													<p style="color: green;">正在配货</p>
												</s:elseif> <s:elseif test="ordersState == 4">
													<p style="color: purple;">已经发货</p>
												</s:elseif> <s:elseif test="ordersState == 5">
													<p class="ColorGreennn">已收货</p>
												</s:elseif> <s:elseif test="ordersState == 6">
													<span style="color: fuchsia;"> 订单取消 </span>
												</s:elseif> <s:elseif test="ordersState == 7">
													<span style="color: fuchsia;"> 问题订单 </span>
												</s:elseif> <s:elseif test="ordersState == 9">
													<p class="ColorGreennn">已评价</p>
												</s:elseif>
										</span></td>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
												<s:set name="settlementStatus" value="<s:property value='settlementStatus' />" /> 
												<s:if test="settlementStatus==0">
													<span id="wfk_0" class="ColorGreennn" style="margin:0;" >未付款</span>
												</s:if> 
												<s:elseif test="settlementStatus==1">
													<span id="yfk_1" style="color: blue;" > 已付款 </span>
												</s:elseif></td>
										<%-- <s:if test="#session.customer.type!=3">
											<td><span><s:property value="buyerName" /></span></td>
										</s:if> --%>
										<td style="border-bottom: 1px #d2d2d2 dashed;">
											<!-- 企业用户付款  -->
											<%-- <s:if test="#session.customer.type==1&&#session.sonaccount.type==2&&settlementStatus==0">
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.ordersId=<s:property value='ordersId' />">立即付款</a>
												<br />
											</s:if> --%> <!--普通用户 --> 
											<s:if test="#session.customer.type!=2&&ordersState==0&&settlementStatus==0">
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/orderToPay.action?orders.ordersId=<s:property value='ordersId' />">立即付款</a>
												<br />
											</s:if> 
											<s:if test="ordersState==0">
												<s:if test="#session.customer.type!=2&&settlementStatus==1">
													<a href="javascript:affirmOption(<s:property value='ordersId' />);">确认收货</a>
													<br/>
												</s:if>
												<s:if test="#session.customer.type!=2&&settlementStatus==0">
													<a href="javascript:turnCheck(<s:property value='ordersId' />)" >取消订单</a>
													<br />
												</s:if>
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId'/>">详情</a>
												<br />
											</s:if> 
											<s:elseif test="ordersState == 4">
												<s:if test="#session.customer.type!=2">
													<a href="javascript:affirmOption(<s:property value='ordersId' />);">确认收货</a>
													<br />
												</s:if>
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
											</s:elseif>
											 <s:elseif test="ordersState == 5">
												<s:if
													test="#session.customer.type!=2">
													<a
														href="${pageContext.request.contextPath}/front/customer/orderComment.action?order.ordersId=<s:property value='ordersId' />">评价</a>
													<br/>
												</s:if>
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
												<br/>
												<s:if
													test="#session.customer.type!=2">
													<a
														href="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/gotoCustomerComplaintsPage.action?ordersId=<s:property value='ordersId' />">退货</a>
												</s:if>
											</s:elseif> 
											<s:elseif test="ordersState == 9">
												<a
													href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
												<br/>
												<s:if
													test="#session.customer.type!=2">
													<a
														href="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/gotoCustomerComplaintsPage.action?ordersId=<s:property value='ordersId' />">退货</a>
												</s:if>
											</s:elseif> <!-- 付款之后，异常订单，取消订单 --> 
											<s:else>
												<a href="${pageContext.request.contextPath}/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value='ordersId' />">详情</a>
											</s:else>
										</td>
									</tr>
								</s:iterator>
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
