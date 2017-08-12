<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>忘记密码</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<script type="text/javascript">
$(function(){//表单验证
	$(".submitButton").bind("click",function(){ 
		if($("#email").val()==""){
			$(".registerInputErrorMessage").html("邮件地址不可为空!");
			return false;
		}else if($("#email").val()!=""){
			var email = $("#email").val();
			var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			if(email != ""){
			if(reg.test(email)){
				$(".registerInputErrorMessage").html("");
				var url ="${pageContext.request.contextPath}/loginCustomer/forGotValidate.action";
				$(".submitButton").attr("disabled",true);//禁用提交按钮
				$(".submitImgLoad").attr("style","display:block;margin:5px 0 0 20px;float: left;");//显示loading图片
				$.post(url,{email:$("#email").val()},function(data){
					   if(data.msg=="F"){
						   $(".registerInputErrorMessage").html("无此用户！");
						   $(".submitButton").attr("disabled",true);//启用提交按钮
						   $(".submitImgLoad").attr("style","display:none;margin:5px 0 0 20px;float: left;");//隐藏loading图片
						  return false;
					   }else{
						  window.location.href="${pageContext.request.contextPath}/loginCustomer/personalPassword.action";
					   }
				},"JSON");
			}else{
				$(".registerInputErrorMessage").html("邮箱格式不正确.");
				$("#email").focus();
				return false;
			}
			}
		}
		
	});
})


</script>

</head>

<body>
	<%@ include file="../include/header.jsp"%>
	<form id="forgotForm" action="" method="post">
		<div class="margin-center PublicWidth1004">
			<div class="LoginBox publicHidden margin-center">
				<section  class="LoginBox_Left RegistrationBox publicHidden publicBorder radius " style="background-image:none;">
					<h3 class=" publicPadding20 FontSize20 FontSizeB line-height1">
					通过身份认证重置密码<br>
					<span class="ColorBlue FontSize13" style="font-weight:100;">输入您的账户邮箱，点击下方的'继续'按钮。</span>
					</h3>
                 	<div class="publicPadding10" style="padding-left:50px;">
						<p class="publicrelative">
							<label for="customer.email" class="FontSize16">邮箱</label><br>
							<input type="text" size="24" id="email" name="customer.email" class="Registration" style="width:400px; padding:5px;"/><br>
							<span style="height:60px; line-height: 60px;">例如: bargainout@b2b2c.com.cn</span>
							<div class="registerInputErrorMessage"></div>
						</p>
						<p>
							<a href="javascript:void(0)" class="submitButton hoverNo publicPadding5_10 publicinline FontSize16 FontSizeB BackgroundPurple ColorWhite1 " style="float:left;">继续</a>
							<p class="submitImgLoad strong " style="display:none;margin:5px 0 0 20px;float: left;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
						</p>
					</div>					
					 <section class="RegistrationBox_Right">
					  <p><span class="FontSize14">确认账户是否存在?</span></p>
					  <a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1" class="hoverNo publicPadding5_10 publicinline FontSize16 FontSizeB BackgroundOrange ColorWhite1" style="">登录</a>
				    </section>
				
					</section>
				
			</div>
		</div>
	</form>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
