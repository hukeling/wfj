<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header_top.css"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index.css"  />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css"  />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_1.css" />
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.CardIdNo.js"></script>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css"/>
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<style>
	/*重置浏览器默认样式*/


	.reg_centent {width:990px;margin:30px  auto 0; border: 1px solid #DDDDDD; clear: both; overflow: hidden;padding: 20px 80px;}
	.boxbg { background: none repeat scroll 0 0 #FFFFFF; border-radius: 4px;}
	.register-wrap {width:822px; height:auto; overflow:hidden;margin:0 auto;clear: both; overflow: hidden; padding: 20px 0; font-family:"微软雅黑";}
	.enterprise-register{float:left; background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/register.png") no-repeat -8px -59px; display: inline; float: left; height: 335px; padding: 60px 26px 0; width: 309px;}
	.personal-register {float:left; margin-left:100px; background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/register.png") no-repeat -378px -59px; display: inline; float: left; height: 335px; padding: 60px 26px 0; width: 309px;}
	.er-h3 { color: #000000;font-size: 13px; font-weight: bold;line-height: 30px;}
	.enterprise-register p, .personal-register p { line-height: 29px; font-size:12px; color:#666; margin:0;}
	.er-btn1 {background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/register.png") no-repeat -404px -2px; border: 0 none;cursor: pointer; height: 51px; margin-top: 18px;width: 309px;}
	.er-btn{ background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/register.png") no-repeat -34px -2px; border: 0 none; cursor: pointer; height: 51px; margin-top: 10px; width: 309px;}
	button, input, select, textarea {font-size: 100%;}
</style>
<script type="text/JavaScript">
	function gotoCustomerRegistrationPage(type){
		window.location.href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type="+type;
	}
</script>
</head>
<body>
  <div class="hesder_topCode">
        <!--top-->
        <div class="hesder_top">
           <div class="welcomeYin fl" style="padding-left:0;">	
             	<div
					style=" width:16px; height:16px; position:relative; left:0px;float: left;">
					<img style="width:16px;height:16px;margin-top:6px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png"/>
				</div>
				<div style="width:100px; float:left; padding-left:6px;">
					<a
						href="javascript:window.external.addFavorite('http://www.baidu.com','SHOPJSP')"
						title="SHOPJSP">收藏商城</a>
				</div>
			</div>
          <div class="welcomeYin fr">
                <a style="margin-left:0;" class="fr"href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action">帮助中心</a>
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
<div style="padding: 10px 0px 0px 173px;">
<h1 ><a href="${pageContext.request.contextPath}/"><img style="width:150px;height:70px;" src="${fileUrlConfig.fileServiceUploadRoot}front/images/logo.png"/></a>
     <span style="font-size:30px; line-height:40px;">&nbsp;<span style="color:#eaeaea;  font-weight:normal;">|</span>&nbsp;&nbsp;欢迎注册</span>
</h1>
</div>

<div class="reg_centent boxbg">
		<div class="register-wrap">
		    <div class="enterprise-register">
		       <!-- <h3 class="er-h3">申请条件：</h3>  -->
		        <p style="padding-bottom:35px;"><span style=" color:#0157b4; padding:0 5px 0px 0; font-size:16px;">■</span>能完成订货、付款、 签收全部流程</p>
		        <p><span style=" color:#0157b4; padding:0 5px 0 0; font-size:16px;">■</span>支持增值税发票</p>
		        <br>

		        <p style="line-height:22px;">如果您或者您的企业不需要单独付款员账号</p>
		        <p style="line-height:22px;">请注册个人账号</p>
		        <br>
		        <br>
		        <br>
		        <br>
		        <br>
		       
		        <input class="er-btn" type="button" value="" onclick="gotoCustomerRegistrationPage(3)">
		    </div>
		    <div class="personal-register">
		         <p style="line-height:22px;"><span style=" color:#0157b4; padding:0 5px 0 0; font-size:16px;">■</span>管理店铺</p>
		         <br>
		         <br>
		         <p><span style=" color:#0157b4; padding:0 5px 0 0; font-size:16px; line-height:22px;">■</span>操作商品发布</p>
		         <p style="line-height:22px; padding-top:18px;">发布商品</p>
		         <p style="line-height:22px;">商品上下架</p>
		         <p style="line-height:22px;">发货流程</p>
                <br>
                <br>
		        <br>
		        <input class="er-btn1" type="button" value="" onclick="gotoCustomerRegistrationPage(2)" style="margin-top:19px;">
		    </div>
		</div>
</div>

<%@ include file="../include/footer.jsp"%>
</body>
</html>
