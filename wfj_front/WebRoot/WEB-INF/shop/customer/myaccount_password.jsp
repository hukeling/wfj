<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>修改密码</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript">
			$(function() {
				//初始化清空原始密码
				$("#password").val("");
				 $("#change-password-form").validate({meta: "validate",
	                   submitHandler:function(form){//当表单通过验证时回调函数
	                    $.post($(form).attr("action"),$(form).serialize(),function(data){
		                     if(data.success==true){
		                      //lconfirm2('消息提醒', '修改成功',submitOk);
		                          lconfirm_yes('消息提醒', '修改成功',submitOk);
		                     }
	                    },'json');
	                   }
	      		});
			});
			//修改密码成功后执行方法
			function submitOk(){
				window.location.href='${pageContext.request.contextPath}/front/customer/Setting/changePass.action';
			}
			
			 //旧密码校验
		      jQuery.validator.addMethod("checkPassword",function(value, element) {
			    	var flag=true;
	 				$.ajax({
	 				   type: "POST",dataType: "JSON",
	 				   url:"${pageContext.request.contextPath}/front/customer/Setting/checkPassword.action",
	 				   data: {password:value},
	 				   async : false,
	 				   success: function(data){
			 				   if(data.success!=true){
			 					   flag=false;
			 				   }
	 				   }
	 				});
		 			return flag;
				},"旧密码错误！" );
			 //校验新密码是否与旧密码相同
		      jQuery.validator.addMethod("eqOldPassword",function(value, element) {
		    	    var flag=true;
			    	var old=$("#password").val();
			    	if(old==value){
			    		flag=false;
			    	}
		 			return flag;
				},"不可与旧密码相同！" );
			 
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
				<s:elseif test="#session.customer.type==2">
					<%@ include file="../include/left_shop.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				 <section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">修改密码</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<form id="change-password-form" name="change-password-form"
									action="changePassword.action" method="post">
									<fieldset class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<s:if test="#session.customer.password!=null">
												<li>
													<div>
														<i class="ColorRed">*</i> 旧密码
													</div>
													<div>
														<input id="password" type="password" name="password" value="" class="{validate:{required:true,checkPassword:true}}">
													</div>
												</li>
											</s:if>
											<li>
												<div>
													<i class="ColorRed">*</i> 新密码
												</div>
												<div>
													<input type="password" name="password0" id="password0"
														value="" class="{validate:{required:true,byteRangeLength:[6,20],eqOldPassword:true}}">
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 密码确认
												</div>
												<div>
													<input id="com_psd" name="password1" type="password"
														value="" class="{validate:{required:true,byteRangeLength:[6,20],equalTo:'#password0'}}">
												</div>
											</li>
											<li>
												<div></div>
												<div>
													<input type="submit" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" style=" border: 0;" value="保存">
												</div>
											</li>
										</ul>
									</fieldset>
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
