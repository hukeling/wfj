<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>发票信息</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
		<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header_top.css"/>
		<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css"/>
		<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index.css"  />
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css"  />
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_1.css" />
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.CardIdNo.js"></script>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
		<style type="text/css">
			.zhuce_zj font{width:156px; height:50px; float:right;font-size:12px; text-align:left; line-height:15px;}
			 label.error {
				background-color: white;
    			border: none;
			    color: red;
			    font-size: 12px;
    			margin: -1px 0 0;
			    overflow: hidden;
			    padding: 2px 10px;
 			}
		</style>
		<script type="text/JavaScript">
		$(function() {
				//表单验证
 	    		$("#form1").validate({meta: "validate", 
			   	      submitHandler:function(form){
				   	        $(".submitImg").attr("disabled","disabled"); 
				   	        $(".submitImgLoad").attr("style","display:block;");
		 			        form.submit();
			          }
		 	    });
			});
	    //企业姓名校验
	    jQuery.validator.addMethod("checkCompanyName",function(value, element) {
	    	var companyName = '${shopInfo.companyName}';
	    	var shopInfoId = '${shopInfo.shopInfoId}';
	    	var flag=true;
	    	//正则校验
	    	var reg=/^[\u4e00-\u9fa5]+$/;
	    	if(value!=companyName){
 				$.ajax({
 					type: "POST",dataType: "JSON",
 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkCompanyName.action",
 					data: {companyName:value,shopInfoId:shopInfoId},
 					async : false,
 					success: function(data){
						if(data.isSuccess==true){
							flag=false;
						}
					}
				});
			}
			return flag;
		},"企业名称已存在！" );
		//纳税人编号校验
	    jQuery.validator.addMethod("checkTaxpayerNumber",function(value, element) {
	    	var taxpayerNumber = '${shopInfo.taxpayerNumber}';
	    	var shopInfoId = '${shopInfo.shopInfoId}';
	    	var flag=true;
	    	if(value!=taxpayerNumber){
 				$.ajax({
 					type: "POST",dataType: "JSON",
 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkTaxpayerNumber.action",
 					data: {taxpayerNumber:value,shopInfoId:shopInfoId},
 					async : false,
 					success: function(data){
						if(data.isSuccess==true){
							flag=false;
						}
					}
				});
			}
			return flag;
		},"您的纳税人编号已存在！" );
		//电话唯一校验
	      jQuery.validator.addMethod("checkPhoneForInvoice",function(value, element) {
	      	var phoneForInvoice = '${shopInfo.phoneForInvoice}';
	      	var shopInfoId = '${shopInfo.shopInfoId}';
	    	var flag=true;
	    	if(value!=phoneForInvoice){
 				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/front/customer/Setting/checkPhoneForInvoice.action",
 				   data: {phoneForInvoice:value,shopInfoId:shopInfoId},
 				   async : false,
 				   success: function(data){
 				   		if(data.isSuccess==true){
	 					   flag=false;
	 				   }
 				   }
 				});
 			}
 			return flag;
		},"该电话已被注册！" );
		//账号校验
	    jQuery.validator.addMethod("checkBankAccountNumber",function(value, element) {
	    	var bankAccountNumber = '${shopInfo.bankAccountNumber}';
	    	var shopInfoId = '${shopInfo.shopInfoId}';
	    	var flag=true;
	    	if(value!=bankAccountNumber){
 				$.ajax({
 					type: "POST",dataType: "JSON",
 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkBankAccountNumber.action",
 					data: {bankAccountNumber:value,shopInfoId:shopInfoId},
 					async : false,
 					success: function(data){
						if(data.isSuccess==true){
							flag=false;
						}
					}
				});
			}
			return flag;
		},"您的账号已存在！" );
		  		//重置按钮，刷新页面
	  function formReset()
	  {
		  window.location = "${pageContext.request.contextPath}/front/customer/Setting/gotoForInvoicePage.action";
	  }
