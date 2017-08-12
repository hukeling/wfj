<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>促销活动</title>
<%@ include file="../shop/include/head.jsp"%>
</head>

<body>
<%@ include file="../shop/include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/sales_style.css">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/WookMark.css">

<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/sales/jquery.easing.1.3.js"></script>
<!--sales images-->
<figure class="sales_focus_images rotator">
	<div id="rot1">
		<img id="imgCard" src="" width="1000px;" height="300px;" class="bg" alt=""/>
		<div class="heading">
			<h1 id="activityName" class="info_heading"></h1>
		</div>
		<div  class="description">
			<p><span id="activityInfo"></span>
		<%--<a href="#" class="more">Read more</a></p>--%>
		</div>    
	</div>
</figure>
<!--//sales images-->
<section class="publicMarginTop15 Sales_Promotion">
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">促销活动</h3>
<ul id="tiles">
<!-- These is where we place content loaded from the Wookmark API -->
</ul>

<div id="loader">
<div id="loaderCircle"></div>
</div>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/sales/jquery.wookmark.js"></script>
<!-- Once the page is loaded, initalize the plug-in. -->
<script type="text/javascript" >
	//全局变量
	apiURL = "${pageContext.request.contextPath}/front/store/salesPromotionCenter/getJson.action?promotionId="+${promotionId};
	hrefURL = "${pageContext.request.contextPath}/productInfo/";
	urlPath="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>";
<%--	srcValue="";--%>
</script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/sales/private.wookmark.js" ></script>
</section>
</div>
<%@ include file="../shop/include/footer.jsp"%>
</body>
</html>
