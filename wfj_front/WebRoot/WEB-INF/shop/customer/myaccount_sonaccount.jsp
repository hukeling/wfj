<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>子帐号管理</title>
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
				$("#form1").validate({meta: "validate",
					submitHandler:function(form){//当表单通过验证时回调函数
						$.post($(form).attr("action"),$(form).serialize(),function(data){
							if(data&&data.success==true){
								lconfirm_yes('消息提醒', '操作成功',submitOk);
							}
						},'json');
					}
				});
			});
			//添加成功后执行方法
			function submitOk(){
				window.location.href='${pageContext.request.contextPath}/front/customer/Setting/listSonaccount.action';
			}
	    //校验推荐人是否存在
	    jQuery.validator.addMethod("checkReferrerAccount",function(value, element) {
	    	var flag=true;
 			if(value!=""){
 				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/register/checkReferrerAccount.action",
 				   data: {loginName:value},
 				   async : false,
 				   success: function(data){
		 				   if(data.isSuccess==false){
		 					   flag=false;
		 				   }else{
		 					  $('input[name="id"]').val(data.id);
		 				   }
 				   }
 				});
 			}
 			return flag;
		},"推荐人不存在！");
	    //真实姓名校验
	    jQuery.validator.addMethod("checkTrueName",function(value, element) {
	    	var nickname = '${sonaccount.nickname}';
	    	var flag=true;
	    	//正则校验
	    	var reg=/^[\u4e00-\u9fa5]+$/;
	    	if(value!=nickname){
 				$.ajax({
 					type: "POST",dataType: "JSON",
 					url:"${pageContext.request.contextPath}/register/checkTrueName.action",
 					data: {trueName:value},
 					async : false,
 					success: function(data){
						if(data.isSuccess==true){
							flag=false;
						}
					}
				});
			}
			return flag;
		},"姓名已存在！" );
	    //手机唯一校验
	      jQuery.validator.addMethod("checkPhone",function(value, element) {
	      	var phone = '${sonaccount.phone}';
	    	var flag=true;
	    	if(value!=phone){
 				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/register/checkPhone.action",
 				   data: {phone:value},
 				   async : false,
 				   success: function(data){
 				   		if(data.isSuccess==true){
	 					   flag=false;
	 				   }
 				   }
 				});
 			}
 			return flag;
		},"该手机已被注册！" );
	    
	    //邮箱唯一性校验
	      jQuery.validator.addMethod("checkEmail",function(value, element) {
	      	var email = '${sonaccount.email}';
	    	var flag=true;
	    	if(value!=email){
	      		$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/register/checkEmail.action",
 				   data: {email:value},
 				   async : false,
 				   success: function(data){
	 				   if(data.isExsit==true){
	 					   flag=false;
	 				   }
 				   }
 				});
	      	}
 			return flag;
		},"该邮箱已被使用！" );
	      
	    //验证码校验
	      jQuery.validator.addMethod("checkCode",function(value, element) {
	    	var flag=true;
 				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/register/checkCode.action",
 				   data: {verificationCode:value},
 				   async : false,
 				   success: function(data){
		 				   if(data.isExsit!="1"){
		 					   flag=false;
		 				   }
 				   }
 				});
 			return flag;
		},"验证码无效！" );
		//子会员类型回显
		$(function(){
			var sonAccountId = '${sonaccount.sonAccountId}';
			if(sonAccountId!=''){
				var id='${sonaccount.type}';
				//清空
				$('input[name="sonaccount.type"]').removeAttr("checked");
				$('#type_'+id).attr("checked",true);
			}
		});
		
		$(function(){
			var flag="${flag}";
			if(flag=="1"){
				$("#password").val("");
				$("#password2").val("");
			}
			
		});
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
					<s:if test="flag==1">
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">添加子帐号</h3>
					</s:if>
					<s:else>
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">修改子帐号</h3>
					</s:else>
				<div class="MarketpalceTree">
					<div class="TabParent_lj publicBorder ClearBoth">
						<div>
							<form id="form1" name="change-son-form" action="saveOrUpdateSonaccount.action" method="post">
							<p></p>
								<input type="hidden" id="sonAccountId" name="sonaccount.sonAccountId" value="${sonaccount.sonAccountId}"/>
								<input type="hidden" id="lockedState" name="sonaccount.lockedState" value="${sonaccount.lockedState}"/>
								<input type="hidden" id="customerId" name="sonaccount.customerId" value="${sonaccount.customerId}"/>
							<div class="publicPadding10" style="padding-left:30px;">
								<p class="publicrelative">
									<label for="password" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 子账号类型：</label>
									<p></p>
									<input type="radio" id="type_1" name="sonaccount.type" checked="checked" value="1" />采购人员
									<input type="radio" id="type_2" name="sonaccount.type" value="2" />财务人员
								</p>
								<p class="publicrelative">
									<span for="trueName" class="FontSize14" style=" width:100px; text-align:right; margin-top:8px; float:left;">
									<font color="red" style="width:5px;height:5px;padding-top:3px;">*</font> 真实姓名：</span>
									<input type="text" id="trueName" name="sonaccount.nickname" value="${sonaccount.nickname }" class="{validate:{required:true,chinese:true,checkTrueName:true,byteRangeLength:[4,8]}} Registration abc" maxlength="50"  style="width:328px;"/>
								</p>
								<s:if test="sonaccount.sonAccountId !=' ' && sonaccount.sonAccountId != null ">
									<p class="publicrelative">
										<label for="password" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
										<font color="red">*</font> 密码：</label>
										<input type="password" id="password" name="sonaccount.password" class="{validate:{byteRangeLength:[6,20]}} Registration" style="width:328px;"/>
									</p>
									<p class="publicrelative">
										<label for="password2" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
										<font color="red">*</font> 确认密码：</label>
										<input type="password" id="password2" name="password2" class="{validate:{byteRangeLength:[6,20],equalTo:'#password'}} Registration" style="width:328px;"/>
									</p>
								</s:if>
								<s:else>
									<p class="publicrelative">
										<label for="password" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
										<font color="red">*</font> 密码：</label>
										<input type="password" id="password" name="sonaccount.password" class="{validate:{required:true,byteRangeLength:[6,20]}} Registration" style="width:328px;"/>
									</p>
									<p class="publicrelative">
										<label for="password2" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
										<font color="red">*</font> 确认密码：</label>
										<input type="password" id="password2" name="password2" class="{validate:{required:true,byteRangeLength:[6,20],equalTo:'#password'}} Registration" style="width:328px;"/>
									</p>
								</s:else>
								<p id="phone_span" class="publicrelative">
									<label  for="email" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 电子邮箱：</label>
									<input type="type" id="email" name="sonaccount.email" value="${sonaccount.email }" class="{validate:{required:true,email:true,checkEmail:true}} Registration" style="width:328px;"/>
								</p>
								<p class="publicrelative">
									<label for="phone" class="FontSize14" style="width:100px; text-align:right; margin-top:8px; float:left;">
									<font color="red">*</font> 手机：</label>
									<input type="text" id="phone" name="sonaccount.phone" value="${sonaccount.phone }" class="{validate:{required:true,mobile:true,checkPhone:true}} Registration abc" maxlength="50"  style="width:328px;"/>
								</p>
									<s:if test="sonaccount.sonAccountId == '' ">
								<p style="padding:20px 0 0 100px;">
									<input type="submit" value="添加" style="width: 200px; cursor:pointer; border:none; border-radius:4px;" class="submitButtonL radius"/> 
								</p>
									</s:if>
									<s:else>
								<p style="padding:20px 0 0 100px;">
									<input type="submit" value="保存" style="width: 170px; cursor:pointer; border:none; border-radius:4px;" class="submitButtonL radius"/> 
									<input type="button" onclick="goBack();" value="返回" style="width: 170px; cursor:pointer; border:none; border-radius:4px;background-color: #FF6600 !important;" class="submitButtonL radius"/> 
									<script>
										function goBack(){
											window.location.href="${pageContext.request.contextPath}/front/customer/Setting/listSonaccount.action";
										}
									</script>
								</p>
									</s:else>
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