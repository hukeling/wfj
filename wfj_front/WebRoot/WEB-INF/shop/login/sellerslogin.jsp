<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header_top.css"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index.css"  />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css"  />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_1.css" />
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/base64.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		
<script type="text/javascript">
	//监控回车事件
	$(document).keydown(function(event){
	    var curKey = event.which; 
	    if(curKey==13){
	    	event.returnValue=false;
	        event.cancel = true;
	    	var t=document.getElementById("useNameLogin").style.display;
	        var v=0
	        if(t=="block"){
	        	v=1;
	        }else{
	        	v=2;
	        }
	        var isTrue=$("#loginButtion").attr("isTrue");
	        if(isTrue=="1"){
	        	dengluUseNameOrPhone(v);
	        }
	    }
    });
	$(document).ready(function() {
		if(chkcookies('customerLogin')){
			var cookie = $.cookie('customerLogin');
			if(cookie!=null){
				cookie = eval(cookie);
				$("#loginName").val(cookie[0].loginName);
			}
		}
	});
	var directUrl = "${directUrl}";
	  //普通方式登陆
	  function denglu(){
		  if($("#loginName").val()==""||$("#password").val()==""){$(".registerInputErrorMessage").html("<font color='red'>请输入账号或密码！</font>");return}
		 var type= '${type}';//会员登陆类型 (采购商,供应商)
		  if($("#addCookie").attr("checked")){
				var cookie = $.cookie('customerLogin');
				$.cookie('customerLogin', null); 
				var value ="[{'loginName':'"+$("#loginName").val()+"','password':'"+$("#password").val()+"'}]";//以json格式存放，方便维护和取
				$.cookie('customerLogin', value, { expires: 3 }); 
			}
		   $.ajax({
   			url:"${pageContext.request.contextPath}/loginCustomer/loginValidate.action",
   			dataType:"json",
   			type:"post",
   			data:{loginName:$("#loginName").val(),cpassword:$("#password").val(),directUrl:directUrl,type:type},
   			success:function(data){
   				if(data.isStr=="F"){
   					$("#loginButtion").attr("isTrue","1");
   					$(".registerInputErrorMessage").html("<font color='red'>用户名或密码输入有误,请重新登录！</font>");
   				}else if(data.isStr=="DJ"){
   					$("#loginButtion").attr("isTrue","1");
   					$(".registerInputErrorMessage").html("<font color='red'>对不起,您的账号已被冻结！</font>");
   				}else if(data.isStr=="SQ"){
   					window.location.href="${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action";
   				}else{
   					$("#loginButtion").attr("isTrue","0");
   					if(directUrl != ""){
   						if(data.gotoHomePage){
	   						location.href = "${pageContext.request.contextPath}/";
   						}else{
	   						re=new RegExp("_","g"); 
	   						var finalUrl=directUrl.replace(re,"%"); 
	   						directUrl = unescape(finalUrl);
	   						window.location.href=directUrl;
   						}
   						
   					}else{
   						location.href = "${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action";
   					}
   				}
   			}
   		 });
	  }
	    
		function changeShowInfo(showObj,hidObj){
			$("#"+showObj).css("display","none");
			$("#"+hidObj).css("display","block");
		}  
	    
	   function dengluUseNameOrPhone(v){
	    	if(v=="1"){
	    		denglu();
	    	}else{
	    		dengluByPhone();
	    	}
	    }
	    //发送验证码至手机
	    function sentMessage(){
	    	 var type= '${type}';//会员登陆类型 (采购商,供应商)
	    	$(".registerInputErrorMessage2").html("");
	    	var phoneNumber=$("#phoneNumber").val();
	    	var mobile =  /^[1][358][0-9]{9}$/;
			if(phoneNumber.length==11&&mobile.test(phoneNumber)){
		    	 $.ajax({
		    			url:"${pageContext.request.contextPath}/loginCustomer/sentMessage.action",
		    			dataType:"json",
		    			type:"post",
		    			data:{phoneNumber:phoneNumber,type:type},
		    			success:function(data){
		    				if(data.isSuccess=="true"){
		    					timedCount();
		    				}else{
		    					$(".registerInputErrorMessage2").html("<font color='red'>用户不存在！</font>");
		    				}
		    			}
		    		 });
			}else{
				$(".registerInputErrorMessage2").html("<font color='red'>手机号码格式错误！</font>");
			}	    	
	    }
	    
	    //使用手机号临时登录
	    function dengluByPhone(){
	   	 var type= '${type}';//会员登陆类型 (采购商,供应商)
	    	$(".registerInputErrorMessage2").html("");
	    	var phoneNumber=$("#phoneNumber").val();
	    	var trendsPassword=$("#trendsPassword").val();
	    	var mobile =  /^[1][358][0-9]{9}$/;
	    	if(phoneNumber.length==11&&mobile.test(phoneNumber)){
	    		$.ajax({
		   			url:"${pageContext.request.contextPath}/loginCustomer/loginValidateByPhone.action",
		   			dataType:"json",
		   			type:"post",
		   			data:{phoneNumber:phoneNumber,trendsPassword:trendsPassword,type:type},
		   			success:function(data){
		   				if(data.isStr=="F"){
		   					$(".registerInputErrorMessage2").html("<font color='red'>验证码无效！</font>");
		   				}else{
		   					if(directUrl != ""){
		   						directUrl = unescape(directUrl);
		   						window.location.href=directUrl;
		   					}else{
		   						location.href = "${pageContext.request.contextPath}/";
		   					}
		   				}
		   			}
		   		 });
			}else{
				$(".registerInputErrorMessage2").html("<font color='red'>手机号码格式错误！</font>");
			}
	    }
	    var Time='${application.fileUrlConfig.session_phone}';
	   var c=Time
	    function timedCount(){
	        document.getElementById('fsyzm').innerHTML ="重新获取(" + c + ")";
	        $("#fsyzm").attr("style","font-size:13px;color:#ccc !important;float: right;margin-right: 150px;text-decoration:none;");
	        $("#fsyzm").removeAttr("href");
	        document.getElementById('fsyzm').disabled="disabled";
	        c=c-1;
	        t=setTimeout("timedCount()",1000)
	        if(c<0){
	            c=Time;
	            stopCount();
	            document.getElementById('fsyzm').innerHTML ="发送验证码";
	            $("#fsyzm").attr("style","float: right;margin-right: 150px;");
	            $("#fsyzm").attr("href","javascript:sentMessage();");
	            document.getElementById('fsyzm').removeAttribute("disabled");
	        }
	    }
	    function stopCount(){
	        clearTimeout(t);
	    }
	    $(function(){
	    	document.getElementById('fsyzm').removeAttribute("disabled");
	    });
	    //验证cookie中是否存在所要的数据
	   function chkcookies(NameOfCookie){
		   var c = document.cookie.indexOf(NameOfCookie+"="); 
		   if (c != -1){
		   	return true;
		   }
		   return false;
	   }

