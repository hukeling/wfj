<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
<title>全部分类</title>
<%@ include file="../include/head.jsp"%>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
<div class=" ClearBoth">
<div id="Categories">
	<h2 class="FontSize20 BackgroundF2Hui publicPadding20 publicMarginB10 strong">全部分类</h2>
	<!-- 解开嵌套categoryMap -->
	<s:iterator value="#application.categoryMap" var="first">
		 <div class="publicBorderR1" style="padding-left:72px;">
	    	<dl class="publicMarginTB20">
	        	<dt class="strong FontSize14 publicPaddingTB7"><a  href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="#first.key.productTypeId"/>&level=<s:property value="#first.key.level"/>&orderBy=normal"><s:property value="#first.key.sortName"/></a></dt>
	        	<s:iterator value="#first.value"  var="sec">
		            <dd class="publicPaddingT7">• <a  href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="#sec.key.productTypeId"/>&level=<s:property value="#sec.key.level"/>&orderBy=normal"><s:property value="#sec.key.sortName"/></a></dd>
		        	<s:iterator value="#sec.value"  var="thir">
			            <dd class="publicPaddingT7">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• <a  href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="#thir.key.productTypeId"/>&level=<s:property value="#thir.key.level"/>&orderBy=normal"><s:property value="#thir.key.sortName"/></a></dd>
			            <s:iterator value="#thir.value"  var="four">
				            <dd class="publicPaddingT7">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• <a  href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="#four.productTypeId"/>&level=<s:property value="#four.level"/>&orderBy=normal"><s:property value="#four.sortName"/></a></dd>
			            </s:iterator>
		        	</s:iterator>
	        	</s:iterator>
	        </dl>
    	</div>
	</s:iterator>
</div>
</div>
<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