</script>
	</head>
	<body>
		<div class="modal">
			
		</div>
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">发票信息</h3>
				<div class="MarketpalceTree">
					<div class="TabParent_lj publicBorder ClearBoth">
						<h3 class="FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">
							<%-- <s:if test="#session.shopInfo.invoiceCheckStatus==1">
								待审核
							</s:if>
							<s:elseif test="#session.shopInfo.invoiceCheckStatus==2">
								审核通过
							</s:elseif>
							<s:elseif test="#session.shopInfo.invoiceCheckStatus==3">
								审核未通过
							</s:elseif> --%>
						</h3>
						<div>
							<form id="form1" action="${pageContext.request.contextPath}/front/customer/Setting/saveOrUpdateForInvoice.action" method="post">
							<p></p>
							<div class="publicPadding10" style="padding-left:30px;">
								<p class="publicrelative">
									<span for="companyName" class="FontSize14" style=" width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red" style="width:5px;height:5px;padding-top:3px;">*</font> 企业名称：</span>
									<input type="text" id="companyName" name="shopInfo.companyName" value="<s:property value='#session.shopInfo.companyName'/>" class="{validate:{required:true,chinese:true,checkCompanyName:true}} Registration abc" maxlength="50"  style="width:328px;"/>
								</p>
								<p class="publicrelative">
									<span for="taxpayerNumber" class="FontSize14" style="width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 纳税人识别号：</span>
									<input type="text" id="taxpayerNumber" name="shopInfo.taxpayerNumber" value="<s:property value='#session.shopInfo.taxpayerNumber'/>" class="{validate:{required:true,number:true,checkTaxpayerNumber:true,maxlength:[20]}} Registration" style="width:328px;"/>
								</p>
								<p class="publicrelative">
									<span for="addressForInvoice" class="FontSize14" style="width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 地址：</span>
									<input type="text" id="addressForInvoice" name="shopInfo.addressForInvoice" value="<s:property value='#session.shopInfo.addressForInvoice'/>" class="{validate:{required:true}} Registration" style="width:328px;"/>
								</p>
								<p class="publicrelative">
									<span for="phoneForInvoice" class="FontSize14" style="width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 电话：</span>
									<input type="text" id="phoneForInvoice" name="shopInfo.phoneForInvoice" value="<s:property value='#session.shopInfo.phoneForInvoice'/>" class="{validate:{required:true,phone:true,checkPhoneForInvoice:true}} Registration abc"  maxlength="50"  style="width:328px;"/>
									&nbsp;<font color="red">格式：0796-8128162</font>
								</p>
								<p class="publicrelative">
									<span  for="openingBank" class="FontSize14" style="width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 开户行：</span>
									<input type="text" id="openingBank" name="shopInfo.openingBank" value="<s:property value='#session.shopInfo.openingBank'/>" class="{validate:{required:true}} Registration" style="width:328px;"/>
								</p>
								<p class="publicrelative">
									<span  for="bankAccountNumber" class="FontSize14" style="width:106px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 账号：</span>
									<input type="text" id="bankAccountNumber" name="shopInfo.bankAccountNumber" value="<s:property value='#session.shopInfo.bankAccountNumber'/>" class="{validate:{required:true,number:true,checkBankAccountNumber:true,byteRangeLength:[16,19]}} Registration abc" maxlength="50" style="width:328px;"/>
								</p>
								<p style="padding:20px 0 0 100px;">
									<input  style="width:140px;margin-right:20px;" type="submit" value="保存" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" style="margin-right:20px; text-align: center; cursor: pointer;"/>
									<input  style="width:140px;margin-right:0;text-align: center; cursor: pointer;background-color: #d01743 !important;border: 1px solid #d01743;" type="button" onclick="formReset();" value="重置"  class="publicPaddingLR10 publicMarginR10 submitButtonL BackgroundOrange radius"/> 
								</p>
								</div>
								</form>
							</div>
						</div>
					</div>
				</section>
			</div>
		<!--//right-->
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
	</body>
</html>