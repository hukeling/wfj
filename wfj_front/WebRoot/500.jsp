<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>500</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css"
	rel="stylesheet" type="text/css" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/public.css"
	rel="stylesheet" type="text/css" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index_1.css"
	rel="stylesheet" type="text/css" />
<!-- 底部信息样式 -->
<style>
.foot {
	height: 165px;
	background: #f7f7f7;
	overflow: hidden;
	text-align: center;
}

.foot_daohang {
	width: 1192px;
	height: 165px;
	overflow: hidden;
	margin: 0 auto;
}

.foot_daohang p {
	text-align: center;
	line-height: 24px;
	color: #282828;
	height: auto;
}

.dibu_img {
	width: 400px;
	height: 40px;
	margin: 0 auto;
	overflow: hidden;
	margin-top: 20px;
}

.dibu_img img {
	padding-left: 5px;
	padding-right: 5px;
}
</style>
</head>

<body>
	<div class="margin-center PublicWidth1004">
		<img
			src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/error/500.png"
			class="publicblock margin-center publicMarginTop15 publicMarginBot15">
		<div
			class="text_Georgia FontSize13 font_family_Georgia font_style_italic">
			似乎您要找到页面已经或者是不复存在。或者也许你只是输错的东西。<br>
			您可以重新开始与主要的导航页面的顶部。或者,如果您喜欢,可以使用下面的联系表,请给我们留言。我们很想听听您的意见。
		</div>
		<a href="${pageContext.request.contextPath}/"
			class="publicblock text-center publicPadding10 hoverNo FontSize16 font_family_Georgia publicMarginTop15">
			<span style="font-size: 30px; color: red; font-weight: bold;">
				点击返回首页 > </span>
		</a>
		<div style="margin-bottom: 50px;"></div>
	</div>

	<!-- foot底部信息 -->
	<%--<div class="foot">
	    <div class="foot_daohang">
	      <p style="padding-top:20px;">关于我们 | 隐私及安全 | 商务合作 | 联系我们 | 人才招聘 | 友情链接 | 百度统计 </p>
	      <p>版权所有SHOPJSP商城copyright***.com  2013  京IPC 备  100004422号</p>
	      <div class="dibu_img"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/dibu_115.gif" /><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/dibu_117.gif" /><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/dibu_119.jpg" /></div>
	    </div>
    </div>--%>

</body>
</html>
