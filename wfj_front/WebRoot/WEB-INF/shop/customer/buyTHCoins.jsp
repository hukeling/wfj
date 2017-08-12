<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>购买SHOPJSP币</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<style type="text/css">
			.ClearBoth2 ul li{wdith:800px;height:30px;}
			.ClearBoth2 ul li.ClearTitile{height:25px; text-align:left;padding-left:25px;font-weight:900;font-size:15px;}
			.ClearBoth2 ul li span{width:85px;display:block; float:left;height:30px;margin-right:5px;line-height:30px; text-align:left;}
			*+html .ClearBoth2 ul li{wdith:800px;height:26px;}
			*+html .ClearBoth2 ul li.ClearTitile{width:800px;height:21px; text-align:left;}
			*+html .ClearBoth2 ul li span{width:225px;display:block; float:left;height:26px;margin-right:15px;line-height:26px; text-align:left;padding-left:25px;}
		    .publicNoBorder ul.ConInformation li input{border:none;}
		    .publicNoBorder ul.ConInformation li input, .publicNoBorder ul.ConInformation li select{color:#fff;}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#addressSwitchDiv").hide();
				$(".updateSafe").click(function(){
					 $("#addressSwitchDiv").show();
				})
				//点击cancel隐藏添加地址
			    $("#addressSwitchCancel").click(function() {
			        $("#addressSwitchDiv").hide();
			    });
			});
			//表单验证提交
			$(function() { //表单验证
			    $("#alipayment").validate({
			        meta: "validate",
			        submitHandler: function(form) {
						form.submit();
			        }
			    });
			});
			$(document).ready(function() {
					$("#to_alipay").click(function(){
						var triggers = $('#edit-payway-alipay').overlay({
									mask: {
							        color: '#ebecff',
							        loadSpeed: 200,
							        opacity: 0.4
							      },
							closeOnEsc:false,
							closeOnClick: false
						});
						if($('input[name="opamount"]').val().length>0){
							$('#edit-payway-alipay').overlay().load();//加载浮层
						}
						$("#payProblem").click(function(){
							window.location="${pageContext.request.contextPath}/front/customer/thcions/gotobuyTHCoins.action";
						});
						$("#paySuccess").click(function(){
							window.location="${pageContext.request.contextPath}/front/customer/brokerageManage/getSuperTerminalVirtualCoinDetail.action";
						});
					});
			});
		</script>
		
		
	</head>

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
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">购买SHOPJSP币</h3>
						<fieldset class="publicNoBorder">
							<form id="alipayment" name="alipayment" action="${pageContext.request.contextPath}/alipay/alipayapi.action" method="post" target="_blank">
<!-- 							<form id="alipayment" name="alipayment" target="_black" action="${pageContext.request.contextPath}/front/customer/thcions/saveBuyInfo.action" method="post"> -->
								<div style="padding-left:25px;">
								<ul class="strong" >
									<li class="ClearTitile" style="padding:10px 0;">提醒：当前SHOPJSP币购买比例为<font color="red">1:<s:property value='proportion'/></font></li>
									<li style="width: 600px; padding:10px 0;">
										<span style="text-align:right;*float:left;*line-height:25px;*margin-right:5px;" ><i class="ColorRed">*</i>&nbsp;&nbsp;购买金额:</span>
										<span>
											<input style="line-height: 25px;" name="opamount" value="<s:property value="safetyCertificate.cooperatorPID"/>" type="text" class="widthpx150 height25 {validate:{required:true,digits:true}}">
										</span>
									</li>									
								</ul>
								<jsp:include page="thCoinsPay.jsp"></jsp:include>
								</div>
							</form>
						</fieldset>
					</section>
				</div>
				<!--//right-->
			</div>
			<div class="modal" id="edit-payway-alipay" style="width: 300px;height: 100px;">
				<h2 class="modal-title">
					支付
				</h2>
				<div class="modal-content">
				<p class="modal-buttons">
					<button  style="width:100px;background-color:#D01743 ;border-radius:5px;font-size:16px;color:#fff;font-weight:bold;" id="paySuccess">支付成功</button>
					<button  class="close" style="width:100px;background-color:#D01743 ;font-size:16px;color:#fff;font-weight:bold;" id="payProblem">
						遇见问题
					</button>
				</p>
			</div>
	</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
