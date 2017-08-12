<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户安全认证</title>
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
			.ClearBoth2 ul li span{width:225px;display:block; float:left;height:30px;margin-right:5px;line-height:30px; text-align:left;padding-left:25px;}
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
			    $("#form1").validate({
			        meta: "validate",
			        submitHandler: function(form) {
						//控制保存按钮变灰，避免重复提交
						$(".submitImg").attr("disabled","disabled");
						var htm="<img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif'/>";
						htm+="&nbsp;&nbsp;正在提交，请稍等...";
						$(".submitImgLoad").html(htm);
						form.submit();
			        }
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">安全认证信息</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<fieldset class="publicNoBorder">
									<ul class="ConInformation margin-center publicMarginTop15">
										<s:if test="safetyCertificate!=null">
										<li>
											<div>
												合作者身份(PID)：
											</div>
											<div>
												<s:property value="safetyCertificate.cooperatorPID"/>
											</div>
										</li>
										<li>
											<div>
												密钥(KEY)：
											</div>
											<div>
												<s:property value="safetyCertificate.secretkey"/>
											</div>
										</li>
										<li style="text-align:right">
											<div >
												<input type="button" class="updateSafe submitButtonL publicPaddingLR10 publicMarginR10" value="修改安全认证" >
											</div>
										</li>
										</s:if>
										<s:else>
											<div class="strong" style="font-size:15px;">你还没有填写安全认证信息，安全认证信息是在支付时候使用的，如果没有填写，或者填写错误都将导致买家无法为商品付款。</div><br/>
											<div>
												<input type="button" class="updateSafe submitButtonL publicPaddingLR10 publicMarginR10" value="填写安全认证" id="Submit_button">
											</div>
										</s:else>
									</ul>
								</fieldset>
							</div>
						</div>
					</div><br/>
					<div class="TabParent_lj publicBorder ClearBoth" id="addressSwitchDiv">
						<fieldset class="publicNoBorder">
						<form id="form1"  action="${pageContext.request.contextPath}/front/customer/Setting/saveSafetyCertificate.action" method="post">
							<input type="hidden" name="safetyCertificate.customerId" value="<s:property value="safetyCertificate.customerId"/>"/>
							<div class="ClearBoth ClearBoth2">
							<ul class="strong" >
								<br/><li class="ClearTitile">提醒：PID和秘钥都是由支付平台提供的，如若没有请去相应的支付平台申请。</li><br/>
								<li>
									<span style="text-align:right" ><i class="ColorRed">*</i>合作者身份(PID)：</span>
									<span >
										<input name="safetyCertificate.cooperatorPID" value="<s:property value="safetyCertificate.cooperatorPID"/>" type="text" class="widthpx200 height25 {validate:{required:true,maxlength:[100]}}">
									</span>
								</li><br/>
								<li>
									<span style="text-align:right"><i class="ColorRed">*</i>密钥(KEY)：</span>
									<span>
										<input name="safetyCertificate.secretkey" value="<s:property value="safetyCertificate.secretkey"/>" type="text" class="widthpx200 height25 {validate:{required:true,maxlength:[100]}}">
									</span>
								</li><br/>
								<li>
								<input type="submit" value="提交"
									class="submitImg submitButtonL publicPaddingLR10 publicMarginR10">
								<input type="button" value="取消"
									class="publicPaddingLR10 publicMarginR10 submitButtonL BackgroundOrange"
									style="background-color: #FF6600 !important;"
									id="addressSwitchCancel">
								<span class="submitImgLoad"></span>
								</li><br/>
							</ul>
							</div>
						</form>
						</fieldset>
					</div>
					</section>
				
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
