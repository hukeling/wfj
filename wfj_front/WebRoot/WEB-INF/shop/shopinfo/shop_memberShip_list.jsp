<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户店铺会员列表</title>
<%@ include file="../include/head.jsp"%>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage_us.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	//店铺查看会员基本信息
	function customerDetail(id){
		$.ajax({
		   type: "POST",
		   dataType: "JSON",
		   url: "${pageContext.request.contextPath}/front/memberShip/getCustomerShop.action",
		   data: {customerId:id},
		   success: function(data){
			   var customer = data.customer;
			   $("#loginName").html(customer.loginName);
			   $("#phone").html(customer.phone);
			   $("#email").html(customer.email);
			   $("#detail-customer-overlay").overlay().load();//加载浮层
		   }
        });
	}

	//店铺审核会员申请信息
	function memberAudit(id){
		$.ajax({
			   type: "POST",
			   dataType: "JSON",
			   url: "${pageContext.request.contextPath}/front/memberShip/getMemberShipObject.action",
			   data: {memberShipId:id},
			   success: function(data){
				   $("#state_"+data.state).attr("checked",true);
				   if(data.state==2){
				   		$("#ids").show();
				   		$("#discount").val(Number(data.discount).toFixed(1));//显示折扣值
				  }else{
					    $("#ids").hide();
					    $("#discount").val("");//重置折扣值
				  }
			   }
	    });
		$("#edit-payway-overlay").overlay().load();//加载浮层
		//审核会员申请，设置折扣比例
		$("#setDiscount").click(function(){
			var state = $("input[name='memberShip.state']:checked").val();
			var discount = $("#discount").val();
			var flag=checkDis(discount);
			if(flag||state!=2){
				$.ajax({
				   type: "POST",
				   dataType: "JSON",
				   url: "${pageContext.request.contextPath}/front/memberShip/auditMemberShip.action",
				   data: {memberShipId:id,state:state,discount:discount},
				   success: function(data){
					   window.location.reload();
				   }
				});
			}
		});
	}
	
	//设置折扣比例
	function selectState(obj){
		  var typeValue = obj.value;
		  if(typeValue=="2"){
			  $("#ids").show();
		  }else{
			  $("#ids").hide();
		  }
	};
	//校验折扣比例
	function checkDis(discount){
		if(discount!=null&&discount!=""){
			 var str = discount.split(".");
			 if(str.length!=2){
				 $("#dis").html("<font color='red'>格式必须为  *.* ! 如:5.6</font>");
				 return false;
			 }else{
				 $("#dis").html("");
				 return true;
			 }
		}else{
			$("#dis").html("<font color='red'>不能为空 !</font>");
			return false;
		}
	 }
</script>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
		<div class="ClearBoth">
			<%@ include file="../include/left_shop.jsp"%>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
				<section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店铺会员列表</h3>
					<form id="formModule" action="${pageContext.request.contextPath}/front/memberShip/memberApplyList.action" method="post">
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="15%" style="padding:12px 0;">会员登录名</td>
								<td width="15%" style="padding:12px 0;">折扣比例</td>
								<td width="20%" style="padding:12px 0;">申请时间</td>
								<td width="11%" style="padding:12px 0;">审核时间</td>
								<td width="7%" style="padding:12px 0;">审核状态</td>
								<td width="5%" style="padding:12px 0;">操作</td>
							</tr>
							<s:iterator value="memberShipList">
								<tr>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;"><a style="color: blue;" href="javascript:customerDetail('<s:property value="customerId" />');"><s:property value="loginName" /></a></td>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;">
										<s:if test="discount!=null&&discount!=''">
											<fmt:formatNumber value="${discount}" pattern="#,##0.0#" />折
										</s:if>
										<s:else>无</s:else>
									</td>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;"><s:date name="auditTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;"><span>
											<s:set name="state" value="<s:property value='state' />" /> 
											<s:if test="state == 1">
												<p class="ColorGreennn" style="margin:0;">待审核</p>
											</s:if> 
											<s:elseif test="state == 2">
												<span style="color: blue;">审核通过</span>
											</s:elseif><s:elseif test="state == 3">
												<p style="color: green;">审核拒绝</p>
											</s:elseif>
									</span></td>
									<td style="border-bottom: 1px #d2d2d2 dashed; padding:12px 0;"><a style="color:#ec3d00;" href="javascript:memberAudit('<s:property value="memberShipId" />');">审核</a></td>
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
				<div class="modal" id="edit-payway-overlay" style="width: 300px;height: 100px;">
					<form id="edit-Discount-form" method="post" action="">
						<table>
						<tr>
							<td>审核状态：</td>
							<td>
								<input id="state_1" type="radio" name="memberShip.state" value="1" onclick="selectState(this);" checked="checked"/>待审核&nbsp;&nbsp;
								<input id="state_2" type="radio" name="memberShip.state" value="2" onclick="selectState(this);"/>审核通过&nbsp;&nbsp;
								<input id="state_3" type="radio" name="memberShip.state" value="3" onclick="selectState(this);"/>审核拒绝
							</td>
						</tr>
						<tr id="ids" style="display: none;">
							<td>折扣比例：</td>
							<td><input id="discount" type="text" value="" style="width: 50px;" maxlength="3" onblur="checkDis(this.value)"/>折<span id="dis"></span></td>
						</tr>
						</table>
					</form>
					<p class="modal-buttons" style="margin-left: 60px;margin-top: 20px;">
						<button type="submit" id="setDiscount" style="border: 0;">确定</button>
						<button class="close" type="button" style="border: 0; margin-left: 5px;">关闭</button>
					</p>
				</div>
				<div class="modal" id="detail-customer-overlay" style="width: 300px;height: 150px;">
					<table>
					<tr>
						<td style="text-align: right;">登录账号：</td>
						<td><span id="loginName"></span></td>
					</tr>
					<tr>
						<td style="text-align: right;">手机号：</td>
						<td><span id="phone"></span></td>
					</tr>
					<tr>
						<td style="text-align: right;">邮箱：</td>
						<td><span id="email"></span></td>
					</tr>
					</table>
					<p class="modal-buttons" style="margin-left: 110px;margin-top: 20px;">
						<button class="close" type="button" style="border: 0; margin-left: 5px;">关闭</button>
					</p>
				</div>
			</div>
			<!--//right-->
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
