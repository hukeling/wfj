<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重置密码</title>
<%@ include file="../../shop/include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script type="text/javascript">
$(function() {//表单验证
	$("#form1").validate({meta: "validate", 
		submitHandler:function(form){
			$("#subitButton").attr("disabled",true);
			var passwordRandomCode=$("#passwordRandomCode").val();
			var url ="${pageContext.request.contextPath}/phone/phoneReSetPasswordValidate.action";
			$.post(url,{password:$("#password2").val(),customerId:$("#customerId").val()},function(data){
				   if(data==null||data.isSuccess=="F"){
					   $("#subitButton").attr("disabled",false);
					   $(".loginPasswordMessage").html("重置密码链接失效!");
					  return false;
				   }else{
					  window.location.href="${pageContext.request.contextPath}/phone/phoneReSetPasswordOk.action";
				   }
			},"JSON");
		}
	});
});	
</script>
</head>

<body>
	<%@ include file="../../shop/include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
	<form id="form1"  method="post">
	<input id="customerId" type="hidden" value="<s:property value='#request.customerId'/>"/>
		<div class="w600BoxBody">
			<div class="boxTitle">重置密码</div>
			<div class="w560_dotted"></div>
			<div class="font16 w560">
				
			</div>
			<div  style="margin-top: 30px; height:60px;">
				<div class="registerInputErrorMessage"></div>
				<div class="inputTag">新密码:</div>
				<div class="registerInput">
					<input type="password" size="24" id="password1" name="password1" class="{validate:{required:true,byteRangeLength:[6,20]}} inputBox" />
					<label for="password1" style="background-color:#fef8f8;padding: 0 10px;" generated="true" class="error"></label>
				</div>
			</div>
			<div>
				<div class="loginPasswordMessage"></div>
				<div class="inputTag"">再次输入:</div>
				<div class="registerInput">
					<input type="password" size="24" id="password2" name="password2" class="{validate:{required:true,byteRangeLength:[6,20],equalTo:'#password1'}} inputBox" />
					<label for="password2" style="background-color:#fef8f8;padding: 0 10px;" generated="true" class="error"></label>
				</div>
			</div>
			<div class="h20"></div>
			<input id="subitButton" type="submit" style="margin-left: 250px;margin-top: 30px;" class="submitButton hoverNo publicPadding5_10 publicinline FontSize16 FontSizeB BackgroundPurple ColorWhite1 " value="提交"/>
			<div class="ClearBoth"></div>
		</div>
		</form>
	</div>
	<%@ include file="../../shop/include/footer.jsp"%>
</body>
</html>
