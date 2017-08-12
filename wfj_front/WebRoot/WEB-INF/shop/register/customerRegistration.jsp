<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header_top.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_1.css" />
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.CardIdNo.js"></script>
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css" />
<style type="text/css">
.zhuce_zj font {
	width: 156px;
	height: 50px;
	float: right;
	font-size: 12px;
	text-align: left;
	line-height: 15px;
}

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
 var flag=false;
	$(function() {//表单验证
		$("#form1").validate({meta: "validate", 
			submitHandler:function(form){
				if(!document.getElementById("deal").checked){
					$("#showErroMSG").html("<font color='red'>你同意用户协议吗</font");
				}else{
					if(flag){
						$(".submitButtonL").attr("disabled","disabled");
				    	form.submit();
					}
				}
			}
		});
	});	
    
	//在新窗口打开协议
	var openMembership=function(type){
		var url="${pageContext.request.contextPath}/register/getAgreement.action?categoryId=100&type="+type;
		window.open (url); 
	};
	//新窗口回传信息
	var returnInformation=function(p){
		if(p=='agree'){
			$("#deal").attr('checked','checked');
		}
	};
	var Time='${application.fileUrlConfig.session_phone}';
    var c=Time;
    function timedCount(){
        c=c-1;
        document.getElementById('fsyzm').value ="重新获取(" + c + ")";
        $("#fsyzm").css("background-color","#ddd");
        $("#fsyzm").css("border","1px solid  #ddd");
        t=setTimeout("timedCount()",1000);
        if(c<0){
            c=Time;
            stopCount();
            document.getElementById('fsyzm').value ="免费获取激活短信";
            document.getElementById('fsyzm').removeAttribute("disabled");
            $("#fsyzm").css("background-color","#e62d59 ");
            $("#fsyzm").css("border","1px solid  #d01743 ");
        }
    }
    function stopCount(){
        clearTimeout(t);
    }
    $(function(){
    	document.getElementById('fsyzm').removeAttribute("disabled");
    	var type='${type}';
    	if(type!=""){
    		$("#type_"+type).attr("checked","checked");
    		$("#type_"+type).attr("disabled",false);
    	}
    });
	//发送验证码至手机
    function sentMessage(){
		//校验验证码
		$.post("${pageContext.request.contextPath}/register/checkYzm.action",{yzm:$.trim($("#yzm").val())},function(data){
			//判断验证码
			if( data && data.yzmV=="0" ){
				$("#label-yzm").html("验证码已失效");
				$("#label-yzm").attr("style","width:100%;color:red;display:;");
				//自动更换验证码
				$("#verification").attr("src","${pageContext.request.contextPath}/register/userRegister.action?timestamp="+new Date().getTime());
				$("#phoneLoginCommon").removeAttr("disabled");//校验失败，取消登陆按钮锁定
			}else if( data && data.yzmV=="1" ){
				$("#label-yzm").html("验证码错误");
				$("#label-yzm").attr("style","width:100%;color:red;display:;");
				//自动更换验证码
				$("#verification").attr("src","${pageContext.request.contextPath}/register/userRegister.action?timestamp="+new Date().getTime());
				$("#phoneLoginCommon").removeAttr("disabled");//校验失败，取消登陆按钮锁定
			}else{//验证码正确 进行正常跳转
				flag=true;
				var phoneNumber=$("#phone").val();
		    	var mobile =  /^[1][358][0-9]{9}$/;//手机正则
		    	if(mobile.test(phoneNumber)){
			    	document.getElementById('fsyzm').disabled="disabled";
			        timedCount();
			    	if(phoneNumber!=""){
				    	 $.ajax({
				    			url:"${pageContext.request.contextPath}/register/sentMessage.action",
				    			dataType:"json",
				    			type:"post",
				    			data:{phoneNumber:phoneNumber},
				    			success:function(data){
				    				if(data.isSuccess=="true"){
				    					
				    				}
				    			}
				    	 });
			    	}
		    	}else{
		    		$("#label-phone").html("手机号错误或没有填写");
		    		$("#label-phone").css("display","");
		    	}
			}
		},'json');
    }
	    
	    //手机唯一校验
	      jQuery.validator.addMethod("checkPhone",function(value, element) {
	    	var flag=true;
 				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}/register/checkPhone.action",
 				   data: {phone:value},
 				   async : false,
 				   success: function(data){
		 				   if(data.isSuccess==true){
		 					   flag=false;
		 					   $("#fsyzm").attr("disabled",true);
		 				   }else{
		 					   $("#fsyzm").attr("disabled",false);
		 				   }
 				   }
 				});
 			return flag;
		},"该手机已被注册！" );
	    
	    //邮箱唯一性校验
	      jQuery.validator.addMethod("checkEmail",function(value, element) {
	    	var flag=true;
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
	    $(function(){
	    	//点击图片更换验证码
		     $("#verification").click(function(){
		         $(this).attr("src","${pageContext.request.contextPath}/register/userRegister.action?timestamp="+new Date().getTime());
		     });
	    });
	    
	  //初始化加载
		window.onload=(function(){
		 //初始化加载验证码信息
		 $("#verification").attr("src","${pageContext.request.contextPath}/register/userRegister.action?timestamp="+new Date().getTime());
		 //初始化聚焦操作
		 $("#email").focus();
		});
</script>
</head>

<body>
	<div class="hesder_topCode">
		<!--top-->
		<div class="hesder_top">
			<div class="welcomeYin fl" style="padding-left: 0;">
				<div
					style="width: 16px; height: 16px; position: relative; left: 0px; float: left;">
					<img style="width: 16px; height: 16px; margin-top: 6px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png" />
				</div>
				<div style="width: 100px; float: left; padding-left: 6px;">
					<a
						href="javascript:window.external.addFavorite('http://www.baidu.com','SHOPJSP')"
						title="SHOPJSP">收藏商城</a>
				</div>
			</div>
			<div class="welcomeYin fr">
				<a style="margin-left: 0;" class="fr"
					href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action">帮助中心</a>
				<div id="noLoginShow" style="display: block; float: right;">
					您好!&nbsp;欢迎来到SHOPJSP!&nbsp; [<a
						href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
					[<a
						href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
					[<a
						href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
					[<a
						href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
				</div>
			</div>


		</div>
	</div>
	<!--top end-->
	<div style="padding: 10px 0px 0px 173px;">
		<h1>
			<a href="${pageContext.request.contextPath}/"><img
				style="width: 150px; height: 70px;"
				src="${fileUrlConfig.fileServiceUploadRoot}front/images/logo.png" /></a>
			<span style="font-size: 30px; line-height: 40px;">&nbsp;<span
				style="color: #eaeaea; font-weight: normal;">|</span>&nbsp;&nbsp;欢迎注册
			</span>
		</h1>
	</div>
	<form id="form1"
		action="${pageContext.request.contextPath}/register/register.action"
		method="post">
		<s:token></s:token>
		<input type="hidden" name="persionId" value="${persionId}" /> <input
			type="hidden" name="cid" value="${id}" /> <input type="hidden"
			name="customer.type" value="${type}" />
		<div class="margin-center PublicWidth1004">
			<div class="LoginBox publicHidden margin-center"
				style="padding-top: 20px;">
				<div class="LoginBox_Left RegistrationBox publicHidden publicBorder"
					style="border: 6px solid #e8e8e8;">
					<h3 class=" publicPadding20 FontSize20 FontSizeB">
						<s:if test="type==1">
		企业账号注册
	</s:if>
						<s:elseif test="type==2">
		供应商注册
	</s:elseif>
						<s:else>
		个人账号注册
	</s:else>
					</h3>
					<div class="publicPadding10" style="padding-left: 30px;">
						<p id="phone_span" class="publicrelative">
							<label for="email" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 电子邮箱：</label> <input type="text" id="email"
								name="customer.email"
								class="{validate:{required:true,emailNew:true,checkEmail:true}} Registration"
								style="width: 328px;" />
						</p>
						<p class="publicrelative">
							<label for="password" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 密码：</label> <input type="password" id="password"
								name="customer.password"
								class="{validate:{required:true,byteRangeLength:[6,20]}} Registration"
								style="width: 328px;" />
						</p>
						<p class="publicrelative">
							<label for="password2" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 确认密码：</label> <input type="password" id="password2"
								name="password2"
								class="{validate:{required:true,byteRangeLength:[6,20],equalTo:'#password'}} Registration"
								style="width: 328px;" />
						</p>
						<p class="publicrelative">
							<label for="phone" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 手机：</label> <input type="text" id="phone"
								name="customer.phone"
								class="{validate:{required:true,mobile:true,checkPhone:true}} Registration abc"
								maxlength="50" style="width: 328px;" /> <label id="label-phone"
								style="width: 100%; color: red;" for="phone" generated="true"
								class="error"></label>
						</p>
						<P class="publicrelative">
							<label for="yzm" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 验证码：</label> <INPUT
								style="width: 156px; float: left;"
								class="{validate:{required:true}} Registration" name="yzm"
								id="yzm" type="text" value=""><img onclick="nextImg();" src=""
								id="verification"
								style="cursor: pointer; float: left; height: 40px; border-radius: 2px; margin-left: 13px;" />
							<span
								style="cursor: pointer; float: left; height: 40px; border-radius: 2px; margin-left: 5px;"><a
								style="color: #0199e4; font-size: 16px;"
								href="javascript:nextImg();">换一张</a></span>
							<script type="text/javascript">
		//换一张超级链接方法
		function nextImg(){
		 $("#verification").attr("src","${pageContext.request.contextPath}/register/userRegister.action?timestamp="+new Date().getTime());
		}
	</script>
							<label id="label-yzm" style="width: 100%; color: red;" for="yzm"
								generated="true" class="error"></label>
						</P>
						<%-- <p id="phone_span" class="publicrelative">
							<label for="verificationCode" class="FontSize14"
								style="width: 100px; text-align: right; margin-top: 8px; float: left;"><font
								color="red">*</font> 短信验证码：</label> <input type="type"
								id="verificationCode" name="verificationCode"
								class="{validate:{required:true,checkCode:true}} Registration"
								style="width: 156px;" /> <span style="margin-left: 10px;"><input
								id="fsyzm" type="button" onclick="sentMessage()"
								style="background-color: #e62d59; padding: 7px 6px; cursor: pointer; text-align: center; color: #fff; font-size: 14px; font-weight: normal; width: 160px; border-radius: 0; -webkit-border-radius: 0; border: 1px solid #d01743; border-radius: 4px;"
								value="免费获取激活短信" /></span> <label for="verificationCode"
								generated="true" class="error"></label>
						</p> --%>
						<p style="padding: 0px 0 0 99px;">
							<input type="checkbox" id="deal" name="deal"
								style="margin: 3px 5px 0 0;" /> <label for="deal"
								style="position: relative; top: -3px;">我已经阅读并接受 <a
								onclick="openMembership(${type})" href="javascript:;"
								class="ColorBlue">用户协议</a></label> <span id="showErroMSG"
								style="margin-left: 10px;"></span>
						</p>
						<p style="padding: 0 0 0 100px;">
							<input type="submit" value="注册"
								style="width: 347px; padding: 8px 10px; cursor: pointer; border: none; border-radius: 4px; background: #e62d59; border: 1px #d01743 solid;"
								class="submitButtonL radius" onclick="form1.submit()" />
						</p>
						<p class="publicrelative">
							<span style="margin-left: 643px; font-size: 16px;">我已经注册，马上
								<a
								href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=${type}"
								style="color: #088ac7; font-size: 16px; cursor: pointer;">登录</a>！
							</span>
						</p>
					</div>
				</div>
				<div class="login_right_h" style="top: 100px; right: 100px;">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/gouwuche.jpg"
						style="padding-top: 30px; padding-left: 30px;" />
				</div>
			</div>
		</div>
	</form>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
