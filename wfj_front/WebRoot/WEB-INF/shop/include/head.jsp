<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css">
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
	<!-- 商城底部文章样式 -->
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
	
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
	
	<!--二维码(jquery qrcode插件制作二维码生成可随意生成多张二维码图片的二维码生成器。拿手机扫一扫就能获取二维码内容数据。)-->
	<!--[if (gte IE 6)&(lte IE 8)]>
	<![endif]-->
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/selectivizr-min.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/css3-mediaqueries.js"></script>  
	<script type="text/javascript">
		$(function () {
			$.scrollUp();
		});
		/*	前台同步提交表单使用验证方法之后无效。
			form表单控制提交按钮，防止重复提交
			使用时，在jsp里面需要加上以下内容：
			1.from提交按钮submit加上class="submitImg"。
			2.在submit后面加上<span class="submitImgLoad"></span>用来显示图标和提示信息
			3.使用的form时，需要在此form中添加class="formLoad"
			
			$("form.formLoad").bind("submit", function(){
				//控制保存按钮变灰，避免重复提交
				  $(".submitImg").attr("disabled","disabled");
					var htm="<img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif'/>";
					htm+="&nbsp;&nbsp;正在提交，请稍等...";
					console.log(htm);
					$(".submitImgLoad").html(htm);
			});
		*/
     </script>
	
	