</script>
<style type="text/css">
	a#usePhoneLoginA:hover,a#useNameLoginA:hover,a#fsyzm:hover{ font-size:13px; text-decoration:underline; color:#FF6600 !important;}
</style>
</head>

<body>
<%--	<%@ include file="../include/header.jsp" %>--%>
   <div class="hesder_topCode">
       <!--top-->
      	 <div class="hesder_top">
	          <div class="welcomeYin fl" style="padding-left:0;">
		          	<div
					style=" width:16px; height:16px; position:relative; left:0px; top:7px; float: left;">
					<img style="width:16px;height:16px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png">
				</div>
				<div style="width:100px; float:left; padding-left:6px;">
					<a
						href="javascript:window.external.addFavorite('http://www.baidu.com','SHOPJSP')"
						title="SHOPJSP">收藏商城</a>
				</div>
		    </div>
          <div class="welcomeYin fr">
                <a style="margin-left:0; padding:0 7px 0 3px;" class="fr"href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action" style="">
                  <s:if test="#session.customer==null">	
						<img style="position:relative; padding-left:6px; padding-right:3px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
				</s:if>
                                    帮助中心
                </a>
                <div id="noLoginShow"  style="display:block;float: right;" >您好!&nbsp;欢迎来到SHOPJSP!&nbsp;
               			 [<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
		            </div>
            </div>
        
          
        </div>
       </div>   
        <!--top end-->
<div style="padding: 10px 0px 0px 0px;  width:1000px; margin:0 auto;margin-left:168px;">
<h1 ><a href="${pageContext.request.contextPath}/"><img style="width:150px;height:70px;" src="${fileUrlConfig.fileServiceUploadRoot}front/images/logo.png"/></a>
     <span style="font-size:30px; line-height:40px;">&nbsp;<span style="color:#eaeaea;  font-weight:normal;">|</span>&nbsp;&nbsp;欢迎登录</span>
</h1>
</div>
	<form id="loginForm" action="" method="post">
		<div class="margin-center PublicWidth1004">
<div class="LoginBox publicHidden margin-center">
<div id="useNameLogin"  style="display: block;">
<div class="LoginBox_Left publicHidden publicBorder radius" style="height:360px; border:6px solid #e8e8e8;">
<div class="login_left_h">
<h3 class=" publicPadding20 FontSize20 FontSizeB">供应商登录 | <span style="color:blue; font-size:14px;"><a style="color:blue" href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">消费者登录</a></span></h3>
<div class="publicPadding10" style="padding-left:65px;">
<p class="publicrelative">
<i class="icon loginButton loginButton_01 publicabsolute" style="top:27px;left:0;"></i>
<label for="customer.loginName" class="FontSize14">账号
<a id="usePhoneLoginA" name="usePhoneLoginA" style="padding-left:256px;" class="FontSize13 ColorBlue" href="javascript:;" onclick="changeShowInfo('useNameLogin','usePhoneLogin')">手机动态密码登录</a>
 </label>

<br>
<input type="text" size="24" id="loginName" name="customer.loginName" placeholder="账号/手机号/邮箱" class="{validate:{required:true,maxlength:[20]}} login_userid" value=""  />
</p>
<p class="publicrelative">
<i class="icon loginButton loginButton_02 publicabsolute" style="top:27px;left:0;"></i>
<label for="customer.password" class="FontSize14">密码</label><br>
<input type="password" size="24" id="password" name="customer.password" class="{validate:{required:true,maxlength:[20]}} login_passwd" value=""  />
<div class="registerInputErrorMessage">&nbsp;</div>
</p>
<p style="text-align:left;">
 <a href="${pageContext.request.contextPath}/loginCustomer/forGotPassword.action" class="ColorBlue">忘记密码?</a>
</p>
<p style="text-align:left; height:20px;">
<input type="checkbox" id="addCookie" style="float:left;margin:3px 3px 0 0;*margin-top:-1px;"/>
<label for="signed_in" style="float:left; margin-right:3px;">保持登录 </label>
<span class="ColorQHui" style="float:left;">(如果在公共场合登录请取消选择)</span>
</p>
<p style="text-align:left;">
<input type="button" id="loginButtion" style="width: 200px; cursor: pointer;" onclick="dengluUseNameOrPhone(1)" isTrue="1"  value="登录"  class="submitButtonL BackgroundRed publicinline publicPadding5_10 FontSize16 radius strong"/>
</p>
</div>
</div>

</div>

</div>
<div id="usePhoneLogin" style="display: none;">
<div class="LoginBox_Left publicHidden publicBorder radius " style="height:360px;border:6px solid #e8e8e8;">

<div class="login_left_h">
<h3 class=" publicPadding20 FontSize20 FontSizeB">登录</h3>
<div class="publicPadding10" style="padding-left:65px;">
<p class="publicrelative">
<label for="phoneNumber" class="FontSize14">手机号 </label>
<a id="useNameLoginA" style="float: right;margin-right: 165px;" class="FontSize13 ColorBlue" href="javascript:;" onclick="changeShowInfo('usePhoneLogin','useNameLogin')">使用普通方式登录</a>
<br>
<input type="text" style="padding: 10px 10px 10px 30px;" size="24" id="phoneNumber" name="phoneNumber" class="login_userid" value=""  />
</p>
<p class="publicrelative">
<label for="trendsPassword" class="FontSize14">动态密码</label>
<a id="fsyzm" style="float: right;margin-right: 165px;" class="FontSize13 ColorBlue" href="javascript:sentMessage();">获取验证码</a>
<br>
<input type="text" style="padding: 10px 10px 10px 30px;" size="24" id="trendsPassword" name="trendsPassword" class="login_passwd" value=""/>
<div class="registerInputErrorMessage2">&nbsp;</div>
</p>
<p>
<input type="button" id="loginButtion2" style="width: 200px;" onclick="dengluUseNameOrPhone(2)" isTrue="1"  value="登录"  class="submitButtonL BackgroundRed publicinline publicPadding5_10 FontSize16 radius strong"/>
</p>
</div>
</div>
</div>
</div>

<div class="login_right_h" style="height:auto;">
  <img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/gouwuc_login.png">
</div>
<div style="width:140px; height:6px; background:#7CBE56; position: absolute; right:106px; top:436px; z-index: 99;"></div>
<div style="width:140px; height:30px; line-height:24px;  background:#7CBE56; text-align: center; float:right; margin-right:50px;border-bottom:6px #e8e8e8 solid; border-left:6px #e8e8e8 solid; border-right:6px #e8e8e8 solid;"><a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2" style=" color:#fff; font-size:14px;  cursor:pointer;">免费注册>></a></div>

</div>
</div>
	</form>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
