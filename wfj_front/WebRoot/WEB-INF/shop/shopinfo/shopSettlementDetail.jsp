<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>店铺申请结算</title>
	<%@ include file="../include/head.jsp"%>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
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
<SCRIPT type="text/javascript">
$(function(){
	$("#s1").bind('click',function(){
		var ym = $("#d4").val();
		var cycle = $("#cycle").val();
		if(ym==""||cycle==""){
			$("#msg").html("<font color='red'>请选择年月和周期！</font>");
		}else{
			$.ajax({
				type: "POST",
				dataType: "JSON",
				url: "${pageContext.request.contextPath}/front/store/settlement/check.action",
				data: {ym:ym,cycle:cycle},
				success: function(data){
					if(data.isSuccess=="false1"){
						$("#msg").html("");
						$("#msg").html("<font color='red'>该月已申请结算!</font>");
					}else if(data.isSuccess=="false2"){
						$("#msg").html("");
						$("#msg").html("<font color='red'>该周期已申请结算!</font>");
					}else if(data.isSuccess=="false3"){
						$("#msg").html("");
						$("#msg").html("<font color='red'>该 月中部分周期已申请结算!</font>");
					}else{
						var options = {  
	                        url : "${pageContext.request.contextPath}/front/store/settlement/applySettlement.action",  
	                        type : "post",  
	                        dataType:"json",
	                        success : function(data) {
	                       		if(data.isSuccess=="true"){
	                       			$('#alipaySettlementDiv').overlay().load();//加载浮层
	                       		}else if(data.isSuccess="false2"){
	                       			$("#msg").html("<font color='red'>该周期内销售额为0!</font>");
	                       		}else if(data.isSuccess="false1"){
	                       			$("#msg").html("<font color='red'>申请失败!</font>");
	                       		}
	                        }
                     	}; 
						$("#formModule1").ajaxSubmit(options); 
					}
				}
			});
		}
	});
});
$(document).ready(function() {
	var triggers = $('#alipaySettlementDiv').overlay({
				mask: {
		        color: '#ebecff',
		        loadSpeed: 200,
		        opacity: 0.4
		      },
		closeOnEsc:false,
		closeOnClick: false
	});
	$("#applySuccess").click(function(){
		window.location.href="${pageContext.request.contextPath}/front/store/settlement/gotoShopSettementDetail.action";
	});
});
</SCRIPT>	
</head>
	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店铺申请结算</h3>
				<form id="formModule1" action="${pageContext.request.contextPath}/front/store/settlement/applySettlement.action" method="post">
				<aside class="shop_orderSearch ClearBoth">
					<p>
						<span style="font-weight: 100;">年月:</span>
						<input type="text" name="ym" style="height:23px;" class="Wdate" id="d4" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-{%M}'})"/>
					</p>
					<p> 
						<span style="font-weight: 100;">周期:</span>
						<select id="cycle" name="cycle">
								<option value="">请选择</option>
								<s:iterator value="#application.keybook['settlementCycle']" var="kb">
									<s:property value="#kb.name"/>
									<option value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
								</s:iterator>
						</select>
					</p>
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div>
							<input id="s1" type="button" value="申请结算" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="cursor: pointer;">
						</div>
						<div>
							<span id="msg"></span>
						</div>
					</aside>
				</aside>
				<div style="height:5px; width:963px; border-bottom: 1px #333 dashed;"></div>
				
				</form><br/>
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">申请结算历史查询</h3>
				<form id="formModule2" action="${pageContext.request.contextPath}/front/store/settlement/gotoShopSettementDetail.action" method="post">
				<aside class="shop_orderSearch ClearBoth">
					<p>
						<span style="font-weight: 100;">年月:</span>
						<input type="text" name="s_ym" style="height:23px;" class="Wdate" id="s_d4" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-{%M-1}'})"/>
					</p>
					<p> 
						<span style="font-weight: 100;">周期:</span>
						<select id="s_cycle" name="s_cycle">
								<option value="">请选择</option>
								<s:iterator value="#application.keybook['settlementCycle']" var="kb">
									<s:property value="#kb.name"/>
									<option value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
								</s:iterator>
						</select>
					</p>
					<p> 
						<span style="font-weight: 100;">审核状态:</span>
						<select id="s_reviewStatus" name="s_reviewStatus">
								<option value="">请选择</option>
								<option value="0">未审核</option>
								<option value="1">已审核</option>
						</select>
					</p>
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div>
							<input id="s2" type="submit" value="查询" class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="cursor: pointer;">
						</div>
						<div>
							<span id="msg"></span>
						</div>
					</aside>
				</aside>
				<div style="height:5px; width:963px; border-bottom: 1px #333 dashed;"></div>
				</form>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/settlement/gotoShopSettementDetail.action" method="post">
					<div class="">
						<div class="">
							<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
								<tr class="gradient publicBorder">
									<td width="16%">
										会员帐号
									</td>
									<td width="16%">
										结算月份
									</td>
									<td width="16%">
										结算周期
									</td>
									<td width="16%">
										店铺售卖总成本
									</td>
									<td width="16%">
										店铺售卖总销售额
									</td>
									<td width="16%">
										审核状态
									</td>
								</tr>

								<s:iterator value="shopSettlementDetailList" id="s">
									<tr>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:property value='#s.loginName'/>
										</td>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:property value="#s.settlementMonth" />
										</td>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:iterator value="#application.keybook['settlementCycle']" var="kb">
												<s:if test="#kb.value==#s.cycle"><s:property value='#kb.name'/></s:if>
											</s:iterator>
										</td>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:property value='#s.totalCost' />
										</td>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:property value='#s.totalSales' />
										</td>
										<td  style="border-bottom: 1px #d2d2d2 dashed;">
											<s:if test="#s.reviewStatus==0"><span style="color:red;">未审核</span></s:if>
											<s:if test="#s.reviewStatus==1">已审核</s:if>
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
			<div class="modal" id="alipaySettlementDiv" style="width: 300px;height: 100px;">
				<a class="close" style="display:none;"></a>
				<h2 class="modal-title" style="padding-left:112px; background: url("../front/images/info.gif") no-repeat 75px;">
					申请成功
				</h2>
				<div class="modal-content">
				<p class="modal-buttons">
					<button  style="border:1px solid #258CD9; background:#258CD9; border-radius: 4px; margin-left:114px; padding:5px 15px;font-size:16px;color:#fff;font-weight:bold;" id="applySuccess">确认</button>
				</p>
			</div>	
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
